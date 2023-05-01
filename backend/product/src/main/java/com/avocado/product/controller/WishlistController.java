package com.avocado.product.controller;

import com.avocado.product.config.UUIDUtil;
import com.avocado.product.dto.request.AddWishlistReq;
import com.avocado.product.dto.request.RemoveWishlistReq;
import com.avocado.product.dto.response.BaseResp;
import com.avocado.product.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/wishlists")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;
    private final UUIDUtil uuidUtil;

    @PostMapping("")
    public BaseResp addProductToWishlist(@RequestBody AddWishlistReq addWishlistReq) {
        UUID consumerId = uuidUtil.joinByHyphen(addWishlistReq.getUser_id());
        wishlistService.addProductToWishlist(addWishlistReq.getMerchandise_id(), consumerId);
        return BaseResp.of("찜 성공");
    }

    @GetMapping("")
    public ResponseEntity<BaseResp> showMyCart(@RequestParam String user_id) {
        UUID consumerId = uuidUtil.joinByHyphen(user_id);
        return ResponseEntity.ok(BaseResp.of(
                "찜 목록 조회 성공", wishlistService.showMyWishlist(consumerId)
        ));
    }

    @DeleteMapping("")
    public BaseResp removeProductFromWishlist(@RequestBody RemoveWishlistReq removeWishlistReq) {
        UUID consumerId = uuidUtil.joinByHyphen(removeWishlistReq.getUser_id());
        wishlistService.removeProductFromWishList(consumerId, removeWishlistReq.getWishlist_id());
        return BaseResp.of("찜 해제 성공");
    }
}
