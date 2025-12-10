package com.lxp.course.presentation.request;

import java.util.List;

public record DeleteChaptersRequest(
    List<Long> chapterIds
) {
}
