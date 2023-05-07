package com.avocado.product.controller;

import com.avocado.product.config.UUIDUtil;
import com.avocado.product.dto.request.AddReviewReq;
import com.avocado.product.dto.request.RemoveReviewReq;
import com.avocado.product.dto.response.BaseResp;
import com.avocado.product.dto.response.DetailMerchandiseResp;
import com.avocado.product.dto.response.ReviewResp;
import com.avocado.product.service.MerchandiseService;
import com.avocado.product.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/merchandises/{merchandise_id}")
public class DetailMerchandiseController {
    private final MerchandiseService merchandiseService;
    private final ReviewService reviewService;
    private final UUIDUtil uuidUtil;

    @GetMapping("")
    public ResponseEntity<BaseResp> showDetailMerchandise(@PathVariable Long merchandise_id, @RequestParam @Nullable String user_id) {
        UUID consumerId = user_id != null ? uuidUtil.joinByHyphen(user_id) : null;
        DetailMerchandiseResp detailMerchandiseResp = merchandiseService.showDetailMerchandise(consumerId, merchandise_id);
        return ResponseEntity.ok(BaseResp.of("상품 상세정보 조회 성공", detailMerchandiseResp));
    }

    @PostMapping("/reviews")
    public ResponseEntity<BaseResp> createReview(@PathVariable Long merchandise_id,
                                                 @RequestBody AddReviewReq addReviewReq) {
        UUID reviewerId = uuidUtil.joinByHyphen(addReviewReq.getUser_id());
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
                                                 @RequestBody RemoveReviewReq removeReviewReq) {
        UUID consumerId = uuidUtil.joinByHyphen(removeReviewReq.getUser_id());
        reviewService.removeReview(consumerId, removeReviewReq.getReview_id());
        return ResponseEntity.ok(BaseResp.of("리뷰가 삭제되었습니다."));
    }
}
