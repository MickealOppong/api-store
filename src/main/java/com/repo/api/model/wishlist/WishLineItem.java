package com.repo.api.model.wishlist;

import com.repo.api.model.user.Customer;
import com.repo.api.util.LogEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WishLineItem extends LogEntity {


    @Id @GeneratedValue
    private Long recId;

    @ManyToOne
    private Customer customer;

    private String sessionId;
    private Long productId;
    private Double price;
    private String productName;


}
