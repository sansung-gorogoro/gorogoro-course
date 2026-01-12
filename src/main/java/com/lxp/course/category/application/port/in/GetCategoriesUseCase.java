package com.lxp.course.category.application.port.in;

import com.lxp.course.category.application.port.in.dto.CategoryDto;
import com.lxp.course.category.application.port.in.dto.CategoryPathDto;

import java.util.List;

public interface GetCategoriesUseCase {
    List<CategoryDto> getCategoriesExecute();
    List<CategoryPathDto> getCategoriesBySubCategoryIdExecute(List<Long> subCategoryIds);
}
