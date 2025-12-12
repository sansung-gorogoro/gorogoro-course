package com.lxp.course.course.application.port.out;

import com.lxp.course.common.event.DomainEvent;

public interface CourseEventPublisher {
    void publish(DomainEvent event);
}
