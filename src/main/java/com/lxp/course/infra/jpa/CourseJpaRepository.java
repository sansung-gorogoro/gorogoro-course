package com.lxp.course.infra.jpa;

import com.lxp.course.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseJpaRepository extends JpaRepository<Course, Long>, CustomCourseJpaRepository {
}
