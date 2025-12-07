package com.lxp.course.domain.vo;

import com.lxp.course.exception.BusinessException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import static com.lxp.course.domain.common.CommonStaticFieldName.ALLOWED_PRICE;
import static com.lxp.course.domain.exception.CourseErrorCode.PRICE_MUST_MORE_THAN_ZERO;

@Embeddable
public record Price(
    @Column(nullable = false)
    Integer price
) {
    public Price {
        if (price == null || price < ALLOWED_PRICE)
            throw BusinessException.builder(PRICE_MUST_MORE_THAN_ZERO).build();
    }

    public Price getPrice() {
        return new Price(price);
    }
}
