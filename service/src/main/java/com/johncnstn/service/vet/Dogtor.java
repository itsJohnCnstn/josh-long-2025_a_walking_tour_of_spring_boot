package com.johncnstn.service.vet;

import com.johncnstn.service.adoptions.DogAdoptionEvent;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

//@Transactional it will still be a separate transaction
@Service
public class Dogtor {

    // @EventListener -> it won't be async
    // @Async -> it won't be in the same transaction
    @ApplicationModuleListener
    public void checkup(DogAdoptionEvent dogId) throws InterruptedException {
        Thread.sleep(5000);
        System.out.println("checking up on " + dogId);
    }
}
