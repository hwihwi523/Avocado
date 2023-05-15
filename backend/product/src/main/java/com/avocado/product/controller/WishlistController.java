package com.avocado.product.controller;

import com.avocado.product.config.JwtUtil;
import com.avocado.product.dto.request.AddWishlistReq;
import com.avocado.product.dto.request.RemoveWishlistReq;
import com.avocado.product.dto.response.BaseResp;
import com.avocado.product.service.WishlistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/wishlists")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;
    private final JwtUtil jwtUtil;

    @PostMapping("")
    public ResponseEntity<BaseResp> addProductToWishlist(@RequestBody AddWishlistReq addWishlistReq,
                                         HttpServletRequest request) {
        UUID consumerId = jwtUtil.getId(request);
        wishlistService.addProductToWishlist(addWishlistReq.getMerchandise_id(), consumerId);
        return ResponseEntity.ok(BaseResp.of("찜 성공"));
    }

    @GetMapping("")
    public ResponseEntity<BaseResp> showMyCart(HttpServletRequest request) {
        UUID consumerId = jwtUtil.getId(request);
        return ResponseEntity.ok(BaseResp.of(
                "찜 목록 조회 성공", wishlistService.showMyWishlist(consumerId)
        ));
    }

    @DeleteMapping("")
    public ResponseEntity<BaseResp> removeProductFromWishlist(@RequestBody RemoveWishlistReq removeWishlistReq,
                                              HttpServletRequest request) {
        UUID consumerId = jwtUtil.getId(request);
        wishlistService.removeProductFromWishList(consumerId, removeWishlistReq.getMerchandise_id());
        return ResponseEntity.ok(BaseResp.of("찜 해제 성공"));
    }

    @GetMapping("/exists")
    public ResponseEntity<BaseResp> testExistsWishlist(@RequestParam String merchandise_name,
                                                       HttpServletRequest request) {
        log.info("testExistsWishlist >> \"" + merchandise_name + "\"");
        UUID consumerId = jwtUtil.getId(request);
        log.info("testExistsWishlist >> \"" + consumerId + "\"");
        boolean exists = wishlistService.testExistsWishlist(consumerId, merchandise_name);
        return ResponseEntity.ok(BaseResp.of("찜 존재 여부 확인", exists));
    }
}
