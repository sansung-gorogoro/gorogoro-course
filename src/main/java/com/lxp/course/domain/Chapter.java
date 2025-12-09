package com.lxp.course.domain;

import com.lxp.course.domain.spec.CreateCourseSpec.CreateChapterSpec;
import com.lxp.course.exception.BusinessException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.lxp.course.domain.common.CommonStaticFieldName.ALLOWED_TITLE_LENGTH;
import static com.lxp.course.domain.common.CommonStaticFieldName.CHAPTER;
import static com.lxp.course.domain.common.CommonStaticFieldName.COURSE;
import static com.lxp.course.domain.common.CommonStaticFieldName.LESSON;
import static com.lxp.course.domain.common.CommonStaticFieldName.SEQ;
import static com.lxp.course.domain.common.CommonStaticFieldName.TITLE;
import static com.lxp.course.domain.common.CommonValidator.requireNonNull;
import static com.lxp.course.domain.common.CommonValidator.requireNotBlank;
import static com.lxp.course.domain.exception.CourseErrorCode.CHAPTER_TITLE_TOO_LONG;
import static com.lxp.course.domain.exception.CourseErrorCode.DUPLICATED_SEQ;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = ALLOWED_TITLE_LENGTH)
    private String title;
    @Column(nullable = false)
    private Integer seq;
    @OneToMany(mappedBy = CHAPTER, cascade = CascadeType.PERSIST)
    private List<Lesson> lessons;
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
    @Column(updatable = false,  nullable = false)
    private Instant createTime;
    private Instant updateTime;

    private Chapter(String title, Integer seq, List<Lesson> lessons, Course course) {
        validateTitle(title);
        validateDuplicateLessonSeq(lessons);

        this.title = title;
        this.seq = requireNonNull(seq, SEQ);
        this.lessons = lessons;
        this.course = requireNonNull(course, COURSE);
        this.createTime = Instant.now();
        this.updateTime = Instant.now();
    }

    static Chapter create(CreateChapterSpec spec, Course course) {
        Chapter chapter = new Chapter(spec.title(), spec.seq(), new ArrayList<>(), course);

        List<Lesson> lessons =
            Optional.ofNullable(spec.lessonSpecs())
                .orElse(List.of())
                .stream().map(lessonSpec -> Lesson.create(lessonSpec, chapter)).toList();

        chapter.addAllLesson(lessons);

        return chapter;
    }

    private void validateDuplicateLessonSeq(List<Lesson> lessons) {
        if (lessons == null || lessons.isEmpty()) return;

        Set<Integer> seen = new HashSet<>();

        lessons.forEach(lesson -> {
            Integer seq = lesson.getSeq();

            if (!seen.add(seq)) {
                throw BusinessException.builder(DUPLICATED_SEQ)
                    .withField(LESSON, seq.toString())
                    .build();
            }
        });
    }

    private void validateTitle(String title) {
        requireNotBlank(title, CHAPTER, TITLE);

        if (title.length() > ALLOWED_TITLE_LENGTH)
            throw BusinessException.builder(CHAPTER_TITLE_TOO_LONG).build();
    }

    private void addAllLesson(List<Lesson> lessons) {
        this.lessons.addAll(lessons);
    }
}
