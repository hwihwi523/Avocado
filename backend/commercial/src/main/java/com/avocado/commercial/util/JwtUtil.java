package com.avocado.commercial.util;

import com.avocado.commercial.error.CommercialException;
import com.avocado.commercial.error.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.UUID;


@Component
@Slf4j
public class JwtUtil {
    private UUIDUtil uuidUtil;

    private Key SECRET_KEY;
    private Long ACCESS_EXPIRATION_TIME;
    private Long REFRESH_EXPIRATION_TIME;

    private String ISSUER;

    private final String HEADER_PREFIX = "Bearer ";


    @Autowired
    private JwtUtil(@Value("${jwt.secret}") String secretKey,
                     @Value("${jwt.access_expiration}") long eccExpirationTime,
                     @Value("${jwt.refresh_expiration}") long refExpirationTime,
                     @Value("${jwt.issuer}") String issuer,
                     UUIDUtil uuidUtil) {
        this.SECRET_KEY = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.ACCESS_EXPIRATION_TIME = eccExpirationTime;
        this.REFRESH_EXPIRATION_TIME = refExpirationTime;
        this.ISSUER = issuer;
        this.uuidUtil = uuidUtil;

    }



    private String getToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(HEADER_PREFIX)) {
            return bearerToken.substring(7);
        }
        throw new CommercialException(ErrorCode.INVALID_AUTH_TOKEN);

    }

    private Claims parseClaims(String token) throws ExpiredJwtException {
        Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
        log.info("claims: {}", claims);
        return claims;
    }

    public Claims getClaims(HttpServletRequest request) {
        return parseClaims(getToken(request));
    }

    public String getType(Claims claims) {
        return claims.get("type").toString();
    }

    public UUID getId(Claims claims) {
        return uuidUtil.joinByHyphen(claims.get("id").toString());
    }

    public UUID getId(HttpServletRequest request) {
        Claims claims = getClaims(request);
        return getId(claims);
    }

    public String getType(HttpServletRequest request) {
        Claims claims = getClaims(request);
        return getType(claims);
    }

}