package com.avocado.product.controller;

import com.avocado.product.config.JwtUtil;
import com.avocado.product.dto.request.AddCartReq;
import com.avocado.product.dto.request.RemoveCartReq;
import com.avocado.product.dto.response.BaseResp;
import com.avocado.product.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final JwtUtil jwtUtil;

    @PostMapping("")
    public ResponseEntity<BaseResp> addProductToCart(@RequestBody AddCartReq addCartReq,
                                                     HttpServletRequest request) {
        UUID consumerId = jwtUtil.getId(request);
        cartService.addProductToCart(consumerId, addCartReq);
        return ResponseEntity.ok(BaseResp.of("장바구니 내역 등록 성공"));
    }

    @GetMapping("")
    public ResponseEntity<BaseResp> showMyCart(HttpServletRequest request) {
        UUID consumerId = jwtUtil.getId(request);
        return ResponseEntity.ok(BaseResp.of(
                "장바구니 목록 조회 성공", cartService.showMyCart(consumerId)
        ));
    }

    @DeleteMapping("")
    public ResponseEntity<BaseResp> removeProductFromCart(@RequestBody RemoveCartReq removeCartReq,
                                                          HttpServletRequest request) {
        UUID consumerId = jwtUtil.getId(request);
        cartService.removeProductFromCart(consumerId, removeCartReq.getCart_id());
        return ResponseEntity.ok(BaseResp.of("장바구니 내역 삭제 성공"));
    }
}
