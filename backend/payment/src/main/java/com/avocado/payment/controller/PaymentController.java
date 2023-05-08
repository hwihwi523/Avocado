package com.avocado.payment.controller;

import com.avocado.payment.dto.request.PurchaseMerchandiseReq;
import com.avocado.payment.dto.request.ReadyForPaymentReq;
import com.avocado.payment.dto.response.BaseResp;
import com.avocado.payment.dto.response.KakaoPayRedirectUrlResp;
import com.avocado.payment.exception.ErrorCode;
import com.avocado.payment.exception.InvalidValueException;
import com.avocado.payment.service.KakaoPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class PaymentController {
    private final KakaoPayService kakaoPayService;

    @PostMapping("/ready")
    public ResponseEntity<BaseResp> ready(@RequestBody ReadyForPaymentReq paymentReq) {
        List<PurchaseMerchandiseReq> merchandises = paymentReq.getMerchandises();

        // 구매 요청한 상품이 없을 때
        if (merchandises == null || merchandises.isEmpty())
            throw new InvalidValueException(ErrorCode.NO_MERCHANDISE);

        // 총 금액과 각 상품의 합계가 다를 때
        long totalPrice = 0L;
        for (PurchaseMerchandiseReq merchandiseReq : merchandises)
            totalPrice += merchandiseReq.getPrice() * merchandiseReq.getQuantity();
        if (paymentReq.getTotal_price() == null || !paymentReq.getTotal_price().equals(totalPrice))
            throw new InvalidValueException(ErrorCode.NOT_SAME_WITH_TOTAL_PRICE);

        KakaoPayRedirectUrlResp kakaoPayRedirectUrlResp = kakaoPayService.ready(paymentReq);
        return ResponseEntity.ok(BaseResp.of("결제 요청이 완료되었습니다.", kakaoPayRedirectUrlResp));
    }
}
