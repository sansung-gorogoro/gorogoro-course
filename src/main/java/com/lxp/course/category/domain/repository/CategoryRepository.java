package com.lxp.course.category.domain.repository;

import com.lxp.course.category.domain.Category;

import java.util.List;

public interface CategoryRepository {
    List<Category> findAll();
}
