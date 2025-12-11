package com.lxp.course.common;

public class RabbitMQConstVal {
    public static final String CART_COURSE_DELETE_QUEUE = "cart.course.deleted.queue";
    public static final String COURSE_DELETE_DLQ = "course.deleted.dead";
    public static final String COURSE_DELETE_RETRY = "course.deleted.retry";
    public static final String COURSE_EVENT_V1_EXCHANGE = "course.event.v1";
    public static final String COURSE_DLX_EXCHANGE = "course.dlx.v1";
    public static final String COURSE_RETRY_EXCHANGE = "course.retry.v1";
    public static final String COURSE_DELETED_ROUTING_KEY = "course.deleted";
}
