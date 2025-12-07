package com.lxp.course.domain.vo;

import com.lxp.course.domain.exception.CourseErrorCode;
import com.lxp.course.exception.BusinessException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;

import static com.lxp.course.domain.common.CommonStaticFieldName.COURSE;
import static com.lxp.course.domain.common.CommonStaticFieldName.DESCRIPTION;
import static com.lxp.course.domain.common.CommonStaticFieldName.ALLOWED_DESCRIPTION_LENGTH;
import static com.lxp.course.domain.common.CommonStaticFieldName.SUMMARY;
import static com.lxp.course.domain.common.CommonStaticFieldName.ALLOWED_SUMMARY_LENGTH;
import static com.lxp.course.domain.common.CommonStaticFieldName.TITLE;
import static com.lxp.course.domain.common.CommonStaticFieldName.ALLOWED_TITLE_LENGTH;
import static com.lxp.course.domain.common.CommonValidator.requireNotBlank;
import static com.lxp.course.domain.exception.CourseErrorCode.COURSE_TITLE_TOO_LONG;

@Embeddable
public record CourseBody(
    @Column(nullable = false, length = ALLOWED_TITLE_LENGTH)
    String title,
    @Column(nullable = false, length = ALLOWED_SUMMARY_LENGTH)
    String summary,
    @Column(nullable = false)
    @Lob
    String description
) {
    public CourseBody {
        requireNotBlank(title, COURSE, TITLE);
        requireNotBlank(summary, SUMMARY, TITLE);
        requireNotBlank(description, DESCRIPTION, TITLE);

        if (title.length() > ALLOWED_TITLE_LENGTH)
            throw BusinessException.builder(COURSE_TITLE_TOO_LONG).build();

        if (summary.length() > ALLOWED_SUMMARY_LENGTH)
            throw BusinessException.builder(CourseErrorCode.COURSE_SUMMARY_TOO_LONG).build();

        if (description.length() > ALLOWED_DESCRIPTION_LENGTH)
            throw BusinessException.builder(CourseErrorCode.COURSE_DESCRIPTION_TOO_LONG).build();
    }

    public CourseBody getBody() {
        return new CourseBody(title, summary, description);
    }
}
