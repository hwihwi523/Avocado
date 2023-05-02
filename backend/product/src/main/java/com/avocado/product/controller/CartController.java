package com.avocado.product.controller;

import com.avocado.product.config.UUIDUtil;
import com.avocado.product.dto.request.AddCartReq;
import com.avocado.product.dto.request.RemoveCartReq;
import com.avocado.product.dto.response.BaseResp;
import com.avocado.product.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final UUIDUtil uuidUtil;

    @PostMapping("")
    public ResponseEntity<BaseResp> addProductToCart(@RequestBody AddCartReq addCartReq) {
        UUID consumerId = uuidUtil.joinByHyphen(addCartReq.getUser_id());
        cartService.addProductToCart(consumerId, addCartReq.getMerchandise_id());
        return ResponseEntity.ok(BaseResp.of("장바구니 내역 등록 성공"));
    }

    @GetMapping("")
    public ResponseEntity<BaseResp> showMyCart(@RequestParam String user_id) {
        UUID consumerId = uuidUtil.joinByHyphen(user_id);
        return ResponseEntity.ok(BaseResp.of(
                "장바구니 목록 조회 성공", cartService.showMyCart(consumerId)
        ));
    }

    @DeleteMapping("")
    public ResponseEntity<BaseResp> removeProductFromCart(@RequestBody RemoveCartReq removeCartReq) {
        UUID consumerId = uuidUtil.joinByHyphen(removeCartReq.getUser_id());
        cartService.removeProductFromCart(consumerId, removeCartReq.getCart_id());
        return ResponseEntity.ok(BaseResp.of("장바구니 내역 삭제 성공"));
    }
}
