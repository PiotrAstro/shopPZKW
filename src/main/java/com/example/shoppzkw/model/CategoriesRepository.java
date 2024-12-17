package com.example.shoppzkw.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriesRepository extends JpaRepository<Category,Long> {
    List<Category> findByName(String name);
    List<Category> findByCode(String code);
}
