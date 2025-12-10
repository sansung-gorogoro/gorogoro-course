package com.lxp.course.category.application.port.in.dto;

import com.lxp.course.category.domain.Category;

import java.util.List;
import java.util.Map;

public record CategoryDto(
    Long id,
    String name,
    List<SubCategoryDto> subCategories
) {
    public record SubCategoryDto(
        Long id,
        String name,
        Long parentId
    ) {
        private static SubCategoryDto toDto(Category subCategory) {
            return new SubCategoryDto(subCategory.getId(), subCategory.getName(), subCategory.getParent().getId());
        }
    }

    public static List<CategoryDto> toDtos(Map<Category, List<Category>> byParent) {
        return byParent.entrySet().stream().map(entry -> {
            List<SubCategoryDto> subDtos = entry.getValue().stream().map(SubCategoryDto::toDto).toList();

            return new CategoryDto(entry.getKey().getId(), entry.getKey().getName(), subDtos);
        }).toList();
    }
}
