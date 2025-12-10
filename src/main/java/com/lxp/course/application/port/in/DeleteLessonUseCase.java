package com.lxp.course.application.port.in;

import com.lxp.course.application.port.in.command.DeleteLessonsCommand;

public interface DeleteLessonUseCase {
    void deleteLessonExecute(DeleteLessonsCommand command);
}
