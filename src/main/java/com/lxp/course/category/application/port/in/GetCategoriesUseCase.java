package com.lxp.course.category.application.port.in;

import com.lxp.course.category.application.port.in.dto.CategoryDto;

import java.util.List;

public interface GetCategoriesUseCase {
    List<CategoryDto> getCategoriesExecute();
}
