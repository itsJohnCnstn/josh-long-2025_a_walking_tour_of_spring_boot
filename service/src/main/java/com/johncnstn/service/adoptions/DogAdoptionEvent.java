package com.johncnstn.service.adoptions;

import org.springframework.modulith.events.Externalized;

import static com.johncnstn.service.adoptions.IntegrationConfiguration.ADOPTIONS_CHANNEL_NAME;

@Externalized(ADOPTIONS_CHANNEL_NAME)
public record DogAdoptionEvent(int dogId) {
}
