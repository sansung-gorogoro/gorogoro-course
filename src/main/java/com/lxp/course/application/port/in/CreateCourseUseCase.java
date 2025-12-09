package com.lxp.course.application.port.in;

import com.lxp.course.application.port.in.command.CreateCourseCommand;

public interface CreateCourseUseCase {
    void execute(CreateCourseCommand command);
}
