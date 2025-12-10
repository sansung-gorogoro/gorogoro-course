package com.lxp.course.course.presentation.request;

import java.util.List;

public record DeleteLessonsRequest(
    List<Long> lessonIds
) {
}
