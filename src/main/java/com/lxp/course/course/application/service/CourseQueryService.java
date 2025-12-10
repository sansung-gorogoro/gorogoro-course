package com.lxp.course.course.application.service;

import com.lxp.course.course.application.port.in.GetCourseUseCase;
import com.lxp.course.course.application.port.in.dto.CourseSummaryDto;
import com.lxp.course.course.application.port.in.dto.CourseSummaryInstructorDto;
import com.lxp.course.course.domain.Course;
import com.lxp.course.course.domain.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CourseQueryService implements GetCourseUseCase {
    private final CourseRepository courseRepository;

    @Override
    public List<CourseSummaryDto> getCoursesSummaryExecute() {
        List<Course> foundCourses = courseRepository.findAll();

        return foundCourses.stream().map(CourseSummaryDto::of).toList();
    }

    @Override
    public List<CourseSummaryInstructorDto> getCoursesSummaryInstructorExecute(Long instructorId) {
        return courseRepository.findAllByInstructorId(instructorId)
            .stream().map(CourseSummaryInstructorDto::of).toList();
    }
}
