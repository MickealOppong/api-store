package com.repo.api.repository;

import com.repo.api.model.cart.CartTable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CartTableRepository extends CrudRepository<CartTable,Long> {

    Optional<CartTable> findByCustomerId(Long customerId);
    Optional<CartTable> findBySessionId(String sessionId);
}
