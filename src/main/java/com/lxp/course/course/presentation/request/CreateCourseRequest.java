package com.lxp.course.course.presentation.request;

import com.lxp.course.course.application.port.in.command.CreateCourseCommand;
import com.lxp.course.course.application.port.in.command.CreateCourseCommand.CreateChapterCommand;
import com.lxp.course.course.application.port.in.command.CreateCourseCommand.CreateLessonCommand;
import com.lxp.course.course.domain.enums.CourseDifficulty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateCourseRequest(
    @NotBlank(message = "강좌 제목을 작성해주세요.")
    @Size(max = 30, message = "강좌 제목은 30자까지만 가능합니다.")
    String title,
    @NotBlank(message = "강좌 소개를 작성해주세요.")
    @Size(max = 200, message = "강좌 소개는 200자까지만 가능합니다.")
    String summary,
    @NotBlank(message = "강좌 설명을 작성해주세요.")
    @Size(max = 1000, message = "강좌 설명은 1000자까지만 가능합니다.")
    String description,
    @NotNull(message = "카테고리를 선택해주세요.")
    Long categoryId,
    @NotNull(message = "금액을 입력해주세요.")
    @PositiveOrZero(message = "금액은 0이상 설젇해주세요.")
    Integer price,
    @NotBlank(message = "")
    String coverImageUrl,
    @NotNull(message = "강좌의 난이도를 선택해주세요")
    CourseDifficulty courseDifficulty,
    List<CreateChapterRequest> contents,
    @NotNull(message = "이용가능한 날을 선택해주세요")
    @PositiveOrZero(message = "이용 가능한 날은 0이상 설정해주세요.")
    Integer availableDays
) {
    record CreateChapterRequest(
        @NotBlank(message = "챕터 제목을 작성해주세요.")
        @Size(max = 30, message = "챕터 제목은 30자까지만 가능합니다.")
        String title,
        @NotNull(message = "챕터의 순서를 지정해주세요.")
        Integer seq,
        List<CreateLessonRequest> lessons
    ) {
        private CreateChapterCommand toCommand() {
            List<CreateLessonCommand> lessonCommands =
                lessons.stream().map(CreateLessonRequest::toCommand).toList();

            return new CreateChapterCommand(title, seq, lessonCommands);
        }

        record CreateLessonRequest(
            @NotBlank(message = "레슨 제목을 작성해주세요.")
            @Size(max = 30, message = "레슨 제목은 30자까지만 가능합니다.")
            String title,
            Integer seq,
            String resourceUrl
        ) {
            private CreateLessonCommand toCommand() {
                return new CreateLessonCommand(title, seq, resourceUrl);
            }
        }
    }

    public CreateCourseCommand toCommand(Long instructorId) {
        List<CreateChapterCommand> chapterCommands =
            contents.stream().map(CreateChapterRequest::toCommand).toList();

        return new CreateCourseCommand(
            title, summary, description,
            categoryId, instructorId, price, availableDays,
            coverImageUrl, courseDifficulty, chapterCommands
        );
    }
}
