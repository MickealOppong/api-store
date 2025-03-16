package com.repo.api.repository;

import com.repo.api.model.product.Attribute;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AttributeRepository extends CrudRepository<Attribute,Long> {

}
