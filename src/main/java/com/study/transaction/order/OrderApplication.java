package com.study.transaction.order;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OrderApplication {
    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(OrderApplication.class).web(WebApplicationType.SERVLET).run(args);
    }

    /** 在Rabbitmq里面创建一个Queue */
    @Bean
    public Queue createRabbitmqQueue() {
        return new Queue("orderQueue");
    }

}
