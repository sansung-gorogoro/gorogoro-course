package com.lxp.course.course.application.port.in;

public interface DeleteCourseUseCase {
    void deleteCourseExecute(Long courseId, Long userId);
}
