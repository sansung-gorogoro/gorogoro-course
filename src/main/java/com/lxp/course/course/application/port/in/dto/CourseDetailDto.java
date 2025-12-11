package com.lxp.course.course.application.port.in.dto;

import com.lxp.course.category.domain.Category;
import com.lxp.course.course.domain.Chapter;
import com.lxp.course.course.domain.Course;
import com.lxp.course.course.domain.Lesson;
import com.lxp.course.course.domain.enums.CourseDifficulty;

import java.util.List;

public record CourseDetailDto(
    Long courseId,
    String title,
    String summary,
    String description,
    Integer price,
    Integer accessDays,
    CategoryDetailDto categoryDetailDto,
    String instructorName,
    Long instructorId,
    String coverImageUrl,
    CourseDifficulty difficulty,
    List<ChapterDetailsDto> chapters
) {
    public record ChapterDetailsDto(
        Long chapterId,
        String title,
        List<LessonDetailsDto> lessons
    ) {
        public static ChapterDetailsDto of(Chapter chapter) {
            return new ChapterDetailsDto(
                chapter.getId(),
                chapter.getTitle(),
                chapter.getLessons().stream().map(LessonDetailsDto::of).toList()
            );
        }
    }

    public record LessonDetailsDto(
        Long lessonId,
        String title,
        String resourceUrl
    ) {
        public static LessonDetailsDto of(Lesson lesson) {
            return new LessonDetailsDto(lesson.getId(), lesson.getTitle(), lesson.getResourceUrl());
        }
    }

    public record CategoryDetailDto(Long categoryId, String name, SubCategoryDetailDto subCategoryDetailDto) {
        public record SubCategoryDetailDto(Long subCategoryId, String name) {}

        public static CategoryDetailDto of(Category category) {
            SubCategoryDetailDto subCategoryDetailDto = new SubCategoryDetailDto(category.getId(), category.getName());

            return new CategoryDetailDto(
                category.getParent().getId(),
                category.getParent().getName(),
                subCategoryDetailDto
            );
        }
    }

    public static CourseDetailDto of(Course course, Category category) {
        List<ChapterDetailsDto> chapterDtos = course.getChapters().stream().map(ChapterDetailsDto::of).toList();

        return new CourseDetailDto(
            course.getId(),
            course.getCourseBody().getTitle(),
            course.getCourseBody().getSummary(),
            course.getCourseBody().getDescription(),
            course.getPrice().getValue(),
            course.getAccessPolicy().getAccessDays(),
            CategoryDetailDto.of(category),
            course.getInstructorName(),
            course.getInstructorId(),
            course.getCoverImageUrl(),
            course.getDifficulty(),
            chapterDtos
        );
    }
}
