package com.lxp.course.application.port.in;

import com.lxp.course.application.port.in.dto.CourseSummaryDto;
import com.lxp.course.application.port.in.dto.CourseSummaryInstructorDto;

import java.util.List;

public interface GetCourseUseCase {
    List<CourseSummaryDto> getCoursesSummaryExecute();

    List<CourseSummaryInstructorDto> getCoursesSummaryInstructorExecute(Long instructorId);
}
