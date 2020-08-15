package com.jeksvp.bpd.service;


import com.jeksvp.bpd.domain.entity.User;

/**
 * Represents service which create entity for new user
 */
public interface SignUpInitializer {

    /**
     * Determines if it should create entity for {@link User}
     *
     * @param user checked user
     * @return should it create new entity or not
     */
    boolean shouldCreate(User user);

    /**
     * Create new Entity for {@link User}
     */
    void createEntityFor(User user);
}
