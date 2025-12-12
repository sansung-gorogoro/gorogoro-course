package com.lxp.course.course.domain.event;

import com.lxp.course.common.event.DomainEvent;

public record CourseDeleteEvent(
    Long courseId,
    String type,
    String version
) implements DomainEvent {
    @Override
    public String type() {
        return type;
    }

    @Override
    public String version() {
        return version;
    }
}
