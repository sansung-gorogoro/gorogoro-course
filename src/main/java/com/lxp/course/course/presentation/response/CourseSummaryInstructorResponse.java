package com.lxp.course.course.presentation.response;

import com.lxp.course.course.application.port.in.dto.CourseSummaryInstructorDto;
import com.lxp.course.course.domain.enums.CourseDifficulty;

import java.util.List;

public record CourseSummaryInstructorResponse(
    List<CourseSummaryInstructorContent> contents
) {
    record CourseSummaryInstructorContent(
        Long courseId,
        String title,
        String coverImageUrl,
        Integer price,
        CourseDifficulty difficulty
    ) {}

    public static CourseSummaryInstructorResponse of(List<CourseSummaryInstructorDto> dtos) {
        List<CourseSummaryInstructorContent> contents = dtos.stream().map(dto ->
            new CourseSummaryInstructorContent(
                dto.courseId(), dto.title(), dto.coverImageUrl(), dto.price(), dto.difficulty()
            )
        ).toList();

        return new CourseSummaryInstructorResponse(contents);
    }
}
