package com.lxp.course.presentation;

import com.lxp.course.presentation.request.CreateCourseRequest;

public interface CourseApi {
    void createCourse(CreateCourseRequest request);
}
