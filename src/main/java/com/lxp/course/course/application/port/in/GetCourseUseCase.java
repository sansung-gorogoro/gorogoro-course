package com.lxp.course.course.application.port.in;

import com.lxp.course.course.application.port.in.dto.CourseDetailDto;
import com.lxp.course.course.application.port.in.dto.CourseSummaryDto;
import com.lxp.course.course.application.port.in.dto.CourseSummaryInstructorDto;

import java.util.List;

public interface GetCourseUseCase {
    List<CourseSummaryDto> getCoursesSummaryExecute(Long categoryId);

    List<CourseSummaryInstructorDto> getCoursesSummaryInstructorExecute(Long instructorId);

    CourseDetailDto getCourseDetail(Long courseId);
}
