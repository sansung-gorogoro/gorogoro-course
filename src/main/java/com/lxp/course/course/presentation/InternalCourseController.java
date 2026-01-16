package com.lxp.course.course.presentation;

import com.lxp.course.course.application.port.in.GetCourseUseCase;
import com.lxp.course.course.presentation.response.CourseDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/internal/courses")
@RequiredArgsConstructor
public class InternalCourseController {
    private final GetCourseUseCase getCourseUseCase;

    @GetMapping
    public List<CourseDetailResponse> getCourseDetails(@RequestParam List<Long> courseIds) {
        return getCourseUseCase.getCourseDetails(courseIds).stream()
            .map(CourseDetailResponse::of).toList();
    }

    @GetMapping("/{courseId}")
    public Long getLectureIdFromCourse(Long courseId) {
        return getCourseUseCase.getLectureIdFromCourse(courseId);
    }
}
