package com.lxp.course.course.presentation;

import com.lxp.course.course.application.port.in.CreateCourseUseCase;
import com.lxp.course.course.application.port.in.DeleteChapterUseCase;
import com.lxp.course.course.application.port.in.DeleteCourseUseCase;
import com.lxp.course.course.application.port.in.DeleteLessonUseCase;
import com.lxp.course.course.application.port.in.GetCourseUseCase;
import com.lxp.course.course.application.port.in.UpdateCourseUseCase;
import com.lxp.course.course.application.port.in.command.DeleteChaptersCommand;
import com.lxp.course.course.application.port.in.command.DeleteLessonsCommand;
import com.lxp.course.course.presentation.request.CreateCourseRequest;
import com.lxp.course.course.presentation.request.DeleteChaptersRequest;
import com.lxp.course.course.presentation.request.DeleteLessonsRequest;
import com.lxp.course.course.presentation.request.UpdateCourseRequest;
import com.lxp.course.course.presentation.response.CourseDetailResponse;
import com.lxp.course.course.presentation.response.CourseSummaryResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController implements CourseApi {
    private final CreateCourseUseCase createCourseUseCase;
    private final GetCourseUseCase getCourseUseCase;
    private final UpdateCourseUseCase updateCourseUseCase;
    private final DeleteCourseUseCase deleteCourseUseCase;
    private final DeleteChapterUseCase deleteChapterUseCase;
    private final DeleteLessonUseCase deleteLessonUseCase;

    @PostMapping
    public void createCourse(
        @RequestHeader("X-User-Id") Long instructorId,
        @Valid @RequestBody CreateCourseRequest request
    ) {
        createCourseUseCase.createExecute(request.toCommand(instructorId));
    }

    @GetMapping
    public CourseSummaryResponse getCoursesSummary(@RequestParam Long categoryId) {
        return CourseSummaryResponse.of(getCourseUseCase.getCoursesSummaryExecute(categoryId));
    }

    @GetMapping("/{courseId}")
    public CourseDetailResponse getCourse(@PathVariable Long courseId) {
        return CourseDetailResponse.of(getCourseUseCase.getCourseDetail(courseId));
    }

    @PutMapping("/{courseId}")
    public void updateCourse(
        @PathVariable Long courseId,
        @RequestHeader("X-User-Id") Long instructorId,
        @Valid @RequestBody UpdateCourseRequest request
    ) {
        updateCourseUseCase.updateExecute(request.toCommand(courseId, instructorId));
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(
        @PathVariable Long courseId,
        @RequestHeader("X-User-Id") Long instructorId
    ) {
        deleteCourseUseCase.deleteCourseExecute(courseId, instructorId);

        return ResponseEntity.status(NO_CONTENT).build();
    }

    @DeleteMapping("/{courseId}/chapters")
    public ResponseEntity<Void> deleteChapters(
        @PathVariable Long courseId,
        @RequestHeader("X-User-Id") Long instructorId,
        @RequestBody DeleteChaptersRequest request
    ) {
        deleteChapterUseCase.deleteChapterExecute(
            new DeleteChaptersCommand(courseId, instructorId, request.chapterIds())
        );

        return ResponseEntity.status(NO_CONTENT).build();
    }

    @DeleteMapping("/{courseId}/chapters/{chapterId}/lessons")
    public ResponseEntity<Void> deleteLessons(
        @PathVariable Long courseId,
        @PathVariable Long chapterId,
        @RequestHeader("X-User-Id") Long instructorId,
        @RequestBody DeleteLessonsRequest request
    ) {
        deleteLessonUseCase.deleteLessonExecute(
            new DeleteLessonsCommand(courseId, chapterId, instructorId, request.lessonIds())
        );

        return ResponseEntity.status(NO_CONTENT).build();
    }
}
