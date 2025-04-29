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


    public void updateCartTableOnCustomerLogin(Customer customer,String sessionId){
        cartTableRepository.findBySessionId(sessionId).ifPresent(cartTable -> cartTable.setCustomer(customer));
    }

    public ResponseDto<Object> addToCart(CartRequest cartRequest){
        try{

            CartTable customerCart=cartTableRepository.findByCustomerId(cartRequest.getCustomerId()).orElse(null);
            CartTable sessionCart=cartTableRepository.findBySessionId(cartRequest.getSessionId())
                    .orElse(null);
            //check for cart using customer id
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
             }
            //check for cart using session
           if(sessionCart!=null){

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
                                .message("Cart updated")
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
                       .message("Cart updated")
                       .build();
            }

           Customer customer = customerDetailsService.getCustomer(cartRequest.getCustomerId());
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
                       .sessionId(customerCart.getSessionId())
                      .includeAllItems(customerCart.isIncludeAllItems())
                      .build();


              for(CartLineItem cartLineItem:cartLineItemRepository.findByCartTableCartId(customerCart.getCartId())){
                  CartLineItemsDto cartLineItemsDto = CartLineItemsDto.builder()
                          .includeItem(cartLineItem.isIncludeItem())
                          .price(productService.getProductPrice(cartLineItem.getProductId()))
                          .reducedPrice(productService.getProductReducedPrice(cartLineItem.getProductId()))
                          .productId(cartLineItem.getProductId())
                          .productName(cartLineItem.getProductName())
                          .recId(cartLineItem.getRecId())
                          .shippingCost(productService.getProductShippingCost(cartLineItem
                                  .getProductId()))
                          .quantity(cartLineItem.getQuantity())
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
                        .sessionId(sessionCart.getSessionId())
                        .build();

                for(CartLineItem cartLineItem:cartLineItemRepository.findByCartTableCartId(sessionCart.getCartId())){
                    CartLineItemsDto cartLineItemsDto = CartLineItemsDto.builder()
                            .includeItem(cartLineItem.isIncludeItem())
                            .price(productService.getProductPrice(cartLineItem.getProductId()))
                            .reducedPrice(productService.getProductReducedPrice(cartLineItem.getProductId()))
                            .productId(cartLineItem.getProductId())
                            .productName(cartLineItem.getProductName())
                            .quantity(cartLineItem.getQuantity())
                            .shippingCost(productService.getProductShippingCost(cartLineItem
                                    .getProductId()))
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
        CartTable cartTable = cartTableRepository.findBySessionId(sessionId)
                .orElse(cartTableRepository.findByCustomerId(customerId).orElse(null));
        Long quantity = 0L;
        if(cartTable !=null){
                for(CartLineItem cartLineItem:cartLineItemRepository.findByCartTableCartId(cartTable.getCartId())){
                    quantity +=cartLineItem.getQuantity();
                }
        }
        return quantity;
    }

    public ResponseDto<Object> includeAllItems(Long cartId,boolean  includeAllItems){
          try{
              CartTable cartTable= cartTableRepository.findById(cartId).orElse(null);
              if(cartTable!=null){
                  cartTable.setIncludeAllItems(includeAllItems);
                  cartTableRepository.save(cartTable);

                  for(CartLineItem lineItem:cartLineItemRepository.findByCartTableCartId(cartTable.getCartId())){
                      lineItem.setIncludeItem(includeAllItems);
                      cartLineItemRepository.save(lineItem);
                  }

                  return ResponseDto.builder()
                          .data(true)
                          .httpStatus(HttpStatus.OK)
                          .message("Updated")
                          .build();
              }
              return ResponseDto.builder()
                      .data(null)
                      .httpStatus(HttpStatus.NOT_FOUND)
                      .message("Record not found")
                      .build();
          }catch (Exception e){
              return ResponseDto.builder()
                      .data(null)
                      .httpStatus(HttpStatus.BAD_REQUEST)
                      .message(e.getMessage())
                      .build();
          }
    }

    public ResponseDto<Object> includeItem(Long cartId,Long recId,boolean  includeItem){
        try{

            //set the selected product for inclusion or exclusion from cart
            CartLineItem cartLineItem = cartLineItemRepository.findById(recId).orElse(null);
            if(cartLineItem!=null){
                cartLineItem.setIncludeItem(includeItem);
                cartLineItemRepository.save(cartLineItem);
                CartTable cartTable =cartTableRepository.findById(cartId).orElse(null);
            }

            CartTable cartTable =cartTableRepository.findById(cartId).orElse(null);

            //count of items marked as true
            int selectedList=0;

            if (cartTable != null) {

                //item list  in cart
                List<CartLineItem> cartItems = cartLineItemRepository.findByCartTableCartId(cartTable.getCartId());

                //check for any item marked to be included in cart ad update the selectList
                for(CartLineItem item:cartItems){
                    if(item.isIncludeItem()){
                        selectedList++;
                    }
                }

                //if all items are included, set header include all items as true else false
                if(selectedList==cartItems.size()){
                    cartTable.setIncludeAllItems(true);
                    cartTableRepository.save(cartTable);
                }else{
                    cartTable.setIncludeAllItems(false);
                    cartTableRepository.save(cartTable);
                }
            }

            return ResponseDto.builder()
                    .data(true)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("Success")
                    .build();
        }catch (Exception e){
            return ResponseDto.builder()
                    .data(null)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
        }
    }

    public ResponseDto<Object> deleteCart(Long cartId){
       try {

           for(CartLineItem lineItem :cartLineItemRepository.findByCartTableCartId(cartId)){
               cartLineItemRepository.delete(lineItem);
           }
           cartTableRepository.findById(cartId).ifPresent(cartTableRepository::delete);
           return ResponseDto.builder()
                   .data(null)
                   .httpStatus(HttpStatus.OK)
                   .message("Cart deleted")
                   .build();
       }catch (Exception e){
           return ResponseDto.builder()
                   .data(null)
                   .httpStatus(HttpStatus.BAD_REQUEST)
                   .message(e.getMessage())
                   .build();
       }
    }
    public ResponseDto<Object> deleteCartItem(Long recId){
        try {

           CartLineItem cartLineItem= cartLineItemRepository.findById(recId).orElse(null);
           if(cartLineItem!=null){
               //get main table id
               Long cartId = cartLineItem.getCartTable().getCartId();

               //delete product from cart items
               cartLineItemRepository.delete(cartLineItem);

               //if last product is deleted, then delete cart table record
               List<CartLineItem> lineItems = cartLineItemRepository.findByCartTableCartId(cartId);
               if(lineItems.size()==0){
                   cartTableRepository.deleteById(cartId);
               }

               return ResponseDto.builder()
                       .data(null)
                       .httpStatus(HttpStatus.OK)
                       .message("Cart deleted")
                       .build();
           }
            return ResponseDto.builder()
                    .data(null)
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .message("Record does not exist")
                    .build();
        }catch (Exception e){
            return ResponseDto.builder()
                    .data(null)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
        }

    }

    public ResponseDto<Object> updateCartQuantity(Long recId,Long quantity){

        try {
            CartLineItem cartLineItem= cartLineItemRepository.findById(recId).orElse(null);
            if(cartLineItem!=null){
                cartLineItem.setQuantity(quantity);
                cartLineItemRepository.save(cartLineItem);
            }
            return ResponseDto.builder()
                    .data(null)
                    .httpStatus(HttpStatus.OK)
                    .message("Updated")
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
