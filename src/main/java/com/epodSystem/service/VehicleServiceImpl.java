package com.epodSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epodSystem.model.Vehicle;
import com.epodSystem.repository.VehicleRepository;

@Service
public class VehicleServiceImpl implements VehicleService{
	
	@Autowired
	VehicleRepository vehicleRepo;

	/**
	 * FETCH ALL VEHICLES
	 * 
	 * @return List<Vehicle>
	 */
	@Override
	public List<Vehicle> findAllVehicle() {
		List<Vehicle> vehList = null;
			 vehList = (List<Vehicle>) vehicleRepo.findAll();
		return vehList;
	}

}
