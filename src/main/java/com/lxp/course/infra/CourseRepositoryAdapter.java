package com.lxp.course.infra;

import com.lxp.course.domain.Course;
import com.lxp.course.domain.repository.CourseRepository;
import com.lxp.course.infra.jpa.CourseJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CourseRepositoryAdapter implements CourseRepository {
    private final CourseJpaRepository courseJpaRepository;

    @Override
    public Course save(Course course) {
        return courseJpaRepository.save(course);
    }

    @Override
    public Optional<Course> findByIdWith(Long id) {
        return courseJpaRepository.findByIdWith(id);
    }

    @Override
    public List<Course> findAll() {
        return courseJpaRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        courseJpaRepository.deleteById(id);
    }

    @Override
    public Optional<Course> findById(Long id) {
        return courseJpaRepository.findById(id);
    }
}
