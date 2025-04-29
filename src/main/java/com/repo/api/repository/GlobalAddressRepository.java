package com.repo.api.repository;

import com.repo.api.model.util.GlobalAddressBook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GlobalAddressRepository extends CrudRepository<GlobalAddressBook,Long> {
    List<GlobalAddressBook> findAllByCustomerId(Long customerId);
    List<GlobalAddressBook> findAll();
}

