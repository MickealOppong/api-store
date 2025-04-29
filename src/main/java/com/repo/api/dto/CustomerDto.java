package com.repo.api.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class CustomerDto {

    private String firstName;
    private String lastName;
    private String username;
    private String telephone;
    private Instant lastLogin;
    private List<CustomerAddressDto> customerDeliveryAddress = new ArrayList<>();
    private List<CustomerAddressDto> customerInvoiceAddress = new ArrayList<>();
}
