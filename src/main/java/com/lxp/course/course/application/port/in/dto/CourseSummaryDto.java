package com.lxp.course.course.application.port.in.dto;

import com.lxp.course.course.domain.Course;

public record CourseSummaryDto(
    Long courseId,
    String title,
    Integer price,
    String name,
    String coverImageUrl
) {
    public static CourseSummaryDto of(Course course) {
        return new CourseSummaryDto(
            course.getId(),
            course.getCourseBody().getTitle(),
            course.getPrice().getValue(),
            course.getInstructorName(),
            course.getCoverImageUrl()
        );
    }
}
