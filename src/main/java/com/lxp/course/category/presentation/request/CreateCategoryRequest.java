package com.lxp.course.category.presentation.request;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryRequest(
    @NotBlank(message = "카테고리 이름을 작성해주세요.")
    String name,
    Long parentId
) {
}
