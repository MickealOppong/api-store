package com.repo.api.repository;

import com.repo.api.model.user.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer,Long> {
    Optional<Customer> findByUsername(String username);
    Optional<Customer> findByTelephone(String telephone);
}
