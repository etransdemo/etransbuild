package com.epodSystem.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.epodSystem.model.Driver;
import com.epodSystem.model.Response;
import com.epodSystem.util.BussinessException;
import com.epodSystem.model.EpodDtls;


public interface EpodService {

	
	/**
	 * Create ePOD
	 * @param epodDtls
	 * @return boolean
	 */
	public Response saveEpod(EpodDtls epodDtls);

	/**
	 * CREATE EPOD IN BULK
	 * @param epodDtls
	 * @return
	 */
	Response saveBulkEpod(List<EpodDtls> epodDtls);

	/**
	 * UPDATE EPOD 
	 * @param epodDtls
	 * @return EpodDtls
	 */


	EpodDtls updateEpod(EpodDtls epodDtls);

	/**
	 * FETCH SINGLE EPOD DATA
	 * 
	 * @param epodId
	 * @return EpodDtls
	 */
	EpodDtls getEpodById(int epodId);

	/**
	 * UPDATE EPOD TABLE FOR CUSTOMER FEEDBACK AND DOCUMENT UPLOAD
	 * 
	 * @param files
	 * @param epod
	 * @return boolean
	 */
	//boolean updateEpodForCust(MultipartFile[] files, EpodDtls epod);

	/**
	 * FETCH ALL EPOD
	 * 
	 * @return List<EpodDtls>
	 */
	List<EpodDtls> epodList();

	/**
	 * UPDATE EPOD TABLE FOR  FEEDBACK AND DOCUMENT UPLOAD
	 * @param files
	 * @param remarks
	 * @param epodId
	 * @return boolean
	 */
	boolean updateEpodForDoc(MultipartFile[] files, String remarks, int epodId);

	/**
	 * UPDATE EPOD FOR ADDING VEHICLE
	 * 
	 * @param epodDtls
	 * @return boolean
	 * @throws BussinessException 
	 */
	boolean updateEpodDtls(EpodDtls epodDtls) throws BussinessException;

	/**
	 * UPDATE EPOD STATUS AFTER ACKNOWLEDGMENT
	 * 
	 * @param epodId
	 * @return Boolean
	 */
	Boolean updateEpodStatusForACK(int epodId);

	/**
	 * FIND VEHICLE AGAINST API KEY AND GET CURRENT LOCATION
	 * 
	 * @param apiKey
	 * @return String
	 */
	Map<String, Object> findVehicleByApiKey(String apiKey);
	

	
}
