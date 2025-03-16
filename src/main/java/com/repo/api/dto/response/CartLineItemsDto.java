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
public class CartLineItemsDto {

    private Long recId;
    private Long productId;
    private Double price;
    private String productName;
    private boolean includeItem;

    private List<String>images = new ArrayList<>();

}
