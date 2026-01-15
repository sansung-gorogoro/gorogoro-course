package com.lxp.course.enrollment.service.dto;

public record EnrolledCourseDto(
    Long courseId,
    String courseTitle,
    String instructorName,
    String coverImage
) {
}
