package com.lxp.course.category.application.port.in;

import com.lxp.course.category.application.port.in.command.CreateCategoryCommand;

public interface CreateCategoryUseCase {
    Long createExecute(CreateCategoryCommand command);
}
