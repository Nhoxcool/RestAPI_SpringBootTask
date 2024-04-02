package com.example.ProductRest.ProductRestAPI.Service;

import com.example.ProductRest.ProductRestAPI.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product save(Product theProduct);

    List<Product> findAll();

    Page<Product> findAllByScoreFromHighToLow(Pageable pageable);

    Product findById(String theId);

    void deleteById(String theId);

    Page<Product> searchByName(String name, int page, int size, Optional<String> filterByName);
}
