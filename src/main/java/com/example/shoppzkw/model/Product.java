package com.example.shoppzkw.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;

    @Column(unique = true, nullable = false)
    @Length(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    private String name;

    @ManyToOne
    @JoinColumn(name = "FkCategoryId", nullable = false)
    private Category category;

    @Column(nullable = false)
    @Min(value = 0, message = "Price must be greater than 0")
    private double price;

    @Column(nullable = false)
    @Min(value = 0, message = "Weight must be greater than 0")
    private double weight;
}
