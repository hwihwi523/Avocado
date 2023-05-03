package com.avocado.userserver.api.service

import com.avocado.userserver.api.dto.KakaoUserInfo
import com.avocado.userserver.db.entity.Consumer
import com.avocado.userserver.db.repository.ConsumerInsertRepository
import com.avocado.userserver.db.repository.ConsumerRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ConsumerService(
    private val consumerRepository: ConsumerRepository,
    private val consumerInsertRepository: ConsumerInsertRepository
) {

    @Transactional(readOnly = true)
    suspend fun getConsumerFromSubAndSocial(sub: String, type: SocialType): Consumer? {
        return consumerRepository.findBySubAndSocial(sub, type)
    }

    @Transactional
    suspend fun save(kakaoUserInfo: KakaoUserInfo):Consumer {
        val consumer = kakaoUserInfo.toConsumer()
        println(consumer)
        consumerInsertRepository.insert(consumer)
        return consumer
    }
}