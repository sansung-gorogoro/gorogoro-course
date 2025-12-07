package com.lxp.course.domain.vo;

import com.lxp.course.exception.BusinessException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

import static com.lxp.course.domain.exception.CourseErrorCode.ACCESS_DAY_MUST_MORE_THAN_ONE_DAY;

@Embeddable
public record CourseAccessPolicy(
    @Column(nullable = false)
    Integer accessDays
) {
    public CourseAccessPolicy {
        if (accessDays == null || accessDays <= 0)
            throw BusinessException.builder(ACCESS_DAY_MUST_MORE_THAN_ONE_DAY).build();
    }

    public LocalDateTime calculateExpireAt(LocalDateTime purchaseTime) {
        return purchaseTime.plusDays(accessDays);
    }
}
