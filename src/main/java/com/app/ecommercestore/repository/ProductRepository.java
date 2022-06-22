package com.app.ecommercestore.repository;

import com.app.ecommercestore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    @Query(
            value = "SELECT * FROM products u WHERE u.id = ?1",
            nativeQuery = true)
    Product getById(Long productId);

    
    @Query(
            value = "SELECT * FROM products",
            nativeQuery = true)
    List<Product> getAllProducts();


    
    @Query(
            value = "SELECT * FROM products where delete_status=0",
            nativeQuery = true)
    List<Product> getAllUnDeletedProducts();
}
