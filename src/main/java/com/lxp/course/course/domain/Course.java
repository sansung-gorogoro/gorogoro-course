package com.lxp.course.course.domain;

import com.lxp.course.course.domain.enums.CourseDifficulty;
import com.lxp.course.course.domain.spec.CreateCourseSpec;
import com.lxp.course.course.domain.spec.CreateCourseSpec.CreateChapterSpec;
import com.lxp.course.course.domain.spec.CreateCourseSpec.CreateLessonSpec;
import com.lxp.course.course.domain.spec.UpdateCourseSpec;
import com.lxp.course.course.domain.spec.UpdateCourseSpec.UpdateChapterSpec;
import com.lxp.course.course.domain.vo.CourseAccessPolicy;
import com.lxp.course.course.domain.vo.CourseBody;
import com.lxp.course.course.domain.vo.Price;
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
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.lxp.course.course.domain.common.CommonStaticFieldName.CATEGORY;
import static com.lxp.course.course.domain.common.CommonStaticFieldName.CHAPTER;
import static com.lxp.course.course.domain.common.CommonStaticFieldName.COURSE;
import static com.lxp.course.course.domain.common.CommonStaticFieldName.COURSE_ACCESS_POLICY;
import static com.lxp.course.course.domain.common.CommonStaticFieldName.COURSE_BODY;
import static com.lxp.course.course.domain.common.CommonStaticFieldName.DIFFICULTY;
import static com.lxp.course.course.domain.common.CommonStaticFieldName.INSTRUCTOR_ID;
import static com.lxp.course.course.domain.common.CommonStaticFieldName.PRICE;
import static com.lxp.course.course.domain.common.CommonValidator.requireNonNull;
import static com.lxp.course.course.domain.exception.CourseErrorCode.DUPLICATED_SEQ;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;
    @Embedded
    @Getter
    private CourseBody courseBody;
    @Embedded
    @Getter
    private Price price;
    @Embedded
    private CourseAccessPolicy accessPolicy;
    @Column(nullable = false)
    private Long categoryId;
    //TODO(강사 이름 필요)
    @Getter
    @Column(nullable = false)
    private String instructorName;
    @Getter
    @Column(nullable = false)
    private Long instructorId;
    @Getter
    private String coverImageUrl;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Getter
    private CourseDifficulty difficulty;
    @OneToMany(mappedBy = COURSE, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Chapter> chapters;

    @Column(updatable = false, nullable = false)
    private Instant createTime;
    private Instant updateTime;

    private Course(
        CourseBody courseBody, Long categoryId, Long instructorId,
        Price price, CourseAccessPolicy accessPolicy,
        String coverImageUrl, CourseDifficulty difficulty,
        List<Chapter> chapters
    ) {
        //TODO(Chapter와 Lesson은 몇개까지 넣을 수 있게 할 것인지, inflearn기준 최소단위는 나와 있지만 최대 단위는 없음)
        this.courseBody = requireNonNull(courseBody, COURSE_BODY);
        this.categoryId = requireNonNull(categoryId, CATEGORY);
        this.instructorId = requireNonNull(instructorId, INSTRUCTOR_ID);
        this.price = requireNonNull(price, PRICE);
        this.accessPolicy = requireNonNull(accessPolicy, COURSE_ACCESS_POLICY);
        this.coverImageUrl = coverImageUrl;
        this.difficulty = requireNonNull(difficulty, DIFFICULTY);
        this.chapters = chapters;
        this.createTime = Instant.now();
        this.updateTime = Instant.now();
    }

    public static Course create(
        CreateCourseSpec spec
    ) {
        Course course = new Course(
            spec.courseBody(), spec.categoryId(),
            spec.instructorId(), spec.price(),
            spec.accessPolicy(), spec.coverImageUrl(),
            spec.difficulty(), new ArrayList<>()
        );

        course.addAllChapter(spec.chapterSpecs(), course);

        return course;
    }

    public void update(UpdateCourseSpec spec) {
        CourseBody updatedCourseBody = courseBody.update(spec.title(), spec.summary(), spec.description());
        Price updatedPrice = price.update(spec.price());
        CourseAccessPolicy updatedAccessPolicy = accessPolicy.update(spec.accessDay());

        this.courseBody = updatedCourseBody;
        this.categoryId = spec.categoryId() == null ? this.categoryId : spec.categoryId();
        this.price = updatedPrice;
        this.accessPolicy = updatedAccessPolicy;
        this.coverImageUrl = spec.coverImageUrl() == null ? this.coverImageUrl : spec.coverImageUrl();
        this.difficulty = spec.courseDifficulty() == null ? this.difficulty : spec.courseDifficulty();

        updateChapter(spec.chapterCommands());
        validateDuplicateChapterSeq(chapters);

        updatedTime();
    }

    private void updateChapter(List<UpdateChapterSpec> changes) {
        Map<Long, UpdateChapterSpec> idChangeMap = changes.stream().collect(Collectors.toMap(
            UpdateChapterSpec::chapterId,
            Function.identity()
        ));

        this.chapters.forEach(chapter ->
            Optional.ofNullable(idChangeMap.get(chapter.getId()))
                .ifPresent(chapter::update)
        );
    }

    public void addChapters(List<CreateChapterSpec> specs) {
        addAllChapter(specs, this);
    }

    public void addLessons(List<CreateLessonSpec> specs, Long chapterId) {
        chapters.stream().filter(chapter -> chapter.getId().equals(chapterId))
            .findFirst().ifPresent(chapter -> chapter.addLessons(specs));
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

    private void addAllChapter(List<CreateChapterSpec> specs, Course course) {
        List<Chapter> chapters = Optional.ofNullable(specs)
            .orElse(List.of())
            .stream().map(spec -> Chapter.create(spec, course)).toList();

        this.chapters.addAll(chapters);

        validateDuplicateChapterSeq(this.chapters);
    }

    public List<Chapter> getChapters() {
        return List.copyOf(chapters);
    }

    public void deleteChapters(List<Long> chapterIds) {
        chapterIds.forEach(chapterId ->
            chapters.removeIf(chapter -> chapter.getId().equals(chapterId))
        );
    }

    public void deleteLessons(Long chapterId, List<Long> lessonIds) {
        chapters.stream().filter(chapter -> chapter.getId().equals(chapterId))
            .findFirst().ifPresent(chapter -> chapter.deleteLessons(lessonIds));
    }

    private void updatedTime() {
        this.updateTime = Instant.now();
    }
}
