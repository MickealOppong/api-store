package com.repo.api.controller;

import com.repo.api.dto.ResponseDto;
import com.repo.api.dto.TokenDto;
import com.repo.api.dto.request.UserRegistrationRequest;
import com.repo.api.dto.response.LoginResponse;
import com.repo.api.model.user.Customer;
import com.repo.api.model.user.RefreshToken;
import com.repo.api.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequestMapping("/auth")
@RestController
public class AuthController {


    private final AuthenticationManager authenticationManager;
    private final CustomerDetailsService customerDetailsService;
    private final RefreshTokenService refreshTokenService;
    private final TokenService tokenService;
    private final CartService cartService;
    private final WishListService wishListService;



    public AuthController(AuthenticationManager authenticationManager,CustomerDetailsService customerDetailsService,
                          RefreshTokenService refreshTokenService,TokenService tokenService,CartService cartService,
                          WishListService wishListService) {
        this.authenticationManager = authenticationManager;
        this.customerDetailsService = customerDetailsService;
        this.refreshTokenService = refreshTokenService;
        this.tokenService = tokenService;
        this.cartService = cartService;
        this.wishListService = wishListService;
    }

    @GetMapping
    public String Home(){
        return "testing path";
    }

    @PostMapping("/register")
    public ResponseDto<Object> createCustomer(@RequestBody UserRegistrationRequest userRegistrationRequest){
      return customerDetailsService.addUser(userRegistrationRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(String username,String password,String sessionId){
                Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username,password));
        if(authentication.isAuthenticated()){
            Customer  customer =(Customer) customerDetailsService.getCustomerByUsername(username).getData();

            //update cart table with customer if cart exist
            cartService.updateCartTableOnCustomerLogin(customer,sessionId);
            wishListService.updateCartTableOnCustomerLogin(customer,sessionId);

            //update last login on account login
            customerDetailsService.updateLastLogin(customer.getUsername());

            RefreshToken token =refreshTokenService.createToken(customer.getUsername());

            TokenDto tokenDto = TokenDto.builder()
                    .token(tokenService.token(authentication).orElse(null))
                    .refreshToken(token.getRefreshToken())
                    .expiredAt(token.getExpiredAt())
                    .issuedAt(token.getIssuedAt())
                    .build();
            LoginResponse customerDto = LoginResponse.builder()
                    .tokenDto(tokenDto)
                    .username(customer.getUsername())
                    .id(customer.getId())
                    .lastLogin(customer.getLastLogin())
                    .name(customer.getFirstName()+" "+customer.getLastName())
                    .build();

            return  ResponseEntity.ok().body(customerDto);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Boolean> logoutUser( String refreshToken){
        try{
            return ResponseEntity.ok(refreshTokenService.removeToken(refreshToken));
        }catch (Exception  e){
            return ResponseEntity.badRequest().body(false);
        }
    }


}
