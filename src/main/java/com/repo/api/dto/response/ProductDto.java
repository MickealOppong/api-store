package com.repo.api.dto.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long productId;

    private String productName;
    private String searchName;
    @Column(length =5000)
    private String productDescription;

    private String generalInfo1;
    private String generalInfo2;
    private String generalInfo3;
    private String generalInfo4;

    private double price;
    private double reducedPrice;
    private double shippingCost;

    private boolean isNewArrival;
    private boolean isOnSale;
    private boolean isFreeShipping;

    private List<String> productImages = new ArrayList<>();

    private Set<AttributeDto> attributeList = new HashSet<>();
    private List<CategoryDto> categoryList = new ArrayList<>();
    private List<ParameterDto> parameterList = new ArrayList<>();


}
