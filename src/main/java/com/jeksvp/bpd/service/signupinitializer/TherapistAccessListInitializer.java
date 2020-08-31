package com.jeksvp.bpd.service.signupinitializer;

import com.jeksvp.bpd.domain.entity.Role;
import com.jeksvp.bpd.domain.entity.User;
import com.jeksvp.bpd.service.SignUpInitializer;
import com.jeksvp.bpd.service.TherapistService;
import org.springframework.stereotype.Component;

@Component
public class TherapistAccessListInitializer implements SignUpInitializer {

    private final TherapistService therapistService;

    public TherapistAccessListInitializer(TherapistService therapistService) {
        this.therapistService = therapistService;
    }

    @Override
    public boolean shouldCreate(User user) {
        return user.getRoles().contains(Role.THERAPIST);
    }

    @Override
    public void createEntityFor(User user) {
        therapistService.createAccessTherapistsList(user.getUsername());
    }
}
