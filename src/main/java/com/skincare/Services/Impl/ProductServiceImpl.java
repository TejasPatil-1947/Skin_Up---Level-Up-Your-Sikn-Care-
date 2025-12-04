package com.skincare.Services.Impl;

import com.skincare.Entities.Product;
import com.skincare.Repositories.ProductRepository;
import com.skincare.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;


    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        productRepository.delete(product);
    }

    @Override
    public Product updateProduct(Long productId, Product product) {
        Product exProduct = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        exProduct.setProductType(product.getProductType());
        exProduct.setName(product.getName());
        exProduct.setBrand(product.getBrand());
        exProduct.setSkinType(product.getSkinType());
        exProduct.setPrice(product.getPrice());
        exProduct.setDescription(product.getDescription());

        return productRepository.save(exProduct);
    }

    public List<Product> findByName(String name){
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Product> findByBrand(String brand){
        return productRepository.findByBrandContainingIgnoreCase(brand);
    }

    public List<Product> findBySkinType(String skinType){
        return productRepository.findBySkinTypeContainingIgnoreCase(skinType);
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }
}
