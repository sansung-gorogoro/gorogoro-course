package com.lxp.course.course.domain.spec;

import com.lxp.course.course.domain.enums.CourseDifficulty;

import java.util.List;

public record UpdateCourseSpec(
    String title,
    String summary,
    String description,
    Long categoryId,
    Integer price,
    Integer accessDay,
    String coverImageUrl,
    CourseDifficulty courseDifficulty,
    List<UpdateChapterSpec> chapterCommands
) {
    public record UpdateChapterSpec(
        Long chapterId,
        String title,
        Integer seq,
        List<UpdateLessonSpec> lessonSpecs
    ) {}

    public record UpdateLessonSpec(
        Long lessonId,
        String title,
        Integer seq,
        String resourceUrl
    ) {}
}
