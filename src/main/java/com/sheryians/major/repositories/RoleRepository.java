package com.sheryians.major.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.sheryians.major.entities.Roles;

public interface RoleRepository extends JpaRepository<Roles, Integer>{
   Optional<Roles> findById(int id);
}
