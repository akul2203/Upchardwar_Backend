package com.upchardwar.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upchardwar.app.entity.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {

}
