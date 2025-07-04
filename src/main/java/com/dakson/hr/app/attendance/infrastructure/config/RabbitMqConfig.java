package com.dakson.hr.app.attendance.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

  @Value("${fingerprint.exchange}")
  private String exchange;

  @Value("${fingerprint.queue}")
  private String queue;

  @Value("${fingerprint.routing-key}")
  private String routingKey;

  @Bean
  public TopicExchange topicExchange() {
    return new TopicExchange(exchange);
  }

  @Bean
  public Queue attendanceQueue() {
    return new Queue(queue);
  }

  @Bean
  public Binding binding() {
    return BindingBuilder.bind(attendanceQueue())
      .to(topicExchange())
      .with(routingKey);
  }

  @Bean
  public Jackson2JsonMessageConverter messageConverter() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    return new Jackson2JsonMessageConverter(mapper);
  }
}
