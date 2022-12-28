package com.djoumatch.productservice.controller;

import com.djoumatch.productservice.dto.ProductRequest;
import com.djoumatch.productservice.dto.ProductResponse;
import com.djoumatch.productservice.model.Product;
import com.djoumatch.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Product Controller
 */
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest){
        productService.createProduct(productRequest);
    }

    @PutMapping
    public void updateProduct(@RequestBody Product product){}

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public void getProductById(@PathVariable String id){}

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id){}
}
