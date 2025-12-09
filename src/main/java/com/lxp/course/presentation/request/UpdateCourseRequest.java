package com.lxp.course.presentation.request;

import com.lxp.course.application.port.in.command.UpdateCourseCommand;
import com.lxp.course.application.port.in.command.UpdateCourseCommand.UpdateChapterCommand;
import com.lxp.course.application.port.in.command.UpdateCourseCommand.UpdateLessonCommand;
import com.lxp.course.domain.enums.CourseDifficulty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UpdateCourseRequest(
    @Size(max = 30, message = "강좌 제목은 30자까지만 가능합니다.")
    String title,
    @Size(max = 200, message = "강좌 소개는 200자까지만 가능합니다.")
    String summary,
    @Size(max = 1000, message = "강좌 설명은 1000자까지만 가능합니다.")
    String description,
    Long categoryId,
    @PositiveOrZero(message = "금액은 0보다 크게 작성해주세요.")
    Integer price,
    String coverImageUrl,
    CourseDifficulty courseDifficulty,
    List<UpdateChapterRequest> contents,
    @PositiveOrZero(message = "이용 가능한 날은 0이상 설정해주세요.")
    Integer availableDays
) {
    record UpdateChapterRequest(
        @NotNull(message = "변경하려는 챕터의 id가 꼭 포함되어야 합니다.")
        Long chapterId,
        @Size(max = 30, message = "챕터 제목은 30자까지만 가능합니다.")
        String title,
        Integer seq,
        List<UpdateLessonRequest> lessons
    ) {
        private UpdateChapterCommand toCommand() {
            List<UpdateLessonCommand> lessonCommands =
                lessons.stream().map(UpdateLessonRequest::toCommand).toList();

            return new UpdateChapterCommand(chapterId, title, seq, lessonCommands);
        }

        record UpdateLessonRequest(
            @NotNull(message = "변경하려는 레슨의 id가 꼭 포함되어야 합니다.")
            Long lessonId,
            @Size(max = 30, message = "레슨 제목은 30자까지만 가능합니다.")
            String title,
            Integer seq,
            String resourceUrl
        ) {
            private UpdateLessonCommand toCommand() {
                return new UpdateLessonCommand(lessonId, title, seq, resourceUrl);
            }
        }
    }

    public UpdateCourseCommand toCommand(Long courseId) {
        List<UpdateChapterCommand> chapterCommands =
            contents.stream().map(UpdateChapterRequest::toCommand).toList();

        return new UpdateCourseCommand(
            courseId, title, summary, description,
            categoryId, price, availableDays,
            coverImageUrl, courseDifficulty, chapterCommands
        );
    }
}
