package com.example.ProductRest.ProductRestAPI.dao;

import com.example.ProductRest.ProductRestAPI.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRespository extends JpaRepository<Product, String> {

    Page<Product> findByProductNameContaining(String name, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE (:productName IS NULL OR p.productName LIKE %:productName%) AND (:filterByName IS NULL OR p.productName LIKE %:filterByName%)")
    Page<Product> findByProductNameContainingAndFilter(String productName, String filterByName, Pageable pageable);
    Page<Product> findAllByOrderByScoreDesc(Pageable pageable);
}
