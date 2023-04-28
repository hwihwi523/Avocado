package com.avocado.product.controller;

import com.avocado.product.config.UUIDUtil;
import com.avocado.product.dto.request.WishlistReq;
import com.avocado.product.dto.response.BaseResp;
import com.avocado.product.repository.StoreRepository;
import com.avocado.product.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/wishlists")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;
    private final UUIDUtil uuidUtil;

    @PostMapping("")
    public BaseResp addProductToWishlist(@RequestBody WishlistReq wishlistReq) {
        UUID consumerId = uuidUtil.joinByHyphen(wishlistReq.getUser_id());
        wishlistService.addProductToWishlist(wishlistReq.getMerchandise_id(), consumerId);
        return BaseResp.of("찜 성공");
    }

    @DeleteMapping("")
    public BaseResp removeProductFromWishlist(@RequestBody WishlistReq wishlistReq) {
        UUID consumerId = uuidUtil.joinByHyphen(wishlistReq.getUser_id());
        wishlistService.removeProductFromWishList(wishlistReq.getMerchandise_id(), consumerId);
        return BaseResp.of("찜 해제 성공");
    }
}
