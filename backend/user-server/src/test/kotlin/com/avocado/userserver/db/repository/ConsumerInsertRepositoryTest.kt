package com.avocado.userserver.db.repository

import com.avocado.userserver.common.error.BaseException
import com.avocado.userserver.common.error.ResponseCode
import com.avocado.userserver.common.utils.ConvertIdUtil
import com.avocado.userserver.db.entity.Consumer
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@WebFluxTest(ProviderRepository::class)
@ExtendWith(SpringExtension::class)
internal class ConsumerInsertRepositoryTest @Autowired constructor(
    private val consumerInsertRepository: ConsumerInsertRepository,
    private val consumerRepository: ConsumerRepository,
    private val convertIdUtil: ConvertIdUtil
) {

    @Test
    fun `지갑 INSERT 테스트`() {
        runBlocking {
            val id:ByteArray = convertIdUtil.unHex("3D82248C9E514BA38EBB00F6A4AA7C78")
            val consumer: Consumer = consumerRepository.findByConsumerId(id)?:throw BaseException(ResponseCode.INVALID_VALUE)
            consumerInsertRepository.insertWallet(consumer)
        }
    }
}