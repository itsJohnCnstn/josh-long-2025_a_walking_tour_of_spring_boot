package com.johncnstn.integration;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.core.GenericHandler;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.json.JsonToObjectTransformer;

@SpringBootApplication
public class IntegrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntegrationApplication.class, args);
    }

    @Bean
    IntegrationFlow inboundIntegrationFlow(ConnectionFactory connectionFactory) {
        var amqp = Amqp.inboundAdapter(connectionFactory, "adoptions");
        return IntegrationFlow
                .from(amqp)
                .transform(new JsonToObjectTransformer(Dog.class))
                // after transform it became dog from byte[]
//                .handle((GenericHandler<byte[]>) (payload, headers) -> {
//                    System.out.println(new String(payload));
//                    headers.forEach((s, o) -> System.out.println(s + "=" + o));
//                    return null;
//                })
                .handle((GenericHandler<Dog>) (payload, headers) -> {
                    System.out.println(payload);
                    headers.forEach((s, o) -> System.out.println(s + "=" + o));
                    return null;
                })
                .get();
    }

}

record Dog(int dogId) {
}