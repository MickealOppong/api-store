package com.repo.api.repository;

import com.repo.api.model.product.ProductAttribute;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductAttributeRepository extends CrudRepository<ProductAttribute,Long> {

    Optional<ProductAttribute> findByAttribute(String attribute);

    @Query(value ="SELECT * FROM product_attribute a JOIN product p ON a.product_rec_id=p.rec_id WHERE p.product_name like %?1% ",  nativeQuery = true)
    List<ProductAttribute> findByProductNameLike(String productName);


    List<ProductAttribute> findByProductRecId(Long recId);

    List<ProductAttribute>  findAll();
}
