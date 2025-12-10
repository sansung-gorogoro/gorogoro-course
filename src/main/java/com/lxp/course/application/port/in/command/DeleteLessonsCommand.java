package com.lxp.course.application.port.in.command;

import java.util.List;

public record DeleteLessonsCommand(
    Long courseId,
    Long chapterId,
    Long instructorId,
    List<Long> lessonIds
) {
}
