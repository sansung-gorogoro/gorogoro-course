package com.lxp.course.enrollment.controller;

import com.lxp.course.enrollment.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/internal/enrollments")
public class InternalEnrollmentsController {
    private final EnrollmentService enrollmentService;

    @GetMapping("/existence")
    public boolean isEnrolled(
        @RequestParam Long userId,
        @RequestParam Long courseId
    ) {
        return enrollmentService.isEnrolled(userId, courseId);
    }
}
