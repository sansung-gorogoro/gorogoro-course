package com.lxp.course.course.presentation;

import com.lxp.course.course.application.port.in.GetCourseUseCase;
import com.lxp.course.course.presentation.response.CourseSummaryInstructorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/instructor/courses")
@RequiredArgsConstructor
public class InstructorCourseController {
    private final GetCourseUseCase getCourseUseCase;

    @GetMapping
    public CourseSummaryInstructorResponse getCourseSummary(
        @RequestHeader("X-User-Id") Long instructorId
    ) {
        return CourseSummaryInstructorResponse.of(getCourseUseCase.getCoursesSummaryInstructorExecute(instructorId));
    }
}
