package com.epodSystem.service;

import java.util.List;

import com.epodSystem.model.Driver;
import com.epodSystem.model.EpodDtls;

public interface DriverSevice {

	/**
	 * SAVE DRIVER
	 * @param driver
	 * @return boolean
	 */
	boolean saveDriver(Driver driver);
	
	/**
	 * VALIDATE DRIVER FOR TRIP
	 * 
	 * @param vehRegNo
	 * @return EpodDtls
	 */
	EpodDtls validateDriver(String vehRegNo);

	/**
	 * GENERATE AND SEND OTP TO PASSED MOBILE NO.
	 * 
	 * @param userType
	 * @param mobNo
	 * @return Object
	 */
	Object sendOTP(String userType, String mobNo);

	/**
	 * FIND ALL DRIVER
	 * 
	 * @return List<Driver>
	 */
	List<Driver> fetchAllDriver();
}
