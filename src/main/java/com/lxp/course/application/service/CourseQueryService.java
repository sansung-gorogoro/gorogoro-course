package com.lxp.course.application.service;

import com.lxp.course.application.port.in.GetCourseUseCase;
import com.lxp.course.application.port.in.dto.CourseSummaryDto;
import com.lxp.course.domain.Course;
import com.lxp.course.domain.repository.CourseRepository;
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
}
