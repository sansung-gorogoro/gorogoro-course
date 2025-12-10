package com.lxp.course.course.application.port.in.dto;

import com.lxp.course.course.domain.Course;
import com.lxp.course.course.domain.enums.CourseDifficulty;

public record CourseSummaryInstructorDto(
    Long courseId,
    String title,
    String coverImageUrl,
    Integer price,
    CourseDifficulty difficulty
) {
    public static CourseSummaryInstructorDto of(Course course) {
        return new CourseSummaryInstructorDto(
            course.getId(),
            course.getCourseBody().getTitle(),
            course.getCoverImageUrl(),
            course.getPrice().getValue(),
            course.getDifficulty()
        );
    }
}
