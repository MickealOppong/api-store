package com.repo.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartDto {

    private Long cartId;
    private String sessionId;
    private boolean includeAllItems;
    private Long customerId;
    List<CartLineItemsDto> lineItems = new ArrayList<>();

}
