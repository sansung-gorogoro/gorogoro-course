package com.lxp.course.enrollment.controller.response;

import com.lxp.course.enrollment.service.dto.EnrolledCourseDto;

import java.util.List;

public record EnrolledCourseResponse(
    List<EnrolledCourseDetail> contents
) {
    record EnrolledCourseDetail(
        Long courseId,
        String courseTitle,
        String instructorName,
        String coverImage
    ) {
    }

    public static EnrolledCourseResponse from(List<EnrolledCourseDto> dtos) {
        return new EnrolledCourseResponse(
            dtos.stream().map(dto ->
                new EnrolledCourseDetail(
                    dto.courseId(),
                    dto.courseTitle(),
                    dto.instructorName(),
                    dto.coverImage()
                )
            ).toList()
        );
    }
}
