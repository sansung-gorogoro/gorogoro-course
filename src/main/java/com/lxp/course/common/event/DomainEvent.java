package com.lxp.course.common.event;

public interface DomainEvent {
    String type();
    String version();
}
