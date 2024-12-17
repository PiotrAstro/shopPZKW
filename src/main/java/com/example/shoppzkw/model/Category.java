package com.example.shoppzkw.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

    @NotNull(message = "Name is required")
    @Column(unique = true, nullable = false)
    @Length(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    private String name;

    @NotNull(message = "Code is required")
    @Column(unique = true, nullable = false)
    @Length(min = 2, max = 100, message = "Code must be between 2 and 100 characters")
    @Pattern(regexp = "K[0-9]+", message = "Code must start with K and be followed by a number")
    private String code;

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Product> products;

    public String showName() {
        return this.name + " - " + this.code;
    }

    public String toString() {
        return Long.toString(this.categoryId);
    }
}
