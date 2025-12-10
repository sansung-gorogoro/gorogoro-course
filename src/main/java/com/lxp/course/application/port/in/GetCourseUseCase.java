package com.lxp.course.application.port.in;

import com.lxp.course.application.port.in.dto.CourseSummaryDto;

import java.util.List;

public interface GetCourseUseCase {
    List<CourseSummaryDto> getCoursesSummaryExecute();
}
