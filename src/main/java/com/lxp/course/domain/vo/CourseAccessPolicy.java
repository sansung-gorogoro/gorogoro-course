package com.lxp.course.domain.vo;

import com.lxp.course.exception.BusinessException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.lxp.course.domain.exception.CourseErrorCode.ACCESS_DAY_MUST_MORE_THAN_ONE_DAY;

@Embeddable
public class CourseAccessPolicy {

    @Column(nullable = false)
    private Integer accessDays;

    protected CourseAccessPolicy() { }

    public CourseAccessPolicy(Integer accessDays) {
        if (accessDays == null || accessDays <= 0)
            throw BusinessException.builder(ACCESS_DAY_MUST_MORE_THAN_ONE_DAY).build();

        this.accessDays = accessDays;
    }

    public CourseAccessPolicy update(Integer accessDays) {
        Integer newValue = (accessDays == null) ? this.accessDays : accessDays;
        return newValue.equals(this.accessDays) ? this : new CourseAccessPolicy(newValue);
    }

    public LocalDateTime calculateExpireAt(LocalDateTime purchaseTime) {
        return purchaseTime.plusDays(accessDays);
    }

    public Integer getAccessDays() { return accessDays; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseAccessPolicy that = (CourseAccessPolicy) o;
        return Objects.equals(accessDays, that.accessDays);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accessDays);
    }
}
