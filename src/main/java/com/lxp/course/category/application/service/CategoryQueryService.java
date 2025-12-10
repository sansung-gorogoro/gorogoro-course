package com.lxp.course.category.application.service;

import com.lxp.course.category.application.port.in.GetCategoriesUseCase;
import com.lxp.course.category.application.port.in.dto.CategoryDto;
import com.lxp.course.category.domain.Category;
import com.lxp.course.category.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryQueryService implements GetCategoriesUseCase {
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getCategoriesExecute() {
        List<Category> categories = categoryRepository.findAll();

        Map<Category, List<Category>> byParent = categories.stream()
            .filter(category -> category.getParent() != null)
            .collect(Collectors.groupingBy(Category::getParent));

        return CategoryDto.toDtos(byParent);
    }
}
