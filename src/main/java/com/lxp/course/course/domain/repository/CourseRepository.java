package com.lxp.course.course.domain.repository;

import com.lxp.course.course.domain.Course;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {
    Course save(Course course);
    Optional<Course> findByIdWith(Long id);
    List<Course> findAllByCategoryId(Long categoryId);
    void deleteById(Long id);
    Optional<Course> findById(Long id);
    List<Course> findAllByInstructorId(Long instructorId);
    List<Course> findByAllByIdsWith(List<Long> ids);
}
