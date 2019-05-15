package com.epodSystem.repository;

import org.springframework.data.repository.CrudRepository;

import com.epodSystem.model.Vehicle;


public interface VehicleRepository extends CrudRepository<Vehicle, Integer>{

	boolean existsByvehReg(String vehReg);
	
	Vehicle findByvehReg(String vehReg);
}
