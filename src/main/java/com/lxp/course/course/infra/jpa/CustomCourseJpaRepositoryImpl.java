package com.lxp.course.course.infra.jpa;

import com.lxp.course.course.domain.Chapter;
import com.lxp.course.course.domain.Course;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.lxp.course.domain.QChapter.chapter;
import static com.lxp.course.domain.QCourse.course;
import static com.lxp.course.domain.QLesson.lesson;

@RequiredArgsConstructor
public class CustomCourseJpaRepositoryImpl implements CustomCourseJpaRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Course> findByIdWith(Long courseId) {
        Optional<Course> entity = Optional.ofNullable(
            jpaQueryFactory.selectFrom(course)
                .distinct()
                .leftJoin(course.chapters, chapter).fetchJoin()
                .where(course.id.eq(courseId))
                .fetchOne()
        );

        entity.ifPresent(e -> {
            List<Long> ids = e.getChapters().stream().map(Chapter::getId).toList();

            jpaQueryFactory.selectFrom(lesson).where(lesson.chapter.id.in(ids)).fetch();
        });

        return entity;
    }
}
