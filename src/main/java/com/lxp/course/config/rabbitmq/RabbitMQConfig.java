package com.lxp.course.config.rabbitmq;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.lxp.course.common.RabbitMQConstVal.COURSE_DELETE_DLQ;
import static com.lxp.course.common.RabbitMQConstVal.CART_COURSE_DELETE_QUEUE;
import static com.lxp.course.common.RabbitMQConstVal.COURSE_DELETE_RETRY;
import static com.lxp.course.common.RabbitMQConstVal.COURSE_DELETED_ROUTING_KEY;
import static com.lxp.course.common.RabbitMQConstVal.COURSE_DLX_EXCHANGE;
import static com.lxp.course.common.RabbitMQConstVal.COURSE_EVENT_V1_EXCHANGE;
import static com.lxp.course.common.RabbitMQConstVal.COURSE_RETRY_EXCHANGE;


@Configuration
public class RabbitMQConfig {
    public static final String X_QUEUE_TYPE = "x-queue-type";
    public static final String X_MESSAGE_TTL = "x-message-ttl";
    public static final String X_MAX_LENGTH = "x-max-length";
    public static final String X_DEAD_LETTER_EXCHANGE = "x-dead-letter-exchange";
    public static final String X_DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";
    public static final String QUORUM = "quorum";
    public static final int RETRY_MESSAGE_TTL = 30_000;
    public static final int MAX_LENGTH = 10_000;
    public static final int CONSUMER_COUNT = 3;
    public static final int MAX_CONSUMER_COUNT = 10;
    public static final int PREFETCH_COUNT = 10;

    @Bean
    public Queue cartCourseDeleteQueue() {
        return QueueBuilder.durable(CART_COURSE_DELETE_QUEUE)
            .withArgument(X_QUEUE_TYPE, QUORUM)
            .withArgument(X_MAX_LENGTH, MAX_LENGTH)
            .withArgument(X_DEAD_LETTER_EXCHANGE, COURSE_RETRY_EXCHANGE)
            .withArgument(X_DEAD_LETTER_ROUTING_KEY, COURSE_DELETE_RETRY)
            .build();
    }

    @Bean
    public Queue cartCourseDeleteRetryQueue() {
        return QueueBuilder.durable(COURSE_DELETE_RETRY)
            .withArgument(X_MESSAGE_TTL, RETRY_MESSAGE_TTL)
            .withArgument(X_DEAD_LETTER_EXCHANGE, COURSE_EVENT_V1_EXCHANGE)
            .withArgument(X_DEAD_LETTER_ROUTING_KEY, COURSE_DELETED_ROUTING_KEY)
            .build();
    }


    @Bean
    public Queue cartCourseDeleteDlq() {
        return QueueBuilder.durable(COURSE_DELETE_DLQ).build();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory cf) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(cf);
        factory.setConcurrentConsumers(CONSUMER_COUNT);
        factory.setMaxConcurrentConsumers(MAX_CONSUMER_COUNT);
        factory.setPrefetchCount(PREFETCH_COUNT);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

    @Bean
    public TopicExchange courseEventExchange() {
        return new TopicExchange(COURSE_EVENT_V1_EXCHANGE);
    }

    @Bean
    public TopicExchange courseDlxExchange() {
        return new TopicExchange(COURSE_DLX_EXCHANGE);
    }

    @Bean
    public TopicExchange courseRetryExchange() {
        return new TopicExchange(COURSE_RETRY_EXCHANGE);
    }

    @Bean
    public Binding cartCourseDeleteBinding(Queue cartCourseDeleteQueue, TopicExchange courseEventExchange) {
        return BindingBuilder
            .bind(cartCourseDeleteQueue)
            .to(courseEventExchange)
            .with(COURSE_DELETED_ROUTING_KEY);
    }

    @Bean
    public Binding cartCourseDeleteRetryBinding(Queue cartCourseDeleteRetryQueue, TopicExchange courseRetryExchange) {
        return BindingBuilder.bind(cartCourseDeleteRetryQueue)
            .to(courseRetryExchange)
            .with(COURSE_DELETE_RETRY);
    }


    @Bean
    public Binding cartCourseDeleteDlqBinding(Queue cartCourseDeleteDlq, TopicExchange courseDlxExchange) {
        return BindingBuilder
            .bind(cartCourseDeleteDlq)
            .to(courseDlxExchange)
            .with(COURSE_DELETE_DLQ);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
