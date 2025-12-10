package com.lxp.course.course.presentation.request;

import java.util.List;

public record DeleteChaptersRequest(
    List<Long> chapterIds
) {
}
