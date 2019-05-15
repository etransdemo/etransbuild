package com.epodSystem.service;

import java.io.IOException;

public interface CommonService {

	/**
	 * SENDING NOTIFICATION USING FCM
	 * 
	 * @param fcmKey
	 * @param msg
	 * @return int
	 * @throws IOException
	 */
	int pushNotification(String fcmKey, String msg) throws IOException;

	/**
	 * SEND MESSAGE TO CUSTOMER
	 * 
	 * @param contactNo
	 * @return boolean
	 */
	boolean sendMessageToCustomer(String contactNo,String apiKey);

	/**
	 * GENERATES API KEY FOR TRACKING
	 * 
	 * @return String
	 */
	String generateAPIKey();

}
