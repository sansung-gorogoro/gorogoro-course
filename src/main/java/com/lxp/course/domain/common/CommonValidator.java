package com.lxp.course.domain.common;

import com.lxp.course.exception.BusinessException;

import static com.lxp.course.domain.exception.CourseErrorCode.FIELD_REQUIRED;

public class CommonValidator {
    public static <T> T requireNonNull(T value, String field) {
        if (value == null) {
            throw BusinessException.builder(FIELD_REQUIRED).withField(field).build();
        }
        return value;
    }

    public static String requireNotBlank(String value, String... fields) {
        if (value == null || value.isBlank())
            throw BusinessException.builder(FIELD_REQUIRED)
                .withField(fields).build();

        return value;
    }
}
