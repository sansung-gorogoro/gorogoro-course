package com.lxp.course.category.application.service;

import com.lxp.course.category.application.port.in.GetCategoriesUseCase;
import com.lxp.course.category.application.port.in.dto.CategoryDto;
import com.lxp.course.category.domain.Category;
import com.lxp.course.category.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService implements GetCategoriesUseCase {
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getCategoriesExecute() {
        List<Category> categories = categoryRepository.findAll();

        Map<Category, List<Category>> byParent = categories.stream()
            .collect(Collectors.groupingBy(Category::getParent));

        return CategoryDto.toDtos(byParent);
    }
}
