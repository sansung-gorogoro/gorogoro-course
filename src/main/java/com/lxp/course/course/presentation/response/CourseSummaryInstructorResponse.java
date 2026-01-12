package com.lxp.course.course.presentation.response;

import com.lxp.course.course.application.port.in.dto.CourseSummaryInstructorDto;
import com.lxp.course.course.domain.enums.CourseDifficulty;
import com.lxp.course.course.presentation.response.CourseSummaryInstructorResponse.CourseSummaryInstructorContent.CategoryContent;
import com.lxp.course.course.presentation.response.CourseSummaryInstructorResponse.CourseSummaryInstructorContent.ParentCategoryContent;

import java.util.List;

public record CourseSummaryInstructorResponse(
    List<CourseSummaryInstructorContent> contents
) {
    record CourseSummaryInstructorContent(
        Long courseId,
        String title,
        String coverImageUrl,
        Integer price,
        CourseDifficulty difficulty,
        CategoryContent category
    ) {
        record CategoryContent(
            Long id,
            String name,
            ParentCategoryContent parent
        ) {
        }

        record ParentCategoryContent(
            Long id,
            String name
        ) {
        }
    }

    public static CourseSummaryInstructorResponse of(List<CourseSummaryInstructorDto> dtos) {
        List<CourseSummaryInstructorContent> contents = dtos.stream().map(dto ->
            new CourseSummaryInstructorContent(
                dto.courseId(), dto.title(), dto.coverImageUrl(),
                dto.price(), dto.difficulty(),
                new CategoryContent(
                    dto.category().id(),
                    dto.category().name(),
                    new ParentCategoryContent(
                        dto.category().parent().id(),
                        dto.category().parent().name()
                    )
                )
            )
        ).toList();

        return new CourseSummaryInstructorResponse(contents);
    }
}
