package com.lxp.course.domain.repository;

import com.lxp.course.domain.Course;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {
    Course save(Course course);
    Optional<Course> findById(Long id);
    List<Course> findAll();
}
