package com.skincare.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reviewText;

    private int ratings;

    private LocalDateTime createdAt;

    @ManyToOne
    private Product product;

    @ManyToOne
    private User user;

    @PrePersist
    protected void setCreatedAt(){
        this.createdAt = LocalDateTime.now();
    }
}
