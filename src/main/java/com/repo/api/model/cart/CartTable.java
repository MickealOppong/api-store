package com.repo.api.model.cart;

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
public class CartTable extends LogEntity {

    @Id @GeneratedValue
    private Long cartId;
    private String sessionId;
    private boolean includeAllItems;
    @ManyToOne
    private Customer customer;
}
