package com.lxp.course.enrollment.service.dto;

public record CreateEnrollmentDto(
    Long courseId,
    Long userId
) {
}
