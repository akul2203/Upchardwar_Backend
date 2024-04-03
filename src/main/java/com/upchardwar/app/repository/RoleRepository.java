package com.upchardwar.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upchardwar.app.entity.Role;


public interface RoleRepository extends JpaRepository<Role, Long> {

}
