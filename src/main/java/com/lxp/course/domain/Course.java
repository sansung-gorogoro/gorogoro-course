package com.lxp.course.domain;

import com.lxp.course.domain.enums.CourseDifficulty;
import com.lxp.course.domain.mapper.CourseCreateSpec;
import com.lxp.course.domain.vo.CourseAccessPolicy;
import com.lxp.course.domain.vo.CourseBody;
import com.lxp.course.domain.vo.Price;
import com.lxp.course.exception.BusinessException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.lxp.course.domain.common.CommonStaticFieldName.CATEGORY;
import static com.lxp.course.domain.common.CommonStaticFieldName.CHAPTER;
import static com.lxp.course.domain.common.CommonStaticFieldName.COURSE;
import static com.lxp.course.domain.common.CommonStaticFieldName.COURSE_ACCESS_POLICY;
import static com.lxp.course.domain.common.CommonStaticFieldName.COURSE_BODY;
import static com.lxp.course.domain.common.CommonStaticFieldName.DIFFICULTY;
import static com.lxp.course.domain.common.CommonStaticFieldName.PRICE;
import static com.lxp.course.domain.common.CommonValidator.requireNonNull;
import static com.lxp.course.domain.exception.CourseErrorCode.DUPLICATED_SEQ;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private CourseBody courseBody;
    @Embedded
    private Price price;
    @Embedded
    private CourseAccessPolicy accessPolicy;
    @Column(nullable = false)
    private Long categoryId;
    private String coverImageUrl;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CourseDifficulty difficulty;
    @OneToMany(mappedBy = COURSE, cascade = CascadeType.PERSIST)
    private List<Chapter> chapters;

    @Column(updatable = false, nullable = false)
    private Instant createTime;
    private Instant updateTime;

    private Course(
        CourseBody courseBody, Long categoryId,
        Price price, CourseAccessPolicy accessPolicy,
        String coverImageUrl, CourseDifficulty difficulty,
        List<Chapter> chapters
    ) {
        //TODO(Chapter와 Lesson은 몇개까지 넣을 수 있게 할 것인지, inflearn기준 최소단위는 나와 있지만 최대 단위는 없음)

        this.courseBody = requireNonNull(courseBody, COURSE_BODY);
        this.categoryId = requireNonNull(categoryId, CATEGORY);
        this.price = requireNonNull(price, PRICE);
        this.accessPolicy = requireNonNull(accessPolicy, COURSE_ACCESS_POLICY);
        this.coverImageUrl = coverImageUrl;
        this.difficulty = requireNonNull(difficulty, DIFFICULTY);
        this.chapters = chapters;
        this.createTime = Instant.now();
        this.updateTime = Instant.now();

        validateDuplicateChapterSeq(chapters);
    }

    public static Course create(
        CourseCreateSpec mapper
    ) {
        List<Chapter> chapters = Optional.ofNullable(mapper.chapterMappers())
            .orElse(List.of())
            .stream().map(Chapter::create).toList();

        return new Course(
            mapper.courseBody(), mapper.categoryId(),
            mapper.price(), mapper.accessPolicy(),
            mapper.coverImageUrl(),
            mapper.difficulty(), chapters
        );
    }

    private void validateDuplicateChapterSeq(List<Chapter> chapters) {
        if (chapters == null || chapters.isEmpty()) return;

        Set<Integer> seen = new HashSet<>();

        chapters.forEach(chapter -> {
            Integer seq = chapter.getSeq();

            if (!seen.add(seq)) {
                throw BusinessException.builder(DUPLICATED_SEQ)
                    .withField(CHAPTER, seq.toString())
                    .build();
            }
        });
    }
}
