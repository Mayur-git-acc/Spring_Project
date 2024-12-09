package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	
	private final ProductRepository productRepository;
	
	@Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Product> getAllProducts(@RequestParam(defaultValue = "0") int page) {
        return productRepository.findAll(PageRequest.of(page, 10)).getContent();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createProduct(@RequestBody Product product) {
         productRepository.save(product);
    }  


    @GetMapping("{id}")
    public Product getProduct(@PathVariable Long id) {
    	Product product=productRepository.findById(id).get();
    	return product;
    }
    

    
    @PutMapping("{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());

        return productRepository.save(product);
    }

    @DeleteMapping("{id}")
    public void deleteProduct(@PathVariable Long id) {
    	Product product=productRepository.findById(id).get();
    	productRepository.delete(product);
    }
}
