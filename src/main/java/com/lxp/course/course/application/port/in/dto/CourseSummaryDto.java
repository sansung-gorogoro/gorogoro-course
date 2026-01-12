package com.lxp.course.course.application.port.in.dto;

import com.lxp.course.category.application.port.in.dto.CategoryPathDto;
import com.lxp.course.course.domain.Course;

public record CourseSummaryDto(
    Long courseId,
    String title,
    Integer price,
    String name,
    String coverImageUrl,
    CategoryContent category
) {
    public record CategoryContent(
        Long id,
        String name,
        ParentCategoryContent parent
    ) {
    }

    public record ParentCategoryContent(
        Long id,
        String name
    ) {
    }

    public static CourseSummaryDto of(Course course, CategoryPathDto dto) {
        return new CourseSummaryDto(
            course.getId(),
            course.getCourseBody().getTitle(),
            course.getPrice().getValue(),
            course.getInstructorName(),
            course.getCoverImageUrl(),
            new CategoryContent(dto.parentId(), dto.parentName(),
                new ParentCategoryContent(dto.childId(), dto.childName())
            )
        );
    }
}
