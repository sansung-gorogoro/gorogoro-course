package com.lxp.course.application.port.in.command;

import com.lxp.course.domain.enums.CourseDifficulty;
import com.lxp.course.domain.spec.CreateCourseSpec;
import com.lxp.course.domain.spec.CreateCourseSpec.CreateChapterSpec;
import com.lxp.course.domain.spec.CreateCourseSpec.CreateLessonSpec;
import com.lxp.course.domain.vo.CourseAccessPolicy;
import com.lxp.course.domain.vo.CourseBody;
import com.lxp.course.domain.vo.Price;

import java.util.List;

public record CreateCourseCommand(
    String title,
    String summary,
    String description,
    Long categoryId,
    Integer price,
    Integer accessDay,
    String coverImageUrl,
    CourseDifficulty courseDifficulty,
    List<CreateChapterCommand> chapterCommands
) {
    public record CreateChapterCommand(
        String title,
        Integer seq,
        List<CreateLessonCommand> lessonCommands
    ) {
        public CreateChapterSpec toSpec() {
            List<CreateLessonSpec> lessonSpecs =
                lessonCommands.stream().map(CreateLessonCommand::toSpec).toList();

            return new CreateChapterSpec(title, seq, lessonSpecs);
        }
    }

    public record CreateLessonCommand(
        String title,
        Integer seq,
        String resourceUrl
    ) {
        public CreateLessonSpec toSpec() {
            return new CreateLessonSpec(title, seq, resourceUrl);
        }
    }

    public CreateCourseSpec toSpec() {
        List<CreateChapterSpec> chapterSpecs =
            chapterCommands.stream().map(CreateChapterCommand::toSpec).toList();

        Price price = new Price(this.price);
        CourseBody courseBody = new CourseBody(title, summary, description);
        CourseAccessPolicy accessPolicy = new CourseAccessPolicy(accessDay);

        return new CreateCourseSpec(
            courseBody, categoryId, price,
            accessPolicy, coverImageUrl,
            courseDifficulty, chapterSpecs
        );
    }
}
