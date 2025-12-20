package com.lxp.course.category.application.service;

import com.lxp.course.category.application.port.in.CreateCategoryUseCase;
import com.lxp.course.category.application.port.in.command.CreateCategoryCommand;
import com.lxp.course.category.domain.Category;
import com.lxp.course.category.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryCommandService implements CreateCategoryUseCase {
    private final CategoryRepository categoryRepository;

    @Override
    public Long createExecute(CreateCategoryCommand command) {
        Category parent = Optional.ofNullable(command.parentId())
            .flatMap(categoryRepository::findById)
            .orElse(null);

        Category category = categoryRepository.save(parent);

        return category.getId();
    }
}
