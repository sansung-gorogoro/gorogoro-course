package com.lxp.course.category.infra;

import com.lxp.course.category.domain.Category;
import com.lxp.course.category.domain.repository.CategoryRepository;
import com.lxp.course.category.infra.jpa.CategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryAdaptor implements CategoryRepository {
    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public List<Category> findAll() {
        return categoryJpaRepository.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryJpaRepository.findById(id);
    }

    @Override
    public List<Category> findAllByIds(List<Long> ids) {
        return categoryJpaRepository.findAllById(ids);
    }
}
