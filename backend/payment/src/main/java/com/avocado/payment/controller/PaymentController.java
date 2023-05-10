package com.avocado.payment.controller;

import com.avocado.payment.dto.request.PurchaseMerchandiseReq;
import com.avocado.payment.dto.request.ReadyForPaymentReq;
import com.avocado.payment.dto.response.BaseResp;
import com.avocado.payment.dto.response.KakaoPayRedirectUrlResp;
import com.avocado.payment.exception.ErrorCode;
import com.avocado.payment.exception.InvalidValueException;
import com.avocado.payment.service.KakaoPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class PaymentController {
    private final KakaoPayService kakaoPayService;

    @Value("${kakao-pay.url.redirect.host}")
    private String completeHost;
    @Value("${kakao-pay.url.redirect.approval}")
    private String completeApproveUrl;
    @Value("${kakao-pay.url.redirect.cancel}")
    private String completeCancelUrl;
    @Value("${kakao-pay.url.redirect.fail}")
    private String completeFailUrl;

    /**
     * 결제 준비하기 (카카오페이 서버로 결제 정보 등록)
     * @param paymentReq : 결제 정보 (합계 금액, 각 상품 정보 등)
     * @return : 모바일 및 PC 결제 URL
     */
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

        KakaoPayRedirectUrlResp kakaoPayRedirectUrlResp = kakaoPayService.ready(paymentReq.getUser_id(), paymentReq);
        return ResponseEntity.ok(BaseResp.of("결제 요청이 완료되었습니다.", kakaoPayRedirectUrlResp));
    }

    /**
     * 사용자가 결제를 완료했을 때 카카오페이 서버로 결제 승인을 요청하는 메서드
     * @param purchasingId : 구매 대기 내역 ID
     * @param pg_token : 카카오페이 서버로 보낼 결제 토큰
     */
    @GetMapping("/approval/{purchasingId}")
    public void approve(HttpServletResponse response,
                        @PathVariable String purchasingId,
                        @RequestParam String pg_token) throws IOException {
        // 승인 요청
        kakaoPayService.approve(purchasingId, pg_token);

        // 성공 페이지로 리다이렉트
        response.sendRedirect(completeHost + completeApproveUrl);
    }

    /**
     * 결제 취소 시 실행되는 메서드
     * @param purchasingId : 구매 대기 내역 ID
     */
    @GetMapping("/cancel/{purchasingId}")
    public void cancel(HttpServletResponse response,
                       @PathVariable String purchasingId) throws IOException {
        // 취소 처리
        kakaoPayService.cancel(purchasingId);

        // 취소 페이지로 리다이렉트
        response.sendRedirect(completeHost + completeCancelUrl);
    }

    /**
     * 결제 실패 시 실행되는 메서드
     * @param purchasingId : 구매 대기 내역 ID
     */
    @GetMapping("/fail/{purchasingId}")
    public void fail(HttpServletResponse response,
                     @PathVariable String purchasingId) throws IOException {
        // 실패 처리
        kakaoPayService.fail(purchasingId);

        // 실패 페이지로 리다이렉트
        response.sendRedirect(completeHost + completeFailUrl);
    }
}
