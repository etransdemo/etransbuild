package com.epodSystem.service;

import com.epodSystem.util.BussinessException;

public interface Validation {

	boolean checkDriverAvailability(String driverContact) throws BussinessException;

	boolean checkVehicleAvailability(String vehRegNo) throws BussinessException;

}
