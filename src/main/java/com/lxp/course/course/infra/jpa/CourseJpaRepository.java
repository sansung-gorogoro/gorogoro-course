package com.lxp.course.course.infra.jpa;

import com.lxp.course.course.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseJpaRepository extends JpaRepository<Course, Long>, CustomCourseJpaRepository {
    List<Course> findAllByInstructorId(Long instructorId);
}
