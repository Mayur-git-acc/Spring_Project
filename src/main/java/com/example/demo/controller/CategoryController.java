package com.example.demo.controller;
import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;

import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	 
		private final CategoryRepository categoryRepository;
	 	
	 	@Autowired
	    public CategoryController(CategoryRepository categoryRepository) {
	        this.categoryRepository = categoryRepository;
	    }
	    
	    @GetMapping
	    public List<Category> getAllCategories(@RequestParam(defaultValue = "0") int page) {
	        return categoryRepository.findAll(PageRequest.of(page, 10)).getContent();
	    }
	    
	    @PostMapping
	    @ResponseStatus(code = HttpStatus.CREATED)
	    public void createCategory(@RequestBody Category category) {
	    	categoryRepository.save(category);
	    }
	    
	    @GetMapping("/{id}")
	    public Category getCategory(@PathVariable Long id) {
	    	Category category=categoryRepository.findById(id).get();
	    	return category;
	    }

		    
	    @PutMapping("/{id}")
	    public Category updateCategory(@PathVariable Long id, @RequestBody Category categoryDetails) {
	        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
	        category.setName(categoryDetails.getName());
	        return categoryRepository.save(category);
	    }
	    
	    @DeleteMapping("/{id}")
	    public void removeCategory(@PathVariable Long id) {
	    	Category category= categoryRepository.findById(id).get();
	    	categoryRepository.delete(category);
	    	
	    }

}
