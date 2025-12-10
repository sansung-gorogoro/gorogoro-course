package com.lxp.course.category.presentation.response;

import com.lxp.course.category.application.port.in.dto.CategoryDto;
import com.lxp.course.category.application.port.in.dto.CategoryDto.SubCategoryDto;

import java.util.List;

public record CategoriesResponse(
    List<CategoryContent> contents
) {
    record CategoryContent(
        Long id,
        String name,
        List<SubCategoryContent> subCategories
    ) {
        private static CategoryContent toContent(CategoryDto dto) {
            List<SubCategoryContent> subContents =
                dto.subCategories().stream().map(SubCategoryContent::toContent).toList();

            return new CategoryContent(dto.id(), dto.name(), subContents);
        }

        record SubCategoryContent(
            Long id,
            String name,
            Long parentId
        ) {
            private static SubCategoryContent toContent(SubCategoryDto dto) {
                return new SubCategoryContent(dto.id(), dto.name(), dto.parentId());
            }
        }
    }

    public static CategoriesResponse of(List<CategoryDto> contents) {
        return new CategoriesResponse(contents.stream().map(CategoryContent::toContent).toList());
    }
}
