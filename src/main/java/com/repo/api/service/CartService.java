package com.repo.api.service;

import com.repo.api.dto.ResponseDto;
import com.repo.api.dto.request.CartRequest;
import com.repo.api.dto.response.CartDto;
import com.repo.api.dto.response.CartLineItemsDto;
import com.repo.api.model.cart.CartLineItem;
import com.repo.api.model.cart.CartTable;
import com.repo.api.model.user.Customer;
import com.repo.api.repository.CartLineItemRepository;
import com.repo.api.repository.CartTableRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    private final CartTableRepository cartTableRepository;
    private final CartLineItemRepository cartLineItemRepository;
    private final PhotoService photoService;
    private final ProductService productService;
   private final CustomerDetailsService customerDetailsService;

    public CartService(CartTableRepository cartTableRepository, PhotoService photoService, ProductService productService,CustomerDetailsService
                        customerDetailsService,CartLineItemRepository cartLineItemRepository) {
        this.cartTableRepository = cartTableRepository;
        this.photoService = photoService;
        this.productService = productService;
        this.customerDetailsService =customerDetailsService;
        this.cartLineItemRepository = cartLineItemRepository;
    }


    public ResponseDto<Object> addToCart(CartRequest cartRequest){
        try{

            CartTable customerCart=cartTableRepository.findByCustomerId(cartRequest.getCustomerId()).orElse(null);
            CartTable sessionCart=cartTableRepository.findBySessionId(cartRequest.getSessionId()).orElse(null);
            //cart already exist
            if(customerCart!=null){

                for(CartLineItem cartLineItem :cartLineItemRepository.findByCartTableCartId(customerCart.getCartId())){
                    if(cartLineItem.getProductId().equals(cartRequest.getProductId())) {
                        //update quantity
                        cartLineItem.setQuantity(cartRequest.getQuantity());
                        //update database
                        cartLineItemRepository.save(cartLineItem);
                        //return updated record
                        return ResponseDto.builder()
                                .data(null)
                                .httpStatus(HttpStatus.CREATED)
                                .message("Cart created")
                                .build();
                    }
                }
                CartLineItem cartLine = CartLineItem.builder()
                        .cartTable(customerCart)
                        .productName(cartRequest.getProductName())
                        .includeItem(true)
                        .price(cartRequest.getPrice())
                        .productName(cartRequest.getProductName())
                        .quantity(cartRequest.getQuantity())
                        .productId(cartRequest.getProductId())
                        .build();
                cartLineItemRepository.save(cartLine);
             return ResponseDto.builder()
                     .data(null)
                     .httpStatus(HttpStatus.CREATED)
                     .message("Cart created")
                     .build();
             }else if(sessionCart!=null){

                for(CartLineItem cartLineItem :cartLineItemRepository.findByCartTableCartId(sessionCart.getCartId())){
                    if(cartLineItem.getProductId().equals(cartRequest.getProductId())) {
                        //update quantity
                        cartLineItem.setQuantity(cartRequest.getQuantity());
                        //update database
                        cartLineItemRepository.save(cartLineItem);
                        //return updated record
                        return ResponseDto.builder()
                                .data(null)
                                .httpStatus(HttpStatus.CREATED)
                                .message("Cart created")
                                .build();
                    }
                }
                CartLineItem cartLine = CartLineItem.builder()
                        .cartTable(sessionCart)
                        .productName(cartRequest.getProductName())
                        .includeItem(true)
                        .price(cartRequest.getPrice())
                        .quantity(cartRequest.getQuantity())
                        .productName(cartRequest.getProductName())
                        .productId(cartRequest.getProductId())
                        .build();
                cartLineItemRepository.save(cartLine);
                return ResponseDto.builder()
                        .data(null)
                        .httpStatus(HttpStatus.CREATED)
                        .message("Cart created")
                        .build();
            }else{
                Customer customer = customerDetailsService.getCustomerById(cartRequest.getCustomerId());
                CartTable cartTable = CartTable.builder()
                        .customer(customer)
                        .sessionId(cartRequest.getSessionId())
                        .includeAllItems(true)
                        .build();
               CartTable savedRecord = cartTableRepository.save(cartTable);
                CartLineItem cartLine = CartLineItem.builder()
                        .cartTable(savedRecord)
                        .productName(cartRequest.getProductName())
                        .includeItem(true)
                        .price(cartRequest.getPrice())
                        .productName(cartRequest.getProductName())
                        .quantity(cartRequest.getQuantity())
                        .productId(cartRequest.getProductId())
                        .build();
                cartLineItemRepository.save(cartLine);
                return ResponseDto.builder()
                        .data(null)
                        .httpStatus(HttpStatus.CREATED)
                        .message("Cart created")
                        .build();
            }

        }catch (Exception e){
            return ResponseDto.builder()
                    .data(null)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
        }
    }

    public ResponseDto<Object> userCartItems(Long customerId,String sessionId){
        try {
            List<CartLineItemsDto> cartList = new ArrayList<>();
          CartTable customerCart= cartTableRepository.findByCustomerId(customerId).orElse(null);
            CartDto cartDto;
          if(customerCart!=null){

               cartDto = CartDto.builder()
                      .cartId(customerCart.getCartId())
                      .customerId(customerCart.getCustomer().getId())
                      .includeAllItems(customerCart.isIncludeAllItems())
                      .build();


              for(CartLineItem cartLineItem:cartLineItemRepository.findByCartTableCartId(customerCart.getCartId())){
                  CartLineItemsDto cartLineItemsDto = CartLineItemsDto.builder()
                          .includeItem(cartLineItem.isIncludeItem())
                          .price(cartLineItem.getPrice())
                          .productId(cartLineItem.getProductId())
                          .productName(cartLineItem.getProductName())
                          .recId(cartLineItem.getRecId())
                          .images(photoService.getProductImages(cartLineItem.getProductId()))
                          .build();
                  cartList.add(cartLineItemsDto);

              }
              cartDto.setLineItems(cartList);

              return ResponseDto.builder()
                      .data(cartDto)
                      .httpStatus(HttpStatus.BAD_REQUEST)
                      .message("Success")
                      .build();
          }
          CartTable sessionCart=cartTableRepository.findBySessionId(sessionId).orElse(null);
            if(sessionCart!=null){

                cartDto = CartDto.builder()
                        .cartId(sessionCart.getCartId())
                        .includeAllItems(sessionCart.isIncludeAllItems())
                        .build();

                for(CartLineItem cartLineItem:cartLineItemRepository.findByCartTableCartId(sessionCart.getCartId())){
                    CartLineItemsDto cartLineItemsDto = CartLineItemsDto.builder()
                            .includeItem(cartLineItem.isIncludeItem())
                            .price(cartLineItem.getPrice())
                            .productId(cartLineItem.getProductId())
                            .productName(cartLineItem.getProductName())
                            .recId(cartLineItem.getRecId())
                            .images(photoService.getProductImages(cartLineItem.getProductId()))
                            .build();
                    cartList.add(cartLineItemsDto);
                }
                cartDto.setLineItems(cartList);
                return ResponseDto.builder()
                        .data(cartDto)
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("Success")
                        .build();
            }
            return ResponseDto.builder()
                    .data(null)
                    .httpStatus(HttpStatus.OK)
                    .message("Cart is empty")
                    .build();
        }catch (Exception e){
            return ResponseDto.builder()
                    .data(null)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
        }
    }

    public Long totalItemsInCart(String sessionId,Long customerId){
        CartTable cartTable = cartTableRepository.findBySessionId(sessionId).orElse(cartTableRepository.findByCustomerId(customerId).orElse(null));
        Long quantity = 0L;
        if(cartTable !=null){
                for(CartLineItem cartLineItem:cartLineItemRepository.findByCartTableCartId(cartTable.getCartId())){
                    quantity +=cartLineItem.getQuantity();
                }
        }
        return quantity;
    }
}
