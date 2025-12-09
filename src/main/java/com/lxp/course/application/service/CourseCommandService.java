package com.lxp.course.application.service;

import com.lxp.course.application.port.in.CreateCourseUseCase;
import com.lxp.course.application.port.in.UpdateCourseUseCase;
import com.lxp.course.application.port.in.command.CreateCourseCommand;
import com.lxp.course.application.port.in.command.UpdateCourseCommand;
import com.lxp.course.domain.Course;
import com.lxp.course.domain.exception.CourseErrorCode;
import com.lxp.course.domain.repository.CourseRepository;
import com.lxp.course.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseCommandService implements CreateCourseUseCase, UpdateCourseUseCase {
    private final CourseRepository courseRepository;

    @Override
    public void createExecute(CreateCourseCommand command) {
        courseRepository.save(Course.create(command.toSpec()));
    }

    @Override
    public void updateExecute(UpdateCourseCommand command) {
        Course course = findByIdOrThrow(command.courseId());

        course.update(command.toSpec());
    }

    private Course findByIdOrThrow(Long courseId) {
        return courseRepository.findById(courseId)
            .orElseThrow(() ->
                BusinessException.builder(CourseErrorCode.COURSE_NOT_FOUND)
                    .withField(courseId.toString())
                    .build()
            );
    }
}
