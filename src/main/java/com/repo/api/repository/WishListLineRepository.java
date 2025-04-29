package com.repo.api.repository;

import com.repo.api.model.wishlist.WishLineItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface WishListLineRepository extends CrudRepository<WishLineItem,Long> {

    Optional<WishLineItem> findByProductIdAndCustomerId(Long productId,Long customerId);
    Optional<WishLineItem> findByProductIdAndSessionId(Long productId,String sessionId);

    List<WishLineItem> findAllBySessionId(String sessionId);
    List<WishLineItem> findAllByCustomerId(String sessionId);
}
