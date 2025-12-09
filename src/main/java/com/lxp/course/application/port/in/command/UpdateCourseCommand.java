package com.lxp.course.application.port.in.command;

import com.lxp.course.domain.enums.CourseDifficulty;
import com.lxp.course.domain.spec.UpdateCourseSpec;
import com.lxp.course.domain.spec.UpdateCourseSpec.UpdateChapterSpec;
import com.lxp.course.domain.spec.UpdateCourseSpec.UpdateLessonSpec;

import java.util.List;

public record UpdateCourseCommand(
    Long courseId,
    String title,
    String summary,
    String description,
    Long categoryId,
    Integer price,
    Integer accessDay,
    String coverImageUrl,
    CourseDifficulty courseDifficulty,
    List<UpdateChapterCommand> chapterCommands
) {
    public record UpdateChapterCommand(
        Long chapterId,
        String title,
        Integer seq,
        List<UpdateLessonCommand> lessonCommands
    ) {
        public UpdateChapterSpec toSpec() {
            List<UpdateLessonSpec> lessonSpecs =
                lessonCommands.stream().map(UpdateLessonCommand::toSpec).toList();

            return new UpdateChapterSpec(chapterId, title, seq, lessonSpecs);
        }
    }

    public record UpdateLessonCommand(
        Long lessonId,
        String title,
        Integer seq,
        String resourceUrl
    ) {
        public UpdateLessonSpec toSpec() {
            return new UpdateLessonSpec(lessonId, title, seq, resourceUrl);
        }
    }

    public UpdateCourseSpec toSpec() {
        List<UpdateChapterSpec> chapterSpecs =
            chapterCommands.stream().map(UpdateChapterCommand::toSpec).toList();

        return new UpdateCourseSpec(
            title, summary, description, categoryId,
            price, accessDay, coverImageUrl,
            courseDifficulty, chapterSpecs
        );
    }
}
