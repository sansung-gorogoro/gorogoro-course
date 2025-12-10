package com.lxp.course.course.application.port.in.command;

import java.util.List;

public record DeleteChaptersCommand(
    Long courseId,
    Long instructorId,
    List<Long> chapterIds
) {
}
