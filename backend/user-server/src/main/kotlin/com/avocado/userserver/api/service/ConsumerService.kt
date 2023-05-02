package com.avocado.userserver.api.service

import com.avocado.userserver.api.dto.KakaoUserInfo
import com.avocado.userserver.common.error.BaseException
import com.avocado.userserver.common.error.ResponseCode
import com.avocado.userserver.db.entity.Consumer
import com.avocado.userserver.db.repository.ConsumerRepository
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ConsumerService(
    private val consumerRepository: ConsumerRepository
) {

    @Transactional(readOnly = true)
    suspend fun getConsumerFromSubAndSocial(sub: String, type: SocialType): Consumer? {
        return consumerRepository.findBySubAndSocial(sub, type)
    }

    @Transactional
    suspend fun save(kakaoUserInfo: KakaoUserInfo):Consumer {
        val consumer = kakaoUserInfo.toConsumer()
        println(consumer)
        // id를 null로 저장하려니까 문제가 생기는 것 같은데..
        consumerRepository.create(consumer)
        return consumer
    }
}