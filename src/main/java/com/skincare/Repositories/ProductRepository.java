package com.skincare.Repositories;

import com.skincare.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByNameContainingIgnoreCase(String name);


    List<Product> findByBrandContainingIgnoreCase(String brand);


    List<Product> findBySkinTypeContainingIgnoreCase(String skinType);
}
