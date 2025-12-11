package com.lxp.course.category.domain.repository;

import com.lxp.course.category.domain.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    List<Category> findAll();
    Optional<Category> findById(Long id);
}
