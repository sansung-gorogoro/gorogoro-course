package com.lxp.course.domain;

import com.lxp.course.exception.BusinessException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

import static com.lxp.course.domain.common.CommonStaticFieldName.ALLOWED_TITLE_LENGTH;
import static com.lxp.course.domain.common.CommonStaticFieldName.LESSON;
import static com.lxp.course.domain.common.CommonStaticFieldName.SEQ;
import static com.lxp.course.domain.common.CommonStaticFieldName.TITLE;
import static com.lxp.course.domain.common.CommonValidator.requireNonNull;
import static com.lxp.course.domain.common.CommonValidator.requireNotBlank;
import static com.lxp.course.domain.exception.CourseErrorCode.LESSON_TITLE_TOO_LONG;
import static com.lxp.course.domain.spec.CreateCourseSpec.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = ALLOWED_TITLE_LENGTH)
    private String title;
    @Column(nullable = false)
    private Integer seq;
    @Column(nullable = false)
    private String resourceUrl;
    @Column(updatable = false,  nullable = false)
    private Instant createTime;
    private Instant updateTime;

    private Lesson(String title, Integer seq, String resourceUrl) {
        validateTitle(title);

        this.title = title;
        this.seq = requireNonNull(seq, SEQ);
        this.resourceUrl = resourceUrl;
        this.createTime = Instant.now();
        this.updateTime = Instant.now();
    }

    static Lesson create(CreateLessonSpec spec) {
        return new Lesson(spec.title(), spec.seq(), spec.resourceUrl());
    }

    private void validateTitle(String title) {
        requireNotBlank(title, LESSON, TITLE);

        if (title.length() > ALLOWED_TITLE_LENGTH)
            throw BusinessException.builder(LESSON_TITLE_TOO_LONG).build();
    }
}
