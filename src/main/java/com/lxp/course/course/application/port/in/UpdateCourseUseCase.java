package com.lxp.course.course.application.port.in;

import com.lxp.course.course.application.port.in.command.UpdateCourseCommand;

public interface UpdateCourseUseCase {
    void updateExecute(UpdateCourseCommand command);
}
