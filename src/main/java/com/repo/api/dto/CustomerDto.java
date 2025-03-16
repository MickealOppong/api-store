package com.repo.api.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

@Builder
@Data
public class CustomerDto {

    private String name;
    private String peselNumber;
    private String username;
    private String telephone;
    private Instant lastLogin;
    private String accountNumber;
    private String paymentAccount;
    private LocalDateTime accountCreatedAt;
}
