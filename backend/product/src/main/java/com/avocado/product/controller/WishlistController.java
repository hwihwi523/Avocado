package com.avocado.product.controller;

import com.avocado.product.config.UUIDUtil;
import com.avocado.product.dto.request.AddWishlistReq;
import com.avocado.product.dto.request.RemoveWishlistReq;
import com.avocado.product.dto.response.BaseResp;
import com.avocado.product.service.WishlistService;
import lombok.RequiredArgsConstructor;
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

    @DeleteMapping("")
    public BaseResp removeProductFromWishlist(@RequestBody RemoveWishlistReq removeWishlistReq) {
        UUID consumerId = uuidUtil.joinByHyphen(removeWishlistReq.getUser_id());
        wishlistService.removeProductFromWishList(consumerId, removeWishlistReq.getWishlist_id());
        return BaseResp.of("찜 해제 성공");
    }
}
