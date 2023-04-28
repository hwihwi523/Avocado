package com.avocado.product.controller;

import com.avocado.product.config.UUIDUtil;
import com.avocado.product.dto.request.CartReq;
import com.avocado.product.dto.response.BaseResp;
import com.avocado.product.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final UUIDUtil uuidUtil;

    @PostMapping("")
    public ResponseEntity<BaseResp> addProductToCart(@RequestBody CartReq cartReq) {
        UUID consumerId = uuidUtil.joinByHyphen(cartReq.getUser_id());
        cartService.addProductToCart(consumerId, cartReq.getMerchandise_id());
        return ResponseEntity.ok(BaseResp.of("장바구니 내역 등록 성공"));
    }

    @GetMapping("")
    public ResponseEntity<BaseResp> showMyCart(@RequestBody Map<String, String> req) {
        UUID consumerId = uuidUtil.joinByHyphen(req.get("user_id"));
        return ResponseEntity.ok(BaseResp.of(
                "장바구니 목록 조회 성공", cartService.showMyCart(consumerId)
        ));
    }

    @DeleteMapping("")
    public ResponseEntity<BaseResp> removeProductFromCart(@RequestBody CartReq cartReq) {
        UUID consumerId = uuidUtil.joinByHyphen(cartReq.getUser_id());
        cartService.removeProductFromCart(consumerId, cartReq.getMerchandise_id());
        return ResponseEntity.ok(BaseResp.of("장바구니 내역 삭제 성공"));
    }
}
