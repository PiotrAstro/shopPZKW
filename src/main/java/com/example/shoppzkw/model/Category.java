package com.example.shoppzkw.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long categoryId;

    @Column(unique = true, nullable = false)
    @Length(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    private String name;

    @Column(unique = true, nullable = false)
    @Length(min = 2, max = 100, message = "Code must be between 3 and 100 characters")
    private String code;

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Product> products;
}
