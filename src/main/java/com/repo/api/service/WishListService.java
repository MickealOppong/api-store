package com.repo.api.service;

import com.repo.api.dto.ResponseDto;
import com.repo.api.model.user.Customer;
import com.repo.api.model.wishlist.WishLineItem;
import com.repo.api.repository.WishListLineRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class WishListService {

    private final WishListLineRepository wishLineItemRepository;
    private final PhotoService photoService;
    private final ProductService productService;
    private final CustomerDetailsService customerDetailsService;

    public WishListService(WishListLineRepository wishLineItemRepository,
                           PhotoService photoService, ProductService productService, CustomerDetailsService customerDetailsService) {
        this.wishLineItemRepository = wishLineItemRepository;
        this.photoService = photoService;
        this.productService = productService;
        this.customerDetailsService = customerDetailsService;
    }

    public void updateCartTableOnCustomerLogin(Customer customer, String sessionId){
        for(WishLineItem item :wishLineItemRepository.findAllBySessionId(sessionId)){
            item.setCustomer(customer);
        }
    }

    public ResponseDto<Object> addToWishList(Long productId,Double price,String productName,String sessionId,Long customerId){
        WishLineItem wishLineItem =  wishLineItemRepository.findByProductIdAndCustomerId(productId,customerId)
                .orElse(wishLineItemRepository.findByProductIdAndSessionId(productId,sessionId).orElse(null));
        if(wishLineItem == null){
            WishLineItem item = WishLineItem.builder()
                    .productId(productId)
                    .price(price)
                    .productName(productName)
                    .build();
            wishLineItemRepository.save(item);
            return ResponseDto.builder()
                    .data(null)
                    .httpStatus(HttpStatus.OK)
                    .message("Item added to wish list")
                    .build();
        }
        return ResponseDto.builder()
                .data(null)
                .httpStatus(HttpStatus.OK)
                .message("Item already added to wish list")
                .build();
    }




    public ResponseDto<Object> deleteItemInWishList(Long productId,String sessionId,Long customerId){
        try {
            WishLineItem wishLineItem =  wishLineItemRepository.findByProductIdAndCustomerId(productId,customerId)
                        .orElse(wishLineItemRepository.findByProductIdAndSessionId(productId,sessionId).orElse(null));
            if(wishLineItem != null){
                wishLineItemRepository.delete(wishLineItem);
            }
            return ResponseDto.builder()
                    .data(null)
                    .httpStatus(HttpStatus.OK)
                    .message("Item deleted")
                    .build();
        }catch (Exception e){
            return ResponseDto.builder()
                    .data(null)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
        }
    }



}
