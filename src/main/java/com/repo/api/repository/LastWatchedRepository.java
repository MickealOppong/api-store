package com.repo.api.repository;

import com.repo.api.model.user.LastWatched;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LastWatchedRepository extends CrudRepository<LastWatched,Long> {
    List<LastWatched> findAll();
    Optional<LastWatched> findByProductId(Long productId);
    List<LastWatched> findAllByCustomerId(Long customerId);
    List<LastWatched> findAllBySessionId(String sessionId);
}
