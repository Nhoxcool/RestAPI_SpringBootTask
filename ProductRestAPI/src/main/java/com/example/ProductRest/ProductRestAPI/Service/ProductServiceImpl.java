package com.example.ProductRest.ProductRestAPI.Service;

import com.example.ProductRest.ProductRestAPI.dao.ProductRespository;
import com.example.ProductRest.ProductRestAPI.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    private ProductRespository productRespository;

    @Autowired
    public ProductServiceImpl (ProductRespository theProductRespository){
        productRespository = theProductRespository;
    };

    @Override
    public Product save(Product theProduct) {
        return productRespository.save(theProduct);
    }

    @Override
    public List<Product> findAll() {
        return productRespository.findAll();
    }

    @Override
    public Page<Product> findAllByScoreFromHighToLow(Pageable pageable) {
        return productRespository.findAllByOrderByScoreDesc(pageable);
    }

    @Override
    public Product findById(String theId) {
        Optional<Product> result = productRespository.findById(theId);
        Product theproduct = null;
        if(result.isPresent()) {
            theproduct = result.get();
        }
        else {
            throw new RuntimeException("Didn't find product id - " + theId);
        }
        return theproduct;
    }

    @Override
    public void deleteById(String theId) {
        productRespository.deleteById(theId);
    }


    @Override
    public Page<Product> searchByName(String name, int page, int size, Optional<String> filterByName) {
        Pageable pageable = PageRequest.of(page, size);

        return filterByName.map(filter -> productRespository.findByProductNameContainingAndFilter(name, filter, pageable))
                .orElse(productRespository.findByProductNameContaining(name, pageable));
    }
}
