package com.repo.api.repository;

import com.repo.api.model.user.Roles;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RolesRepository extends CrudRepository<Roles, Long> {

    Optional<Roles> findByRole(String role);
}
