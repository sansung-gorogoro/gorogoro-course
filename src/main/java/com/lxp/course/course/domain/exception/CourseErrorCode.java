package com.lxp.course.course.domain.exception;

import com.lxp.course.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum CourseErrorCode implements ErrorCode {
    PRICE_MUST_MORE_THAN_ZERO("가격은 0보다 커야 합니다.", HttpStatus.BAD_REQUEST, "COS-0001"),
    ACCESS_DAY_MUST_MORE_THAN_ONE_DAY("수강기간은 1일 이상이어야 합니다.", HttpStatus.BAD_REQUEST, "COS-0002"),
    FIELD_REQUIRED("아래 필드가 누락되었습니다.", HttpStatus.BAD_REQUEST, "COS-0003"),

    COURSE_TITLE_TOO_LONG("강좌 제목은 최대 30자까지 가능합니다.", HttpStatus.BAD_REQUEST, "COS-0004"),
    COURSE_SUMMARY_TOO_LONG("강좌 요약 설명은 최대 255자까지 가능합니다.", HttpStatus.BAD_REQUEST, "COS-0006"),
    COURSE_DESCRIPTION_TOO_LONG("강좌 상세 설명은 최대 1000자까지 가능합니다.", HttpStatus.BAD_REQUEST, "COS-0008"),

    CHAPTER_TITLE_TOO_LONG("챕터 제목은 최대 30자까지 가능합니다.", HttpStatus.BAD_REQUEST, "COS-0010"),

    LESSON_TITLE_TOO_LONG("레슨 제목은 최대 30자까지 가능합니다.", HttpStatus.BAD_REQUEST, "COS-0010"),

    DUPLICATED_SEQ("중복된 시퀀스가 존재합니다.", HttpStatus.BAD_REQUEST, "COS-0011"),
    COURSE_NOT_FOUND("강좌를 찾을 수 없습니다.", HttpStatus.NOT_FOUND, "COS-0012"),
    COURSE_OWNERSHIP_EXCEPTION("해당 유저는 강의의 소유자가 아닙니다.", HttpStatus.FORBIDDEN, "COS-0013"),
    CATEGORY_NOT_FOUND("해당 강좌에 들어 있는 카테고리를 찾을 수 없습니다.", HttpStatus.NOT_FOUND, "COS-0014");
    private final String message;
    private final HttpStatus status;
    private final String code;

    CourseErrorCode(String message, HttpStatus status, String code) {
        this.message = message;
        this.status = status;
        this.code = code;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getCode() {
        return code;
    }
}
