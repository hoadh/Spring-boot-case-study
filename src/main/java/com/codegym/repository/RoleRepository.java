package com.codegym.repository;

import com.codegym.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Integer> {

    Role findByName(String name);

}