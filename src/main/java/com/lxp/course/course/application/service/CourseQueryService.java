package com.lxp.course.course.application.service;

import com.lxp.course.category.domain.Category;
import com.lxp.course.category.domain.exception.CategoryErrorCode;
import com.lxp.course.category.domain.repository.CategoryRepository;
import com.lxp.course.course.application.port.in.GetCourseUseCase;
import com.lxp.course.course.application.port.in.dto.CourseDetailDto;
import com.lxp.course.course.application.port.in.dto.CourseSummaryDto;
import com.lxp.course.course.application.port.in.dto.CourseSummaryInstructorDto;
import com.lxp.course.course.domain.Course;
import com.lxp.course.course.domain.repository.CourseRepository;
import com.lxp.course.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.lxp.course.course.domain.exception.CourseErrorCode.COURSE_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CourseQueryService implements GetCourseUseCase {
    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<CourseSummaryDto> getCoursesSummaryExecute(Long categoryId) {
        List<Course> foundCourses = courseRepository.findAllByCategoryId(categoryId);

        return foundCourses.stream().map(CourseSummaryDto::of).toList();
    }

    @Override
    public List<CourseSummaryInstructorDto> getCoursesSummaryInstructorExecute(Long instructorId) {
        return courseRepository.findAllByInstructorId(instructorId)
            .stream().map(CourseSummaryInstructorDto::of).toList();
    }

    @Override
    public CourseDetailDto getCourseDetail(Long courseId) {
        Course foundCourse = courseRepository.findByIdWith(courseId)
            .orElseThrow(() -> BusinessException.builder(COURSE_NOT_FOUND)
                    .withField(courseId.toString())
                    .build());

        Category foundCategory = categoryRepository.findById(foundCourse.getCategoryId())
            .orElseThrow(() -> BusinessException.builder(CategoryErrorCode.CATEGORY_NOT_FOUND).build());

        return CourseDetailDto.of(foundCourse, foundCategory);
    }
}
