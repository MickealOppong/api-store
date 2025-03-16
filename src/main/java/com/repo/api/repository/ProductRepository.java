package com.repo.api.repository;

import com.repo.api.model.product.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product,Long> {
    Optional<Product> findByProductName(String productName);
    List<Product> findAll();
    @Query(value = "SELECT * FROM product AS p JOIN product_category_list AS cl ON p.id=cl.product_id JOIN category c ON c.rec_id=cl.category_list_rec_id WHERE c.category=?",nativeQuery = true)
    List<Product> findByCategory(String category);

    @Query(value ="SELECT * FROM product p JOIN product_attribute a ON p.rec_id=a.product_rec_id WHERE p.product_name LIKE %?1% ",  nativeQuery = true)
    List<Product> findByProductNameLike(String productName);
}
