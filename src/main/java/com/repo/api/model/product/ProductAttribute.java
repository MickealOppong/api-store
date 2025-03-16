package com.repo.api.model.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductAttribute {


    @Id @GeneratedValue
    private Long attributeId;
    private String attribute;
    private String value;
    @ManyToOne
    private Product product;

    public ProductAttribute(ProductAttribute productAttribute) {
    }
}
