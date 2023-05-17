package com.avocado.userserver.api.service

import com.avocado.EventType
import com.avocado.MemberEvent
import com.avocado.SignupInfo
import com.avocado.UpdateInfo
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
import com.avocado.userserver.kafka.service.KafkaProducer
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
    private val kafkaProducer: KafkaProducer,
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
        
        // kafka로 이벤트 날리기
        val consumerId:String = convertIdUtil.hex(consumer.consumerId)
        val signupInfo = SignupInfo.newBuilder()
            .setConsumerName(consumer.name)
            .setPictureUrl(consumer.pictureUrl).build()
        val memberEvent = MemberEvent.newBuilder()
            .setSignupInfo(signupInfo)
            .setEvent(EventType.SIGN_UP).build()
        kafkaProducer.sendMemberEvent(consumerId, memberEvent)

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

        // kafka로 이벤트 날리기
        val consumerId: String = convertIdUtil.hex(updatedConsumer.consumerId)
        val info: UpdateInfo = UpdateInfo.newBuilder()
            .setAgeGroup(updatedConsumer.ageGroup?:throw BaseException(ResponseCode.INVALID_VALUE))
            .setGender(updatedConsumer.gender)
            .setConsumerName(updatedConsumer.name)
            .setMbtiId(updatedConsumer.mbtiId)
            .setPersonalColorId(updatedConsumer.personalColorId).build()
        val memberEvent = MemberEvent.newBuilder()
            .setEvent(EventType.UPDATE)
            .setUpdateInfo(info)
            .build()
        kafkaProducer.sendMemberEvent(consumerId, memberEvent)

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
        
        // kafka 로 회원 탈퇴했음을 알리기
        val id:String = convertIdUtil.hex(consumerId)
        val memberEvent = MemberEvent.newBuilder()
            .setEvent(EventType.SIGN_OUT).build()
        kafkaProducer.sendMemberEvent(id, memberEvent)

    }
}