package com.example.ProductRest.ProductRestAPI.rest;

import com.example.ProductRest.ProductRestAPI.Service.ProductService;
import com.example.ProductRest.ProductRestAPI.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductRestController {
    private ProductService productService;

    public ProductRestController (ProductService theProductService) {
        productService = theProductService;
    };

    // get all product
    @GetMapping("/products")
    public List<Product> findAll() {return productService.findAll();}

    // add product with name and id is unique
    @PostMapping("/products")
    public Product addProduct(@RequestBody Product theProduct) {
        // set current Date
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        theProduct.setCreatedDate(currentDate);
        theProduct.setUpdatedDate(currentDate);

        // check product id existing or not
        List<Product> productsList = productService.findAll();
        for(Product product: productsList) {
            if(theProduct.getId().equals(product.getId())) {
                throw new RuntimeException("Product id is existing");
            }
        }

        // check Product name is existing or not
        for(Product product: productsList) {
            if(theProduct.getProductName().equals(product.getProductName())) {
                throw new RuntimeException("Product name is existing: " + theProduct.getProductName());
            }
        }

        Product dbProduct = productService.save(theProduct);
        return dbProduct;
    }

    //Delete Product by id
    @DeleteMapping("/products/{productId}")
    public String deleteProduct(@PathVariable String productId) {
        Product theProduct = productService.findById(productId);

        if(theProduct == null) {
            throw new RuntimeException("Product id not found -" + productId);
        }

        productService.deleteById(productId);

        return "Deleted successfully Product with id: " + productId;
    }

    // Update product
    @PutMapping("/products/{productId}")
    public Product updateProduct(@RequestBody Product theProduct, @PathVariable String productId){
        List<Product> productsList = productService.findAll();
        boolean check = false;

        for(Product product: productsList) {
            if(productId.equals(product.getId())) {
                check = true;
                theProduct.setCreatedDate(product.getCreatedDate());
                theProduct.setId(productId);
            }
        }

        if(check)
        {
            Calendar calendar = Calendar.getInstance();
            Date currentDate = calendar.getTime();
            theProduct.setUpdatedDate(currentDate);

            Product dbProduct = productService.save(theProduct);
            return dbProduct;
        }
        else {
            throw new RuntimeException("Product id isn't exist");
        }
    }

    // Get product sort filter search by Name
    @GetMapping("/products/search")
    public Page<Product> searchProductsByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String filterByName
    ) {
        return productService.searchByName(name, page, size, Optional.ofNullable(filterByName));
    }

    // Sort By Score from high to low
    @GetMapping("/products/sortByScore")
    public Page<Product> getAllProductsSortedByAverageScore(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return productService.findAllByScoreFromHighToLow(PageRequest.of(page, size));
    }
}
