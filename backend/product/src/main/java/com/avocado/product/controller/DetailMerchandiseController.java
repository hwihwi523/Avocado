package com.avocado.product.controller;

import com.avocado.product.config.JwtUtil;
import com.avocado.product.dto.request.AddReviewReq;
import com.avocado.product.dto.request.RemoveReviewReq;
import com.avocado.product.dto.response.BaseResp;
import com.avocado.product.dto.response.DetailMerchandiseResp;
import com.avocado.product.dto.response.ReviewResp;
import com.avocado.product.exception.BusinessLogicException;
import com.avocado.product.service.MerchandiseService;
import com.avocado.product.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/merchandises/{merchandise_id}")
@Slf4j
public class DetailMerchandiseController {
    private final MerchandiseService merchandiseService;
    private final ReviewService reviewService;
    private final JwtUtil jwtUtil;

    @GetMapping("")
    public ResponseEntity<BaseResp> showDetailMerchandise(@PathVariable Long merchandise_id,
                                                          HttpServletRequest request) {
        UUID consumerId;
        try {  // 토큰이 있을 경우
            consumerId = jwtUtil.getId(request);
        } catch (BusinessLogicException e) {  // 토큰이 없을 경우
            consumerId = null;
        }

        DetailMerchandiseResp detailMerchandiseResp = merchandiseService.showDetailMerchandise(consumerId, merchandise_id);
        if (consumerId != null)
            merchandiseService.addClick(consumerId, merchandise_id);
        return ResponseEntity.ok(BaseResp.of("상품 상세정보 조회 성공", detailMerchandiseResp));
    }

    @PostMapping("/reviews")
    public ResponseEntity<BaseResp> createReview(@PathVariable Long merchandise_id,
                                                 @RequestBody AddReviewReq addReviewReq,
                                                 HttpServletRequest request) {
        UUID reviewerId = jwtUtil.getId(request);
        reviewService.createReview(reviewerId, merchandise_id, addReviewReq.getScore(), addReviewReq.getContent());
        return ResponseEntity.ok(BaseResp.of("리뷰가 등록되었습니다."));
    }

    @GetMapping("/reviews")
    public ResponseEntity<BaseResp> searchReviews(@PathVariable Long merchandise_id) {
        List<ReviewResp> reviews = reviewService.searchReviews(merchandise_id);
        return ResponseEntity.ok(BaseResp.of("리뷰를 조회했습니다.", reviews));
    }

    @DeleteMapping("/reviews")
    public ResponseEntity<BaseResp> removeReview(@PathVariable Long merchandise_id,
                                                 @RequestBody RemoveReviewReq removeReviewReq,
                                                 HttpServletRequest request) {
        UUID consumerId = jwtUtil.getId(request);
        reviewService.removeReview(consumerId, removeReviewReq.getReview_id());
        return ResponseEntity.ok(BaseResp.of("리뷰가 삭제되었습니다."));
    }
}
