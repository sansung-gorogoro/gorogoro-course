package com.lxp.course.course.presentation.response;

import com.lxp.course.course.application.port.in.dto.CourseDetailDto;
import com.lxp.course.course.application.port.in.dto.CourseDetailDto.CategoryDetailDto;
import com.lxp.course.course.application.port.in.dto.CourseDetailDto.ChapterDetailsDto;
import com.lxp.course.course.application.port.in.dto.CourseDetailDto.LessonDetailsDto;
import com.lxp.course.course.domain.enums.CourseDifficulty;

import java.util.List;

public record CourseDetailResponse(
    Long courseId,
    String title,
    String summary,
    String description,
    Integer price,
    Integer accessDays,
    CategoryDetail categoryDetail,
    String instructorName,
    Long instructorId,
    String coverImageUrl,
    CourseDifficulty difficulty,
    List<ChapterDetails> chapters
) {
    record ChapterDetails(
        Long chapterId,
        String title,
        List<LessonDetails> lessons
    ) {
        public static ChapterDetails of(ChapterDetailsDto dto) {
            return new ChapterDetails(
                dto.chapterId(),
                dto.title(),
                dto.lessons().stream().map(LessonDetails::of).toList()
            );
        }
    }

    record LessonDetails(
        Long lessonId,
        String title,
        String resourceUrl
    ) {
        public static LessonDetails of(LessonDetailsDto dto) {
            return new LessonDetails(dto.lessonId(), dto.title(), dto.resourceUrl());
        }
    }

    public record CategoryDetail(Long categoryId, String name, SubCategoryDetail subCategoryDetailDto) {
        public record SubCategoryDetail(Long subCategoryId, String name) {
        }

        public static CategoryDetail of(CategoryDetailDto dto) {
            SubCategoryDetail subCategoryDetail = new SubCategoryDetail(
                dto.subCategoryDetailDto().subCategoryId(),
                dto.subCategoryDetailDto().name()
            );

            return new CategoryDetail(dto.categoryId(), dto.name(), subCategoryDetail);
        }
    }
    public static CourseDetailResponse of(CourseDetailDto dto) {
        return new CourseDetailResponse(
            dto.courseId(),
            dto.title(),
            dto.summary(),
            dto.description(),
            dto.price(),
            dto.accessDays(),
            CategoryDetail.of(dto.categoryDetailDto()),
            dto.instructorName(),
            dto.instructorId(),
            dto.coverImageUrl(),
            dto.difficulty(),
            dto.chapters().stream().map(ChapterDetails::of).toList()
        );
    }
}
