package com.lxp.course.domain.mapper;

public record LessonCreateSpec(
    String title,
    Integer seq,
    String resourceUrl
) {}
