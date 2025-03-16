package com.repo.api.repository;

import com.repo.api.model.cart.CartLineItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CartLineItemRepository extends CrudRepository<CartLineItem,Long> {

    List<CartLineItem> findByCartTableCartId(Long cartId);
    Optional<CartLineItem> findByProductId(Long productId);
}
