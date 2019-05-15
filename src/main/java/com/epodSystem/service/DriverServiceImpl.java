package com.epodSystem.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epodSystem.converter.JsonConverter;
import com.epodSystem.model.Driver;
import com.epodSystem.model.EpodDtls;
import com.epodSystem.repository.DriverRepository;
import com.epodSystem.repository.EpodRepository;
import com.epodSystem.util.HttpURLConnectionClient;
import com.epodSystem.util.OTPService;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class DriverServiceImpl implements DriverSevice {

	@Autowired
	DriverRepository driRepo;
	@Autowired
	EpodRepository epodRepo;
	@Autowired
	HttpURLConnectionClient msgClient;

	private final Logger log = Logger.getLogger(DriverServiceImpl.class);

	/**
	 * SAVE DRIVER
	 * 
	 * @param driver
	 * @return boolean
	 */
	@Override
	public boolean saveDriver(Driver driver) {
		if (null != driver) {
			Driver existingDri = new Driver();
			existingDri = driRepo.findBydriverContact(driver.getDriverContact());
			try {
				System.out.println(JsonConverter.objToJson(existingDri));
				if (null != existingDri) {
					driver.setDriverId(existingDri.getDriverId());
					driRepo.save(driver);

				} else {
					driRepo.save(driver);

				}
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * VALIDATE DRIVER FOR TRIP
	 * 
	 * @param vehRegNo
	 * @return EpodDtls
	 */
	@Override
	public EpodDtls validateDriver(String vehRegNo) {
		Object resp = null;
		EpodDtls epodDetails = new EpodDtls();
		try {

			epodDetails = epodRepo.findByvehRegNo(vehRegNo);

			if (null != epodDetails) {
				resp = sendOTP("DRIVER", epodDetails.getDriverContact());
				if (!JsonConverter.objToJson(resp).contains("Success")) {
					epodDetails = null;
				}
			}

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return epodDetails;
	}

	/**
	 * GENERATE AND SEND OTP TO PASSED MOBILE NO.
	 * 
	 * @param userType
	 * @param mobNo
	 * @return Object
	 */
	@Override
	public Object sendOTP(String userType, String mobNo) {
		Object resp = null;
		try {
			String otp = OTPService.OTP(userType, 6);

			String msg = "your verification code is " + otp + " enter this code to verify your number";
			System.out.println(mobNo + "," + msg);
			resp = msgClient.sendOTPMessage(mobNo, msg);
		} catch (Exception ex) {
			log.info(ex.getMessage());
			ex.printStackTrace();
		}
		return resp;
	}
	

	/**
	 * FIND ALL DRIVER
	 * 
	 * @return List<Driver>
	 */
	@Override
	public List<Driver> fetchAllDriver(){
		List<Driver> driList = (List<Driver>) driRepo.findAll();
		return driList;
	}

}
