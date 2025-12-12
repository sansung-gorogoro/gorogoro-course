package com.lxp.course.course.infra.activemq;

import com.lxp.course.common.event.DomainEvent;
import com.lxp.course.common.event.EventEnvelope;
import com.lxp.course.course.application.port.out.CourseEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.lxp.course.common.RabbitMQConstVal.COURSE_DELETED_ROUTING_KEY;
import static com.lxp.course.common.RabbitMQConstVal.COURSE_EVENT_V1_EXCHANGE;

@Component
@RequiredArgsConstructor
public class CourseEventPublisherAdaptor implements CourseEventPublisher {
    private final RabbitTemplate template;


    @Override
    public void publish(DomainEvent event) {
        EventEnvelope<DomainEvent> envelope = EventEnvelope.wrap(event);
        CorrelationData correlation = new CorrelationData(envelope.eventId().toString());

        try {
            template.convertAndSend(
                COURSE_EVENT_V1_EXCHANGE,
                COURSE_DELETED_ROUTING_KEY,
                envelope.payload(),
                msg -> {
                    MessageProperties mp = msg.getMessageProperties();
                    mp.setMessageId(envelope.eventId().toString());
                    mp.setTimestamp(Date.from(envelope.occurredAt()));
                    mp.setType(envelope.type());
                    mp.setContentType(MessageProperties.CONTENT_TYPE_JSON);
                    mp.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    mp.setHeader("version", envelope.version());
                    envelope.metadata().forEach((k, v) -> mp.setHeader("meta-" + k, v));
                    return msg;
                },
                correlation
            );
        } catch (AmqpException ex) {
            //
        }
    }
}
