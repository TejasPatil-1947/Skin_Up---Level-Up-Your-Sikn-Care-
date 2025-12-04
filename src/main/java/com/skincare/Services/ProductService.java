package com.skincare.Services;

import com.skincare.Entities.Product;

import java.util.List;

public interface ProductService {

    Product addProduct(Product product);

    void deleteProduct(Long id);

    Product updateProduct(Long productId,Product product);

    List<Product> findByName(String name);

    List<Product> findByBrand(String brand);

    List<Product> findBySkinType(String skinType);

    List<Product> findAllProducts();
}
