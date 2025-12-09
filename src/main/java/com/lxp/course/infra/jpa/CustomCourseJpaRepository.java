package com.lxp.course.infra.jpa;

import com.lxp.course.domain.Course;

import java.util.Optional;

public interface CustomCourseJpaRepository {
    Optional<Course> findByIdWith(Long courseId);
}
