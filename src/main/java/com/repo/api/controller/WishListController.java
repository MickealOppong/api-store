package com.repo.api.controller;

import com.repo.api.dto.ResponseDto;
import com.repo.api.service.WishListService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/wishlist")
@RestController
public class WishListController {

    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @PostMapping("/add")
    public ResponseDto<Object> addToList(Long productId,Double price,String productName,String sessionId,Long customerId){
        return wishListService.addToWishList(productId,price,productName,sessionId,customerId);
    }

    @DeleteMapping("/remove")
    public ResponseDto<Object> removeToList(Long productId,Double price,String productName,String sessionId,Long customerId){
        return wishListService.deleteItemInWishList(productId,sessionId,customerId);
    }
}
