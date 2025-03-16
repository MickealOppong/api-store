package com.repo.api.model.cart;

import com.repo.api.util.LogEntity;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartLineItem extends LogEntity {


    @Id @GeneratedValue
    private Long recId;
    private Long productId;
    private Double price;
    private String productName;
    private boolean includeItem;
    private Long quantity;

    @ManyToOne
    @JoinColumn(name = "cartId",referencedColumnName = "cartId")
    private CartTable cartTable;

}
