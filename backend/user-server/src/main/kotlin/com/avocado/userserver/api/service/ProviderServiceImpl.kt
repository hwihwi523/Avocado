package com.avocado.userserver.api.service

import com.avocado.userserver.api.request.ProviderLoginReq
import com.avocado.userserver.api.response.ProviderLoginResp
import com.avocado.userserver.db.entity.Provider
import com.avocado.userserver.db.repository.ProviderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.RuntimeException

@Service
class ProviderServiceImpl(
    @Autowired val providerRepository: ProviderRepository
):ProviderService {

    /**
     * 로그인 함수
     * @param req
     * @return resp
     */
    override suspend fun login(req: ProviderLoginReq): ProviderLoginResp {
        
        // 1. 이메일을 바탕으로 정보 가져오기 (없으면 LoginEmailException 내기)
        val provider: Provider = providerRepository.findByEmail(req.email) ?: throw RuntimeException("존재하지 않는 아이디입니다.")
        
        // 2. 비밀번호 hash로 변환해서 일치하는지 확인하기. (일치하지 않으면 LoginPasswordException 내기)

        
        
        // 3. JWT 토큰 발급하기

    }
}