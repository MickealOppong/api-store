package com.repo.api.repository;

import com.repo.api.model.product.Parameter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParameterRepository extends CrudRepository<Parameter,Long> {
}
