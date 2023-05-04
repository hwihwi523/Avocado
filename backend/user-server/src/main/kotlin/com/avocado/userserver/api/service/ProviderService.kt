package com.avocado.userserver.api.service

import com.avocado.userserver.api.controller.ProviderController
import com.avocado.userserver.api.request.ProviderLoginReq
import com.avocado.userserver.api.response.ProviderLoginResp
import com.avocado.userserver.common.error.BaseException
import com.avocado.userserver.common.error.ResponseCode
import com.avocado.userserver.db.entity.Provider
import com.avocado.userserver.db.repository.ProviderRepository
import org.mindrot.jbcrypt.BCrypt
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProviderService(
    @Autowired val providerRepository: ProviderRepository,
    @Autowired val jwtProvider: JwtProvider
) {
    val log: Logger = LoggerFactory.getLogger(ProviderService::class.java)
    
    /**
     * 로그인 함수
     * @param req
     * @return resp
     */
    suspend fun login(req: ProviderLoginReq): ProviderLoginResp {
        log.info("Provider 로그인 과정 시작")
        
        // 1. 이메일을 바탕으로 정보 가져오기 (없으면 LoginEmailException 내기)
        val provider: Provider = providerRepository.findByEmail(req.email) ?: throw BaseException(ResponseCode.INVALID_LOGIN)
        
        // 2. 비밀번호 hash 로 변환해서 일치하지 않으면 Exception 처리
        if (!BCrypt.checkpw(req.password, provider.password)) {
            throw BaseException(ResponseCode.INVALID_LOGIN)
        }
        log.info("Provider JWT 토큰 발급 시작")
        // 3. JWT 토큰 발급하기
        val accessToken = jwtProvider.getAccessToken(provider)
        val refreshToken = jwtProvider.getRefreshToken(provider)

        return ProviderLoginResp(accessToken, refreshToken, provider)
    }
}