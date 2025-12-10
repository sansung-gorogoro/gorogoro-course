package com.lxp.course.category.infra;

import com.lxp.course.category.domain.Category;
import com.lxp.course.category.domain.repository.CategoryRepository;
import com.lxp.course.category.infra.jpa.CategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryAdaptor implements CategoryRepository {
    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public List<Category> findAll() {
        return categoryJpaRepository.findAll();
    }
}
