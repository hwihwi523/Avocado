package com.avocado.userserver.api.service

import com.avocado.userserver.api.dto.KakaoUserInfo
import com.avocado.userserver.api.request.ConsumerNotRequiredInfoReq
import com.avocado.userserver.api.request.ConsumerPersonalColorReq
import com.avocado.userserver.api.request.ConsumerRequiredInfoReq
import com.avocado.userserver.api.request.ConsumerUpdateReq
import com.avocado.userserver.common.error.BaseException
import com.avocado.userserver.common.error.ResponseCode
import com.avocado.userserver.common.utils.ConvertIdUtil
import com.avocado.userserver.common.utils.OAuthUrlUtil
import com.avocado.userserver.db.entity.Consumer
import com.avocado.userserver.db.entity.Wallet
import com.avocado.userserver.db.repository.ConsumerInsertRepository
import com.avocado.userserver.db.repository.ConsumerRepository
import com.avocado.userserver.db.repository.WalletRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import io.jsonwebtoken.Claims
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.net.URI

@Service
class ConsumerService(
    private val consumerRepository: ConsumerRepository,
    private val consumerInsertRepository: ConsumerInsertRepository,
    private val walletRepository: WalletRepository,
    private val oauthService: OauthService,
    private val oAuthUrlUtil: OAuthUrlUtil,
    private val convertIdUtil: ConvertIdUtil,
    private val jwtProvider: JwtProvider,
) {
    val log: Logger = LoggerFactory.getLogger(ConsumerService::class.java)

    @Transactional(readOnly = true)
    suspend fun getConsumerFromSubAndSocial(sub: String, type: SocialType): Consumer? {
        return consumerRepository.findBySubAndSocial(sub, type)
    }



    suspend fun loginOrSignUp(code: String): URI {
        // 1. 카카오 서버에서 유저 정보 가져오기
        val userInfo: KakaoUserInfo = oauthService.getUserInfoKakao(code)

        // 2. consumer DB에 해당 유저 정보가 있는지 확인하고, 없다면 저장하기
        val consumer: Consumer = getConsumerFromSubAndSocial(userInfo.sub, SocialType.KAKAO)
            ?:save(userInfo)

        // 3. 토큰 발급
        val accessToken = jwtProvider.getAccessToken(consumer)
        val refreshToken = jwtProvider.getRefreshToken(consumer)
        println("accessToken: $accessToken, refreshToken: $refreshToken")
        return oAuthUrlUtil.getFrontRedirectUrl(accessToken, refreshToken, consumer.auth)
    }


    @Transactional
    suspend fun save(kakaoUserInfo: KakaoUserInfo):Consumer {
        val consumer = kakaoUserInfo.toConsumer()
        log.info("카카오 정보를 바탕으로 소비자 정보 구성. consumer: {}", consumer)
        consumerInsertRepository.insert(consumer)
        consumerInsertRepository.insertWallet(consumer)
        
        // TODO - kafka로 새로 회원가입 했음을 알리기
        consumer.consumerId // 얘 보내기
        
        return consumer
    }

    @Transactional
    suspend fun putRequiredInfo(req: ConsumerRequiredInfoReq, claims: Claims) {
        val consumer = consumerRepository.findById(jwtProvider.getId(claims))?:throw BaseException(ResponseCode.NOT_FOUND_VALUE)
        val updatedConsumer = consumer.updateRequiredInfo(req)
        consumerRepository.save(updatedConsumer)
    }

    @Transactional
    suspend fun putNotRequiredInfo(req: ConsumerNotRequiredInfoReq, claims: Claims) {
        val consumer = consumerRepository.findById(jwtProvider.getId(claims))?:throw BaseException(ResponseCode.NOT_FOUND_VALUE)
        val updatedConsumer = consumer.updateNotRequiredInfo(req)
        consumerRepository.save(updatedConsumer)
    }

    @Transactional
    suspend fun updatePersonalColor(req: ConsumerPersonalColorReq, claims: Claims) {
        val consumer = consumerRepository.findById(jwtProvider.getId(claims))?:throw BaseException(ResponseCode.NOT_FOUND_VALUE)
        val updatedConsumer = consumer.updatePersonalColor(req)
        consumerRepository.save(updatedConsumer)
    }

    @Transactional
    suspend fun updateInfo(req: ConsumerUpdateReq, claims: Claims) {
        val consumer = consumerRepository.findById(jwtProvider.getId(claims))?:throw BaseException(ResponseCode.NOT_FOUND_VALUE)
        val updatedConsumer = consumer.updateInfo(req)
        consumerRepository.save(updatedConsumer)
        
        // TODO - Kafka로 회원정보 수정 알리기
//        consumer.consumerId
//        consumer.ageGroup
//        consumer.gender
//        consumer.name
//        consumer.mbtiId
//        consumer.personalColorId
    }

    @Transactional
    suspend fun updateWallet(totalPrice: Long, userId: String) {
        val consumerId = convertIdUtil.unHex(userId.replace("-",""))
        val wallet: Wallet = walletRepository.findByConsumerId(consumerId)?:throw BaseException(ResponseCode.NOT_FOUND_VALUE)
        val updatedWallet = wallet.updateTotalExpense(totalPrice)
        walletRepository.save(updatedWallet)
    }

    @Transactional
    suspend fun deleteConsumer(claims: Claims) {
        val consumerId = jwtProvider.getId(claims)
        consumerRepository.deleteById(consumerId)
        // TODO -  Kafka로 회원 탈퇴 처리 알리기
        //  consumerId 보내기
    }
}