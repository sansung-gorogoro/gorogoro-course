package com.lxp.course.course.application.port.in;

import com.lxp.course.course.application.port.in.command.CreateCourseCommand;

public interface CreateCourseUseCase {
    void createExecute(CreateCourseCommand command);
}
