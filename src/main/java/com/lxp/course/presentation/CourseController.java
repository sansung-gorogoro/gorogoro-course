package com.lxp.course.presentation;

import com.lxp.course.application.port.in.CreateCourseUseCase;
import com.lxp.course.presentation.request.CreateCourseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController implements CourseApi {
    private final CreateCourseUseCase createCourseUseCase;

    @PostMapping
    public void createCourse(@RequestBody CreateCourseRequest request) {
        createCourseUseCase.execute(request.toCommand());
    }
}
