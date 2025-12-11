package com.lxp.course.course.infra.activemq;

import com.lxp.course.course.application.port.out.CourseEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static com.lxp.course.common.RabbitMQConstVal.COURSE_DELETED_ROUTING_KEY;
import static com.lxp.course.common.RabbitMQConstVal.COURSE_EVENT_V1_EXCHANGE;

@Component
@RequiredArgsConstructor
public class CourseEventPublisherHandler implements CourseEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void deleteEventPublish(Long courseId) {
        rabbitTemplate.convertAndSend(
            COURSE_EVENT_V1_EXCHANGE,
            COURSE_DELETED_ROUTING_KEY,
            courseId
        );
    }
}
