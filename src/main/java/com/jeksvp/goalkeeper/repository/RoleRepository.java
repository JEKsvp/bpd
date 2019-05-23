package com.jeksvp.goalkeeper.repository;

import com.jeksvp.goalkeeper.domain.entity.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByRoleName(String name);
}
