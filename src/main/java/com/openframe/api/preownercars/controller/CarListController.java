package com.openframe.api.preownercars.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.openframe.api.preownercars.model.CarList;
import com.openframe.api.preownercars.service.CarListService;

@Controller
public class CarListController {

	private final CarListService carListService;
	
	public CarListController(CarListService carListService) {
		this.carListService = carListService;
	}
	
	@QueryMapping
	List<CarList> getAllCars() {
		return carListService.getAllCars();
	}
	
	@QueryMapping
	List<CarList> getCarsByBrand(@Argument String brand) {
		return carListService.getCarsByBrand(brand);
	}
}
