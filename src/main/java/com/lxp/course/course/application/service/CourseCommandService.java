package com.lxp.course.course.application.service;

import com.lxp.course.common.event.DomainEvent;
import com.lxp.course.course.application.port.in.CreateCourseUseCase;
import com.lxp.course.course.application.port.in.DeleteChapterUseCase;
import com.lxp.course.course.application.port.in.DeleteCourseUseCase;
import com.lxp.course.course.application.port.in.DeleteLessonUseCase;
import com.lxp.course.course.application.port.in.UpdateCourseUseCase;
import com.lxp.course.course.application.port.in.command.CreateCourseCommand;
import com.lxp.course.course.application.port.in.command.DeleteChaptersCommand;
import com.lxp.course.course.application.port.in.command.DeleteLessonsCommand;
import com.lxp.course.course.application.port.in.command.UpdateCourseCommand;
import com.lxp.course.course.application.port.in.command.UpdateCourseCommand.UpdateChapterCommand;
import com.lxp.course.course.application.port.out.CourseEventPublisher;
import com.lxp.course.course.application.port.out.UserPort;
import com.lxp.course.course.application.port.out.dto.UserDetailDto;
import com.lxp.course.course.domain.Course;
import com.lxp.course.course.domain.event.CourseDeleteEvent;
import com.lxp.course.course.domain.exception.CourseErrorCode;
import com.lxp.course.course.domain.repository.CourseRepository;
import com.lxp.course.course.domain.spec.CreateCourseSpec;
import com.lxp.course.course.domain.spec.CreateCourseSpec.CreateChapterSpec;
import com.lxp.course.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.lxp.course.course.domain.common.CommonStaticFieldName.DELETE_TYPE;
import static com.lxp.course.course.domain.common.CommonStaticFieldName.EVENT_VERSION;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseCommandService implements
    CreateCourseUseCase, UpdateCourseUseCase, DeleteCourseUseCase,
    DeleteChapterUseCase, DeleteLessonUseCase {
    private final CourseRepository courseRepository;
    private final CourseEventPublisher courseEventPublisher;
    private final UserPort userPort;

    @Override
    public void createExecute(CreateCourseCommand command) {
        UserDetailDto userDetail = userPort.getUserDetail(command.instructorId());

        courseRepository.save(Course.create(command.toSpec(userDetail.nickname())));
    }

    @Override
    public void updateExecute(UpdateCourseCommand command) {
        Course course = findByIdWithOrThrow(command.courseId());
        validateOwnership(course.getInstructorId(), command.instructorId());

        course.update(command.toSpec());

        command.chapterCommands().stream()
            .filter(chapterCommand -> chapterCommand.chapterId() != null)
            .forEach(chapterCommand -> {
                List<CreateCourseSpec.CreateLessonSpec> createLessonSpecs = chapterCommand.lessonCommands().stream()
                    .filter(lessonCommand -> lessonCommand.lessonId() == null)
                    .map(UpdateCourseCommand.UpdateLessonCommand::toCreateSpec).toList();

                course.addLessons(createLessonSpecs, chapterCommand.chapterId());
            });

        List<CreateChapterSpec> chapterCreateSpecs = command.chapterCommands().stream()
            .filter(chapterCommand -> chapterCommand.chapterId() == null)
            .map(UpdateChapterCommand::toCreateSpec)
            .toList();

        course.addChapters(chapterCreateSpecs);
    }

    @Override
    public void deleteCourseExecute(Long courseId, Long instructorId) {
        Course course = findByIdOrThrow(courseId);
        validateOwnership(course.getInstructorId(), instructorId);

        courseRepository.deleteById(courseId);

        DomainEvent event = new CourseDeleteEvent(courseId, DELETE_TYPE, EVENT_VERSION);
        courseEventPublisher.publish(event);
    }

    @Override
    public void deleteChapterExecute(DeleteChaptersCommand command) {
        Course foundCourse = findByIdWithOrThrow(command.courseId());
        validateOwnership(foundCourse.getInstructorId(), command.instructorId());

        foundCourse.deleteChapters(command.chapterIds());
    }

    @Override
    public void deleteLessonExecute(DeleteLessonsCommand command) {
        Course foundCourse = findByIdWithOrThrow(command.courseId());
        validateOwnership(foundCourse.getInstructorId(), command.instructorId());

        foundCourse.deleteLessons(command.chapterId(), command.lessonIds());
    }

    private Course findByIdWithOrThrow(Long courseId) {
        return courseRepository.findByIdWith(courseId)
            .orElseThrow(() ->
                BusinessException.builder(CourseErrorCode.COURSE_NOT_FOUND)
                    .withField(courseId.toString())
                    .build()
            );
    }

    private Course findByIdOrThrow(Long courseId) {
        return courseRepository.findById(courseId)
            .orElseThrow(() ->
                BusinessException.builder(CourseErrorCode.COURSE_NOT_FOUND)
                    .withField(courseId.toString())
                    .build()
            );
    }

    private void validateOwnership(Long instructorIdFromCourse, Long instructorIdFromAuth) {
        if (!Objects.equals(instructorIdFromCourse, instructorIdFromAuth)) {
            throw BusinessException.builder(CourseErrorCode.COURSE_OWNERSHIP_EXCEPTION)
                .build();
        }
    }
}
