package com.openframe.api.preownercars.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.openframe.api.preownercars.model.CarList;
import com.openframe.api.preownercars.repository.CarRepository;

@Service
public class CarListService {

	private final CarRepository carRepository;
	
	 Logger logger = LoggerFactory.getLogger(CarListService.class);

	public CarListService(CarRepository carRepository) {
		this.carRepository = carRepository;
	}

	public List<CarList> getAllCars() {
		logger.info("Getting all the cars");
		return carRepository.findAll();
	}

	public List<CarList> getCarsByBrand(String brand) {
		logger.info("Getting all the cars for the brand "+brand);
		return carRepository.findByBrand(brand);
	}

}
