package com.avocado.payment;

import com.avocado.payment.config.UUIDUtil;
import com.avocado.payment.dto.request.PurchaseMerchandiseReq;
import com.avocado.payment.dto.request.ReadyForPaymentReq;
import com.avocado.payment.entity.Merchandise;
import com.avocado.payment.exception.ErrorCode;
import com.avocado.payment.exception.KakaoPayException;
import com.avocado.payment.repository.MerchandiseRepository;
import com.avocado.payment.service.KakaoPayService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PaymentApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private KakaoPayService kakaoPayService;
    @Autowired
    private MerchandiseRepository merchandiseRepository;
    @Autowired
    private UUIDUtil uuidUtil;
    @Test
    void 동시성_재고100개_1개씩() throws InterruptedException {
        int maxCount = 300;
        int threadCount = 300;
        long mdId = 0L;

        // DTO 생성
        List<PurchaseMerchandiseReq> merchandises = new ArrayList<>();
        merchandises.add(
                PurchaseMerchandiseReq.builder()
                        .merchandise_id(mdId)
                        .price(3000L)
                        .quantity(1)
                        .build()
        );
        ReadyForPaymentReq req = ReadyForPaymentReq.builder()
                .merchandises(merchandises)
                .total_price(3000L)
                .build();

        // 쓰레드 풀 생성
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // 쓰레드 실행
        for (int i = 0; i < threadCount; i++) {
            executorService.execute(() -> {
                kakaoPayService.testPay(uuidUtil.joinByHyphen("2fe2b6febfbf40d49517b2af0d6e0052"), req);
                latch.countDown();
            });
        } latch.await();

        Merchandise merchandise = merchandiseRepository.findById(mdId)
                .orElseThrow(() -> new KakaoPayException(ErrorCode.APPROVE_ERROR));
        assertThat(merchandise.getInventory()).isEqualTo(maxCount - threadCount);
    }
}
