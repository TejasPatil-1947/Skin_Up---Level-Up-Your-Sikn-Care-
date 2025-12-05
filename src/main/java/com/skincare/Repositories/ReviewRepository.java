package com.skincare.Repositories;

import com.skincare.Entities.Product;
import com.skincare.Entities.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Reviews,Long> {

    List<Reviews> findByProductId(Long productId);

    List<Reviews> findByUserId(Long userId);
}
