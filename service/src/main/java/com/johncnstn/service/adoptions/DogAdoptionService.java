package com.johncnstn.service.adoptions;

import com.google.protobuf.Empty;
import com.johncnstn.service.adoptions.grpc.AdoptionsGrpc;
import com.johncnstn.service.adoptions.grpc.DogsResponse;
import io.grpc.stub.StreamObserver;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.DirectChannelSpec;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Collection;

@Service
class DogAdoptionsGrpcService extends AdoptionsGrpc.AdoptionsImplBase {

    private final DogAdoptionService dogAdoptionService;

    DogAdoptionsGrpcService(DogAdoptionService dogAdoptionService) {
        this.dogAdoptionService = dogAdoptionService;
    }

    // grpcurl --plaintext localhost:8080 Adoptions.All
    @Override
    public void all(Empty request, StreamObserver<DogsResponse> responseObserver) {
        var all = dogAdoptionService.dogs().stream().map(ogDog -> com.johncnstn.service.adoptions.grpc.Dog.newBuilder()
                        .setId(ogDog.id())
                        .setName(ogDog.name())
                        .setDescription(ogDog.description())
                        .build())
                .toList();

        var reply = DogsResponse.newBuilder().addAllDogs(all).build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}

@Controller
class DogAdoptionsGraphqlController {

    private final DogAdoptionService dogAdoptionService;

    DogAdoptionsGraphqlController(DogAdoptionService dogAdoptionService) {
        this.dogAdoptionService = dogAdoptionService;
    }

    // http://localhost:8080/graphiql?path=/graphql
    @QueryMapping
    Collection<Dog> dogs() {
        return dogAdoptionService.dogs();
    }
}

@Configuration
class IntegrationConfiguration {

    static final String ADOPTIONS_CHANNEL_NAME = "outboundAdoptionMessageChannel";

    static final String ADOPTIONS_NAME = "adoptions";

    @Bean
    Queue adoptionsQueue() {
        return QueueBuilder.durable(ADOPTIONS_NAME).build();
    }

    @Bean
    Exchange adoptionsExchange() {
        return ExchangeBuilder.directExchange(ADOPTIONS_NAME).build();
    }

    @Bean
    Binding adoptionsBinding(Queue adoptionsQueue, Exchange adoptionsExchange) {
        return BindingBuilder
                .bind(adoptionsQueue)
                .to(adoptionsExchange)
                .with(ADOPTIONS_NAME)
                .noargs();
    }

    @Bean(ADOPTIONS_CHANNEL_NAME)
    MessageChannelSpec<DirectChannelSpec, DirectChannel> outboundAdoptionMessageChannel() {
        return MessageChannels.direct();
    }

    @Bean
    IntegrationFlow outboundAdoptionsIntegrationFlow(AmqpTemplate amqpTemplate,
                                                     @Qualifier(ADOPTIONS_CHANNEL_NAME) MessageChannel messageChannel) {
        return IntegrationFlow
                .from(messageChannel)
                .handle(Amqp.outboundAdapter(amqpTemplate)
                        .routingKey(ADOPTIONS_NAME)
                        .exchangeName(ADOPTIONS_NAME)
                )
                .get();
    }
}

@Controller
@ResponseBody
class DogAdoptionHttpController {

    private final DogAdoptionService dogAdoptionService;

    DogAdoptionHttpController(DogAdoptionService dogAdoptionService) {
        this.dogAdoptionService = dogAdoptionService;
    }

    @GetMapping("/dogs")
    Collection<Dog> dogs() {
        return dogAdoptionService.dogs();
    }

    @PostMapping("/dogs/{dogId}/adoptions")
    void adopt(@PathVariable int dogId, @RequestParam String owner) {
        dogAdoptionService.adopt(dogId, owner);
    }

}

//app.controllers.CustomerController
//app.services.CustomerService
//app.repositories.CustomerRepository
//app.models.Customer

//app.customers.* {Customer,...}

@Service
@Transactional
class DogAdoptionService {

    private final DogRepository dogRepository;

    private final ApplicationEventPublisher eventPublisher;

    DogAdoptionService(DogRepository dogRepository, ApplicationEventPublisher eventPublisher) {
        this.dogRepository = dogRepository;
        this.eventPublisher = eventPublisher;
    }

    void adopt(int id, String owner) {
        dogRepository.findById(id).ifPresent(dog -> {
            var updated = dogRepository.save(new Dog(id, dog.name(), owner, dog.description()));
            eventPublisher.publishEvent(new DogAdoptionEvent(id));
            System.out.println("updated: [" + updated + "]");
        });
    }

    Collection<Dog> dogs() {
        return dogRepository.findAll();
    }

    String assistant() {
        return null; // todo
    }

}

record Dog(@Id int id, String name, String owner, String description) {
}

interface DogRepository extends ListCrudRepository<Dog, Integer> {
}

@Controller
@ResponseBody
class MeController {
    @GetMapping("/me")
    String me(Principal principal) {
        return principal.getName();
    }
}
