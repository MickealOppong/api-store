package com.repo.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutDto {

    private String store;
    private Double amount;
    private String currency;
    private Long quantity;
    private String username;
    private String successUrl;
    private String cancelUrl;
}
