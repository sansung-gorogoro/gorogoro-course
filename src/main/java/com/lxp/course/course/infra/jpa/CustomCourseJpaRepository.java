package com.lxp.course.course.infra.jpa;

import com.lxp.course.course.domain.Course;

import java.util.Optional;

public interface CustomCourseJpaRepository {
    Optional<Course> findByIdWith(Long courseId);
}
