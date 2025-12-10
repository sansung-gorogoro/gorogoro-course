package com.lxp.course.presentation;

import com.lxp.course.application.port.in.CreateCourseUseCase;
import com.lxp.course.application.port.in.DeleteChapterUseCase;
import com.lxp.course.application.port.in.DeleteCourseUseCase;
import com.lxp.course.application.port.in.DeleteLessonUseCase;
import com.lxp.course.application.port.in.UpdateCourseUseCase;
import com.lxp.course.application.port.in.command.DeleteChaptersCommand;
import com.lxp.course.application.port.in.command.DeleteLessonsCommand;
import com.lxp.course.presentation.request.CreateCourseRequest;
import com.lxp.course.presentation.request.DeleteChaptersRequest;
import com.lxp.course.presentation.request.DeleteLessonsRequest;
import com.lxp.course.presentation.request.UpdateCourseRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController implements CourseApi {
    private final CreateCourseUseCase createCourseUseCase;
    private final UpdateCourseUseCase updateCourseUseCase;
    private final DeleteCourseUseCase deleteCourseUseCase;
    private final DeleteChapterUseCase deleteChapterUseCase;
    private final DeleteLessonUseCase deleteLessonUseCase;

    @PostMapping
    public void createCourse(@Valid @RequestBody CreateCourseRequest request) {
        createCourseUseCase.createExecute(request.toCommand(1L));
    }

    @PutMapping("/{courseId}")
    public void updateCourse(
        @PathVariable Long courseId,
        @Valid @RequestBody UpdateCourseRequest request
    ) {
        updateCourseUseCase.updateExecute(request.toCommand(courseId));
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(
        @PathVariable Long courseId,
        Long userId
    ) {
        deleteCourseUseCase.deleteCourseExecute(courseId, 1L);

        return ResponseEntity.status(NO_CONTENT).build();
    }

    @DeleteMapping("/{courseId}/chapters")
    public ResponseEntity<Void> deleteChapters(
        @PathVariable Long courseId,
        @RequestBody DeleteChaptersRequest request
    ) {
        deleteChapterUseCase.deleteChapterExecute(
            new DeleteChaptersCommand(courseId, 1L, request.chapterIds())
        );

        return ResponseEntity.status(NO_CONTENT).build();
    }

    @DeleteMapping("/{courseId}/chapters/{chapterId}/lessons")
    public ResponseEntity<Void> deleteLessons(
        @PathVariable Long courseId,
        @PathVariable Long chapterId,
        @RequestBody DeleteLessonsRequest request
    ) {
        deleteLessonUseCase.deleteLessonExecute(
            new DeleteLessonsCommand(courseId, chapterId, 1L, request.lessonIds())
        );

        return ResponseEntity.status(NO_CONTENT).build();
    }
}
