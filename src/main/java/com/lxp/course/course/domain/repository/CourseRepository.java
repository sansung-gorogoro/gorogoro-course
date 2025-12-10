package com.lxp.course.course.domain.repository;

import com.lxp.course.course.domain.Course;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {
    Course save(Course course);
    Optional<Course> findByIdWith(Long id);
    List<Course> findAll();
    void deleteById(Long id);
    Optional<Course> findById(Long id);
    List<Course> findAllByInstructorId(Long instructorId);
}
