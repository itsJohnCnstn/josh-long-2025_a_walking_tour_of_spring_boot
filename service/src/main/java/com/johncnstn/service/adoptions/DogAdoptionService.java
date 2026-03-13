package com.johncnstn.service.adoptions;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

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
