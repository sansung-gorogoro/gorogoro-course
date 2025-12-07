package com.lxp.course.domain.mapper;

import java.util.List;

public record ChapterCreateSpec(
    String title,
    Integer seq,
    List<LessonCreateSpec> lessonMappers
) {}
