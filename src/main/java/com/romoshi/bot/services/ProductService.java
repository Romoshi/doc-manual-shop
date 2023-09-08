package com.romoshi.bot.services;

import com.romoshi.bot.models.Product;
import com.romoshi.bot.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product updateProductName(Long productId, String newName) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            product.setName(newName);
            return productRepository.save(product);
        }
        return null;
    }

    public Product updateProductDescription(Long productId, String newDescription) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            product.setName(newDescription);
            return productRepository.save(product);
        }
        return null;
    }

    public Product updateProductPrice(Long productId, int newPrice) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            product.setPrice(newPrice);
            return productRepository.save(product);
        }
        return null;
    }
}
