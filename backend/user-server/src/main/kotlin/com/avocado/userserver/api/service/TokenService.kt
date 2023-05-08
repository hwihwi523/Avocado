package com.avocado.userserver.api.service

import com.avocado.userserver.api.controller.OauthController
import com.avocado.userserver.api.response.TokenRefreshResp
import com.avocado.userserver.common.error.BaseException
import com.avocado.userserver.common.error.ResponseCode
import com.avocado.userserver.common.utils.ConvertIdUtil
import com.avocado.userserver.db.repository.ConsumerRepository
import com.avocado.userserver.db.repository.ProviderRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Service

@Service
class TokenService (
    val jwtProvider: JwtProvider,
    val providerRepository: ProviderRepository,
    val consumerRepository: ConsumerRepository,
    val convertIdUtil: ConvertIdUtil,
) {
    val log: Logger = LoggerFactory.getLogger(TokenService::class.java)

    suspend fun refresh(request: ServerHttpRequest): TokenRefreshResp {

        val token = jwtProvider.getToken(request)
        log.info("token: {}", token)
        val claims = jwtProvider.parseClaims(token)
        log.info("claims: {}", claims)
        val id = convertIdUtil.unHex(claims["id"]?.toString()?:throw BaseException(ResponseCode.WRONG_TOKEN))

        var resp: TokenRefreshResp = when (claims["type"]?.toString()) {
            "consumer" -> getConsumerToken(id)
            "provider" -> getProviderToken(id)
            else -> throw BaseException(ResponseCode.WRONG_TOKEN)
        }
        log.info("response: {}", resp)

        return resp
    }

    private suspend fun getConsumerToken(id: ByteArray): TokenRefreshResp {
        val consumer = consumerRepository.findById(id)?: throw BaseException(ResponseCode.NOT_FOUND)
        return TokenRefreshResp(jwtProvider.getAccessToken(consumer), jwtProvider.getRefreshToken(consumer))
    }

    private suspend fun getProviderToken(id: ByteArray): TokenRefreshResp {
        val provider = providerRepository.findById(id)?: throw BaseException(ResponseCode.NOT_FOUND)
        return TokenRefreshResp(jwtProvider.getAccessToken(provider), jwtProvider.getRefreshToken(provider))
    }

}