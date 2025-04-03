package com.nadila.blogapi.repository;

import com.nadila.blogapi.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    boolean existsByName(String category);

    Category findByName(String name);
}
