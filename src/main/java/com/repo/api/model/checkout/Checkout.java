package com.repo.api.model.checkout;

import lombok.Data;

@Data
public class Checkout {
    public enum Currency {
        EUR, USD;
    }
    private String description;
    private int amount;
    private Currency currency;
    private String username;
    private String token;
}
