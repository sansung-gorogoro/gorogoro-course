package com.lxp.course.course.infra.jpa;

import com.lxp.course.course.domain.Course;

import java.util.List;
import java.util.Optional;

public interface CustomCourseJpaRepository {
    Optional<Course> findByIdWith(Long courseId);
    List<Course> findAllByIdsWith(List<Long> courseIds);
    List<Course> findAllByCategoryId(Long categoryId);
}
