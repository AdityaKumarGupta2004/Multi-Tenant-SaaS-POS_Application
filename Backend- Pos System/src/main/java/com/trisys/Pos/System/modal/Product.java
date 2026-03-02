package com.trisys.Pos.System.modal;

import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        name = "products"
)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String sku;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String color;

    private Double mrp;

    @Column(nullable = false)
    private Double sellingPrice;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Store store;

    @PastOrPresent
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PastOrPresent
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}