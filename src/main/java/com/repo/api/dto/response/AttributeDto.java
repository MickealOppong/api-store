package com.repo.api.dto.response;

import com.repo.api.model.product.ProductAttribute;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttributeDto {
    private Long id;
    private String attribute;
    private Long productId;
    private String value;

    public AttributeDto(ProductAttribute productAttribute) {
        this.id=productAttribute.getAttributeId();
        this.attribute = productAttribute.getAttribute();
        this.productId= productAttribute.getProduct().getRecId();
        this.value = productAttribute.getValue();
    }
}
