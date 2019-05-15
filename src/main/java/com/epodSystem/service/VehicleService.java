package com.epodSystem.service;

import java.util.List;

import com.epodSystem.model.Vehicle;

public interface VehicleService {

	/**
	 * FETCH ALL VEHICLES
	 * 
	 * @return List<Vehicle>
	 */
	public List<Vehicle> findAllVehicle();
	
}
