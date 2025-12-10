package com.lxp.course.course.domain.vo;

import com.lxp.course.course.domain.exception.CourseErrorCode;
import com.lxp.course.exception.BusinessException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

import static com.lxp.course.course.domain.common.CommonStaticFieldName.COURSE;
import static com.lxp.course.course.domain.common.CommonStaticFieldName.DESCRIPTION;
import static com.lxp.course.course.domain.common.CommonStaticFieldName.ALLOWED_DESCRIPTION_LENGTH;
import static com.lxp.course.course.domain.common.CommonStaticFieldName.SUMMARY;
import static com.lxp.course.course.domain.common.CommonStaticFieldName.ALLOWED_SUMMARY_LENGTH;
import static com.lxp.course.course.domain.common.CommonStaticFieldName.TITLE;
import static com.lxp.course.course.domain.common.CommonStaticFieldName.ALLOWED_TITLE_LENGTH;
import static com.lxp.course.course.domain.common.CommonValidator.requireNotBlank;
import static com.lxp.course.course.domain.exception.CourseErrorCode.COURSE_TITLE_TOO_LONG;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseBody {

    @Column(nullable = false, length = ALLOWED_TITLE_LENGTH)
    private String title;

    @Column(nullable = false, length = ALLOWED_SUMMARY_LENGTH)
    private String summary;

    @Lob
    @Column(nullable = false)
    private String description;

    public CourseBody(String title, String summary, String description) {
        requireNotBlank(title, COURSE, TITLE);
        requireNotBlank(summary, SUMMARY, TITLE);
        requireNotBlank(description, DESCRIPTION, TITLE);

        if (title.length() > ALLOWED_TITLE_LENGTH)
            throw BusinessException.builder(COURSE_TITLE_TOO_LONG).build();

        if (summary.length() > ALLOWED_SUMMARY_LENGTH)
            throw BusinessException.builder(CourseErrorCode.COURSE_SUMMARY_TOO_LONG).build();

        if (description.length() > ALLOWED_DESCRIPTION_LENGTH)
            throw BusinessException.builder(CourseErrorCode.COURSE_DESCRIPTION_TOO_LONG).build();

        this.title = title;
        this.summary = summary;
        this.description = description;
    }

    public CourseBody update(String title, String summary, String description) {
        CourseBody newCourseBody = new CourseBody(
            title == null ? this.title : title,
            summary == null ? this.summary : summary,
            description == null ? this.description : description
        );
        return this.equals(newCourseBody) ? this : newCourseBody;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseBody that = (CourseBody) o;
        return Objects.equals(title, that.title)
            && Objects.equals(summary, that.summary)
            && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, summary, description);
    }
}
