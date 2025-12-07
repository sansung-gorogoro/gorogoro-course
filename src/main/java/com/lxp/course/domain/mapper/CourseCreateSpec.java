package com.lxp.course.domain.mapper;

import com.lxp.course.domain.enums.CourseDifficulty;
import com.lxp.course.domain.vo.CourseAccessPolicy;
import com.lxp.course.domain.vo.CourseBody;
import com.lxp.course.domain.vo.Price;

import java.util.List;

public record CourseCreateSpec(
    CourseBody courseBody,
    Long categoryId,
    Price price,
    CourseAccessPolicy accessPolicy,
    String coverImageUrl,
    CourseDifficulty difficulty,
    List<ChapterCreateSpec> chapterMappers
) {
}
