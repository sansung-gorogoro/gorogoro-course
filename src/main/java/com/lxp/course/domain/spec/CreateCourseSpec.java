package com.lxp.course.domain.spec;

import com.lxp.course.domain.enums.CourseDifficulty;
import com.lxp.course.domain.vo.CourseAccessPolicy;
import com.lxp.course.domain.vo.CourseBody;
import com.lxp.course.domain.vo.Price;

import java.util.List;

public record CreateCourseSpec(
    CourseBody courseBody,
    Long categoryId,
    Long instructorId,
    Price price,
    CourseAccessPolicy accessPolicy,
    String coverImageUrl,
    CourseDifficulty difficulty,
    List<CreateChapterSpec> chapterMappers
) {
    public record CreateChapterSpec(
        String title,
        Integer seq,
        List<CreateLessonSpec> lessonSpecs
    ) {}

    public record CreateLessonSpec(
        String title,
        Integer seq,
        String resourceUrl
    ) {}
}
