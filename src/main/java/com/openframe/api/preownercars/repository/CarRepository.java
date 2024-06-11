package com.openframe.api.preownercars.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openframe.api.preownercars.model.CarList;


public interface CarRepository extends JpaRepository<CarList, UUID> {
	
	List<CarList> findByBrand(String brand);

}
