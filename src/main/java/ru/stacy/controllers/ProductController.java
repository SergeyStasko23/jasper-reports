package ru.stacy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.stacy.entities.Product;
import ru.stacy.services.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/products")
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/products/report")
    public String generateReport() {
        return productService.generateReport();
    }
}
