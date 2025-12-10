package com.lxp.course.course.presentation;

import com.lxp.course.course.presentation.request.CreateCourseRequest;

public interface CourseApi {
    void createCourse(Long instructorId, CreateCourseRequest request);
}
