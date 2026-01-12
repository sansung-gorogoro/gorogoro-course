package com.lxp.course.course.application.port.in.dto;

import com.lxp.course.category.application.port.in.dto.CategoryPathDto;
import com.lxp.course.course.domain.Course;
import com.lxp.course.course.domain.enums.CourseDifficulty;

public record CourseSummaryInstructorDto(
    Long courseId,
    String title,
    String coverImageUrl,
    Integer price,
    CourseDifficulty difficulty,
    CategoryContent category
) {
    public record CategoryContent(
        Long id,
        String name,
        ParentCategoryContent parent
    ) {}

    public record ParentCategoryContent(
        Long id,
        String name
    ) {}

    private static CategoryContent of(CategoryPathDto categoryPathDto) {
        return new CategoryContent(
            categoryPathDto.childId(),
            categoryPathDto.childName(),
            new ParentCategoryContent(categoryPathDto.parentId(), categoryPathDto.parentName())
        );
    }

    public static CourseSummaryInstructorDto of(Course course, CategoryPathDto categoryPathDto) {
        return new CourseSummaryInstructorDto(
            course.getId(),
            course.getCourseBody().getTitle(),
            course.getCoverImageUrl(),
            course.getPrice().getValue(),
            course.getDifficulty(),
            of(categoryPathDto)
        );
    }
}
