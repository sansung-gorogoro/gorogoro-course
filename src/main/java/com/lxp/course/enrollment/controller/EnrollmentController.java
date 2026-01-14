package com.lxp.course.enrollment.controller;

import com.lxp.course.enrollment.controller.request.CreateEnrollmentRequest;
import com.lxp.course.enrollment.service.EnrollmentService;
import com.lxp.course.enrollment.service.dto.CreateEnrollmentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @PostMapping
    public void createEnrollment(
        @RequestHeader("X-User-Id") Long userId,
        @RequestBody CreateEnrollmentRequest request
    ) {
        enrollmentService.createEnrollment(new CreateEnrollmentDto(userId, request.courseId()));
    }
}
