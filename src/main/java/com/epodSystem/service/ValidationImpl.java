package com.epodSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epodSystem.converter.JsonConverter;
import com.epodSystem.model.EpodDtls;
import com.epodSystem.repository.EpodRepository;
import com.epodSystem.util.BussinessException;
import com.epodSystem.util.Constants;

@Service
public class ValidationImpl implements Validation {

	@Autowired
	EpodRepository epodRepo;
	
	Constants constants;

	@Override
	public boolean checkDriverAvailability(String driverContact) throws BussinessException {
		boolean available = false;
		List<EpodDtls> driCount = epodRepo.checkDriverAvailability(driverContact);
		if (null == driCount || driCount.size() == 0)
			available = true;
		else
			throw new BussinessException(constants.DRIVER_UNAVAILABLE);
		return available;
	}

	@Override
	public boolean checkVehicleAvailability(String vehRegNo) throws BussinessException {
		boolean available = false;

		List<EpodDtls> vehCount = epodRepo.checkVehicleAvailability(vehRegNo);
		if (null == vehCount || vehCount.size() == 0)
			available = true;
		else
			throw new BussinessException(constants.VEHICLE_UNAVAILABLE);

		return available;
	}
}
