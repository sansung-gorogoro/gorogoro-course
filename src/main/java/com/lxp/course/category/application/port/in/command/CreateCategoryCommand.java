package com.lxp.course.category.application.port.in.command;

import com.lxp.course.category.domain.Category;

public record CreateCategoryCommand(
    String name,
    Long parentId
) {
    public Category toEntity(Category parent) {
        return Category.create(name, parent);
    }
}
