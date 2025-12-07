package com.lxp.course.domain;

import com.lxp.course.domain.mapper.ChapterCreateSpec;
import com.lxp.course.exception.BusinessException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.lxp.course.domain.common.CommonStaticFieldName.CHAPTER;
import static com.lxp.course.domain.common.CommonStaticFieldName.LESSON;
import static com.lxp.course.domain.common.CommonStaticFieldName.SEQ;
import static com.lxp.course.domain.common.CommonStaticFieldName.TITLE;
import static com.lxp.course.domain.common.CommonStaticFieldName.ALLOWED_TITLE_LENGTH;
import static com.lxp.course.domain.common.CommonValidator.requireNonNull;
import static com.lxp.course.domain.common.CommonValidator.requireNotBlank;
import static com.lxp.course.domain.exception.CourseErrorCode.*;

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
    @Column(updatable = false,  nullable = false)
    private Instant createTime;
    private Instant updateTime;

    private Chapter(String title, Integer seq, List<Lesson> lessons) {
        validateTitle(title);
        validateDuplicateLessonSeq(lessons);

        this.title = title;
        this.seq = requireNonNull(seq, SEQ);
        this.lessons = lessons;
        this.createTime = Instant.now();
        this.updateTime = Instant.now();
    }

    static Chapter create(ChapterCreateSpec spec) {
        List<Lesson> lessons =
            Optional.ofNullable(spec.lessonMappers())
                .orElse(List.of())
                .stream().map(Lesson::create).toList();

        return new Chapter(spec.title(), spec.seq(), lessons);
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
}
