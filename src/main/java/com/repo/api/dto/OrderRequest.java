package com.repo.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private Long orderId;
    private String store;
    private Long quantity;
    private Double amount;
    private String invoiceNumber;

}
