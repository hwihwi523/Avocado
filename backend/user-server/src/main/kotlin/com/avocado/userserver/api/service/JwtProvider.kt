package com.avocado.userserver.api.service

import com.avocado.userserver.api.controller.OauthController
import com.avocado.userserver.common.error.BaseException
import com.avocado.userserver.common.error.ResponseCode
import com.avocado.userserver.common.utils.ConvertIdUtil
import com.avocado.userserver.db.entity.Consumer
import com.avocado.userserver.db.entity.Provider
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import java.security.Key
import java.util.*

@Service
class JwtProvider(
    @Value("\${jwt.secret}")
    val secretKey: String,
    @Value("\${jwt.access_expiration}")
    val ACCESS_EXPIRATION_TIME: Long,
    @Value("\${jwt.refresh_expiration}")
    val REFRESH_EXPIRATION_TIME: Long,
    @Value("\${jwt.issuer}")
    val ISSUER: String,

    val convertIdUtil: ConvertIdUtil
) {
    val HEADER_PREFIX: String = "Bearer "

    var SECRET_KEY: Key = Keys.hmacShaKeyFor(secretKey.toByteArray())

    val log: Logger = LoggerFactory.getLogger(JwtProvider::class.java)

    suspend fun getAccessToken(member: Any): String {
        println(member)
        var claims: Map<String, Any>
        if (member is Provider) {
            claims = createProviderAccessClaims(member)
        } else if (member is Consumer) {
            claims = createConsumerAccessClaims(member)
        } else {
            throw BaseException(ResponseCode.BAD_REQUEST)
        }

        var now = Date()
        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .setClaims(claims)
            .setIssuer(ISSUER)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + ACCESS_EXPIRATION_TIME))
            .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
            .compact()
    }

    suspend fun getRefreshToken(member: Any): String {
        var claims: Map<String, Any?>
        if (member is Provider) {
            claims = createProviderRefreshClaims(member)
        } else if (member is Consumer) {
            claims = createConsumerRefreshClaims(member)
        } else {
            throw BaseException(ResponseCode.BAD_REQUEST)
        }

        var now = Date()
        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .setClaims(claims)
            .setIssuer(ISSUER)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + REFRESH_EXPIRATION_TIME))
            .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
            .compact()
    }


    private suspend fun createProviderAccessClaims(provider: Provider): Map<String, Any> {
        var claims = HashMap<String, Any>()
        claims["type"] = "provider"
        claims["id"] = convertIdUtil.hex(provider.id)
        return claims
    }

    private suspend fun createConsumerAccessClaims(consumer: Consumer): Map<String, Any> {
        var claims = HashMap<String, Any>()
        claims["type"] = "consumer"
        claims["id"] = convertIdUtil.hex(consumer.consumerId)
        return claims
    }

    private suspend fun createProviderRefreshClaims(provider: Provider): Map<String, Any> {
        var claims = HashMap<String, Any>()
        claims["type"] = "provider"
        claims["name"] = provider.name
        claims["id"] = convertIdUtil.hex(provider.id)
        claims["email"] = provider.email
        claims["picture_url"] = ""
        claims["gender"] = ""
        claims["age"] = ""
        claims["height"] = ""
        claims["weight"] = ""
        claims["mbti_id"] = ""
        claims["personal_color_id"] = ""

        return claims
    }

    private suspend fun createConsumerRefreshClaims(consumer: Consumer): Map<String, Any?> {
        var claims = HashMap<String, Any?>()
        claims["type"] = "consumer"
        claims["name"] = consumer.name
        claims["id"] = convertIdUtil.hex(consumer.consumerId)
        claims["email"] = consumer.email
        claims["picture_url"] = consumer.pictureUrl
        claims["gender"] = consumer.gender?:"NAN"
        claims["age_group"] = consumer.ageGroup?:-1
        claims["height"] = consumer.height?:-1
        claims["weight"] = consumer.weight?:-1
        claims["mbti_id"] = consumer.mbtiId?:-1
        claims["personal_color_id"] = consumer.personalColorId?:-1

        return claims
    }

    suspend fun getToken(request: ServerHttpRequest): String {
        val bearerToken: String = request.headers.getFirst(HttpHeaders.AUTHORIZATION)?: throw BaseException(ResponseCode.UNAUTHORIZED)
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(HEADER_PREFIX)) {
            return bearerToken.substring(7)
        }
        throw BaseException(ResponseCode.WRONG_HEADER)
    }


    suspend fun parseClaims(token: String): Claims {
        val claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).body
        log.info("claims : {}", claims)

        return claims;
    }

    suspend fun getClaims(request: ServerHttpRequest): Claims {
        return parseClaims(getToken(request))
    }

    suspend fun getType(claims: Claims):String {
        return claims["type"]?.toString()?:throw BaseException(ResponseCode.WRONG_TOKEN)
    }

    suspend fun getId(claims: Claims):ByteArray {
        return convertIdUtil.unHex(claims["id"]?.toString()?:throw BaseException(ResponseCode.WRONG_TOKEN))
    }

}