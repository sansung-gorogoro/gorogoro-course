package com.lxp.course.course.domain.vo;

import com.lxp.course.exception.BusinessException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

import static com.lxp.course.course.domain.common.CommonStaticFieldName.ALLOWED_PRICE;
import static com.lxp.course.course.domain.exception.CourseErrorCode.PRICE_MUST_MORE_THAN_ZERO;

@Embeddable
public class Price {

    @Column(nullable = false)
    private Integer price;

    protected Price() { }

    public Price(Integer price) {
        if (price == null || price < ALLOWED_PRICE)
            throw BusinessException.builder(PRICE_MUST_MORE_THAN_ZERO).build();
        this.price = price;
    }

    public Price update(Integer price) {
        Integer newValue = (price == null) ? this.price : price;
        return newValue.equals(this.price) ? this : new Price(newValue);
    }

    public Integer getValue() { return price; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price that = (Price) o;
        return Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
