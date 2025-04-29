package com.repo.api.repository;

import com.repo.api.model.util.Courier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourierRepository extends CrudRepository<Courier,Long> {

    Optional<Courier> findByCourier(String courier);
}
