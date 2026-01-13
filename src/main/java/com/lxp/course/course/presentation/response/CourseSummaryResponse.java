package com.lxp.course.course.presentation.response;

import com.lxp.course.course.application.port.in.dto.CourseSummaryDto;

import java.util.List;

public record CourseSummaryResponse(
    List<CourseSummaryContent> contents
) {
    record CourseSummaryContent(
        Long courseId,
        String title,
        Integer price,
        String name,
        String coverImageUrl,
        CategoryContent category
    ) {}

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

    public static CourseSummaryResponse of(List<CourseSummaryDto> dtos) {
        List<CourseSummaryContent> items =
            dtos.stream().map(dto ->
                new CourseSummaryContent(
                    dto.courseId(),
                    dto.title(),
                    dto.price(),
                    dto.name(),
                    dto.coverImageUrl(),
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

        return new CourseSummaryResponse(items);
    }
}
