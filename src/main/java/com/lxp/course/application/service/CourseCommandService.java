package com.lxp.course.application.service;

import com.lxp.course.application.port.in.CreateCourseUseCase;
import com.lxp.course.application.port.in.command.CreateCourseCommand;
import com.lxp.course.domain.Course;
import com.lxp.course.domain.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseCommandService implements CreateCourseUseCase {
    private final CourseRepository courseRepository;

    @Override
    public void createExecute(CreateCourseCommand command) {
        courseRepository.save(Course.create(command.toSpec()));
    }
}
