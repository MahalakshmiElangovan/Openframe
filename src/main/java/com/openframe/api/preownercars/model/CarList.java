package com.openframe.api.preownercars.model;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity(name = "car_list")
@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CarList {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String brand;
    private int year;
    private String name;
    private double price;
    private double kilometersDriven;
    private String imageUrl;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
    
    
	
    
    
}
