package com.lxp.course.presentation;

import com.lxp.course.application.port.in.CreateCourseUseCase;
import com.lxp.course.application.port.in.UpdateCourseUseCase;
import com.lxp.course.presentation.request.CreateCourseRequest;
import com.lxp.course.presentation.request.UpdateCourseRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController implements CourseApi {
    private final CreateCourseUseCase createCourseUseCase;
    private final UpdateCourseUseCase updateCourseUseCase;

    @PostMapping
    public void createCourse(@Valid @RequestBody CreateCourseRequest request) {
        createCourseUseCase.createExecute(request.toCommand(1L));
    }

    @PutMapping("/{courseId}")
    public void updateCourse(
        @PathVariable Long courseId,
        @Valid @RequestBody UpdateCourseRequest request
    ) {
        updateCourseUseCase.updateExecute(request.toCommand(courseId));
    }
}
