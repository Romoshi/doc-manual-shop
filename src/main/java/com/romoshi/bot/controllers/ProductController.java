package com.romoshi.bot.controllers;

import com.romoshi.bot.models.Product;
import com.romoshi.bot.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @PutMapping("/{productId}/description")
    public Product updateProductDescription(@PathVariable Long productId, @RequestParam String newDescription) {
        return productService.updateProductName(productId, newDescription);
    }

    @PutMapping("/{productId}/price")
    public Product updateProductPrice(@PathVariable Long productId, @RequestParam int newPrice) {
        return productService.updateProductPrice(productId, newPrice);
    }
}
