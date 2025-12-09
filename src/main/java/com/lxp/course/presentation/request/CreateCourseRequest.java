package com.lxp.course.presentation.request;

import com.lxp.course.application.port.in.command.CreateCourseCommand;
import com.lxp.course.application.port.in.command.CreateCourseCommand.CreateChapterCommand;
import com.lxp.course.application.port.in.command.CreateCourseCommand.CreateLessonCommand;
import com.lxp.course.domain.enums.CourseDifficulty;

import java.util.List;

public record CreateCourseRequest(
    String title,
    String summary,
    String description,
    Long categoryId,
    Integer price,
    String coverImageUrl,
    CourseDifficulty courseDifficulty,
    List<CreateChapterRequest> contents,
    Integer availableDays
) {
    record CreateChapterRequest(
        String title,
        Integer seq,
        List<CreateLessonRequest> lessons
    ) {
        private CreateChapterCommand toCommand() {
            List<CreateLessonCommand> lessonCommands =
                lessons.stream().map(CreateLessonRequest::toCommand).toList();

            return new CreateChapterCommand(title, seq, lessonCommands);
        }

        record CreateLessonRequest(
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
