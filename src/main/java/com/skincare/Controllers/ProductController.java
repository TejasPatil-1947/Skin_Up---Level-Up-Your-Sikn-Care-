package com.skincare.Controllers;

import com.skincare.Entities.Product;
import com.skincare.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableMethodSecurity
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product){
        return new ResponseEntity<>(productService.addProduct(product), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,@RequestBody Product product){
        return new ResponseEntity<>(productService.updateProduct(id,product),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return new ResponseEntity<>("product deleted successfully",HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> findAllProducts(){
        return new ResponseEntity<>(productService.findAllProducts(),HttpStatus.OK);
    }

    @GetMapping("/byName")
    public ResponseEntity<List<Product>> findByProductName(@RequestParam String productName){
        String trim = productName.trim();
        return new ResponseEntity<>(productService.findByName(trim),HttpStatus.OK);
    }


    @GetMapping("/brand")
    public ResponseEntity<List<Product>> findByBrand(@RequestParam String brand){
        return new ResponseEntity<>(productService.findByBrand(brand),HttpStatus.OK);
    }


    @GetMapping("/skinType")
    public ResponseEntity<List<Product>> findBySkinType(@RequestParam String skinType){
        return new ResponseEntity<>(productService.findBySkinType(skinType),HttpStatus.OK);
    }























}
