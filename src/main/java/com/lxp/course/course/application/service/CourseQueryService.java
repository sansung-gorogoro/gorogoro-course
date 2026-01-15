package com.lxp.course.course.application.service;

import com.lxp.course.category.application.port.in.GetCategoriesUseCase;
import com.lxp.course.category.application.port.in.dto.CategoryDto;
import com.lxp.course.category.application.port.in.dto.CategoryPathDto;
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
import java.util.Map;
import java.util.stream.Collectors;

import static com.lxp.course.course.domain.exception.CourseErrorCode.COURSE_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CourseQueryService implements GetCourseUseCase {
    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final GetCategoriesUseCase getCategoriesUseCase;

    @Override
    public List<CourseSummaryDto> getCoursesSummaryExecute(Long categoryId) {
        List<Course> foundCourses = courseRepository.findAllByCategoryId(categoryId);
        List<Long> categoryIds = foundCourses.stream().map(Course::getCategoryId).distinct().toList();

        Map<Long, CategoryPathDto> categoryPathDtoMap = getCategoriesUseCase.getCategoriesBySubCategoryIdExecute(categoryIds)
            .stream().collect(Collectors.toMap(
                CategoryPathDto::childId,
                dto ->  dto
            ));

        return foundCourses.stream().map(course ->
            CourseSummaryDto.of(course, categoryPathDtoMap.get(course.getCategoryId()))
        ).toList();
    }

    @Override
    public List<CourseSummaryInstructorDto> getCoursesSummaryInstructorExecute(Long instructorId) {
        List<Course> courses = courseRepository.findAllByInstructorId(instructorId);

        List<Long> categoryIds = courses.stream().map(Course::getCategoryId)
            .distinct()
            .toList();

        Map<Long, CategoryPathDto> categoryPaths =
            getCategoriesUseCase.getCategoriesBySubCategoryIdExecute(categoryIds)
                .stream().collect(Collectors.toMap(
                    CategoryPathDto::childId,
                dto -> dto
                ));

        return courses.stream().map(course ->
            CourseSummaryInstructorDto.of(course, categoryPaths.get(course.getCategoryId()))
        ).toList();
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

    @Override
    public List<CourseDetailDto> getCourseDetails(List<Long> courseIds) {
        List<Course> foundCourses = courseRepository.findByAllByIdsWith(courseIds);

        List<Long> categoryIds = foundCourses.stream().map(Course::getCategoryId).distinct().toList();

        Map<Long, Category> idFoundCategoryMap = categoryRepository.findAllByIds(categoryIds)
            .stream().collect(Collectors.toMap(Category::getId, category -> category));

        return foundCourses.stream().map(course ->
            CourseDetailDto.of(course, idFoundCategoryMap.get(course.getCategoryId()))
        ).toList();
    }

    @Override
    public Long getLectureIdFromCourse(Long courseId) {
        return courseRepository.findByIdWith(courseId)
            .orElseThrow(() -> BusinessException.builder(COURSE_NOT_FOUND)
                .withField(courseId.toString())
                .build())
            .getInstructorId();
    }
}
