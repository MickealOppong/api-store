package com.repo.api.repository;

import com.repo.api.model.user.CustomerTrans;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerTransRepository extends CrudRepository<CustomerTrans,Long> {
    @Query(value = "SELECT * from customer_trans c WHERE c.fk_id=?" ,nativeQuery = true)
    List<CustomerTrans> findByUserId(Long transId);
}
