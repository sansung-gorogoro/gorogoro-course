package com.lxp.course.application.port.in.command;

import java.util.List;

public record DeleteChaptersCommand(
    Long courseId,
    List<Long> chapterIds
) {
}
