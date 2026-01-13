package com.lxp.course.course.domain.repository.dto;

public record CourseSummaryDto(
    Long courseId,
    String title,
    Integer price,
    String name,
    String coverImageUrl,

    CategorySummary category
) {
    public record CategorySummary(
        Long id,
        String name,
        ParentCategorySummary parent
    ) {}

    public record ParentCategorySummary(
        Long id,
        String name
    ) {}
}
