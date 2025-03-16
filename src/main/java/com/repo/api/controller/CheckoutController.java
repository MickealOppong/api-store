package com.repo.api.controller;

import com.repo.api.dto.CheckoutDto;
import com.repo.api.dto.CheckoutResponse;
import com.repo.api.dto.CheckoutStatusResponse;
import com.repo.api.service.CheckoutService;
import com.stripe.exception.StripeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/checkout")
public class CheckoutController {


    private final CheckoutService checkoutService;
    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @Value("${STRIPE_PUBLIC_KEY}")
    private String stripePublicKey;


    @PostMapping("/client-secret")
    public ResponseEntity<CheckoutResponse> checkoutSession(@RequestBody CheckoutDto checkoutDto) throws StripeException {
        try {
            return ResponseEntity.ok(checkoutService.charge(checkoutDto));
        }catch (Exception e){
            return ResponseEntity.ok(CheckoutResponse.builder()
                    .clientSecret("").build());
        }

    }

    @PostMapping
    public ResponseEntity<CheckoutResponse> checkout(@RequestParam Long id) throws StripeException {
        try {

            return ResponseEntity.ok(checkoutService.charge(null));
        }catch (Exception e){
            return ResponseEntity.ok(CheckoutResponse.builder()
                    .clientSecret("").build());
        }

    }

    @GetMapping("/session-status")
    public ResponseEntity<CheckoutStatusResponse> checkoutStatus(String sessionId,Long orderId){
        try{
            CheckoutStatusResponse statusResponse =checkoutService.checkoutStatus(sessionId,orderId);
            return ResponseEntity.ok(statusResponse);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(CheckoutStatusResponse.builder()
                            .status("open")
                            .username(null)
                    .build());
        }
    }

}
