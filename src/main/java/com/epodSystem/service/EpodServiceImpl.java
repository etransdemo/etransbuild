package com.epodSystem.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.epodSystem.converter.JsonConverter;
import com.epodSystem.model.Driver;
import com.epodSystem.model.EpodDtls;
import com.epodSystem.model.Response;
import com.epodSystem.model.Vehicle;
import com.epodSystem.repository.DriverRepository;
import com.epodSystem.repository.EpodRepository;
import com.epodSystem.repository.VehicleRepository;
import com.epodSystem.util.BussinessException;
import com.epodSystem.util.Constants;
import com.epodSystem.util.HttpURLConnectionClient;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
@Qualifier("epodServiceImpl")
public class EpodServiceImpl implements EpodService {
	Response response;

	@Autowired
	VehicleRepository vehRepo;
	@Autowired
	DriverRepository driRepo;

	@Autowired
	EpodRepository repository;
	@Autowired
	UploadDocServiceImpl uploadDocService;
	@Autowired
	CommonService commonService;
	@Autowired
	HttpURLConnectionClient msgClient;
	@Autowired
	Validation validation;

	Constants constants;

	private final Logger log = Logger.getLogger(EpodServiceImpl.class);

	/**
	 * Create ePOD
	 * 
	 * @param epodDtls
	 * @return boolean
	 */
	@Override
	@Transactional
	public Response saveEpod(EpodDtls epodDtls) {
		response = new Response();
		try {
			String fcmKey = null;
			Object msgResp = null;

			if (null != epodDtls) {
				Vehicle existingVeh = vehRepo.findByvehReg(epodDtls.getVehRegNo());
				Driver existingDri = driRepo.findBydriverContact(epodDtls.getDriverContact());
				if (null == existingVeh) {
					Vehicle vehicle = new Vehicle();
					vehicle.setVehReg(epodDtls.getVehRegNo());
					vehicle.setCreatedBy(epodDtls.getS_create_by());
					vehRepo.save(vehicle);
					epodDtls.setVehId(vehicle.getVehId());
				} else if (null != existingVeh) {
					epodDtls.setVehId(existingVeh.getVehId());
				}

				if (null == existingDri) {
					Driver driver = new Driver();
					driver.setDriverContact(epodDtls.getDriverContact());
					// driver.setDriverName(epodDtls.getS_epod_dtls().get("driverName").toString());
					driRepo.save(driver);
					epodDtls.setDriId(driver.getDriverId());
				} else if (null != existingDri) {
					epodDtls.setDriId(existingDri.getDriverId());
					fcmKey = existingDri.getFcmCode();

				}
				repository.save(epodDtls);
				String msg = constants.TRIP_ASSIGNED;
				System.out.println(epodDtls.getDriverContact());
				msgResp = msgClient.sendOTPMessage(epodDtls.getDriverContact(), msg);
				System.out.println(msgResp);

				if (null != fcmKey)
					commonService.pushNotification(fcmKey, msg);

				response.setCode("0");
				response.setMsg("ePOD created");
			}
		} catch (Exception e) {
			response.setCode("1");
			response.setMsg("Unable to create ePOD");
			response.setAdditionalMsg(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * CREATE EPOD IN BULK
	 * 
	 * @param epodDtls
	 * @return
	 */
	@Override
	@Transactional
	public Response saveBulkEpod(List<EpodDtls> epodDtls) {
		response = new Response();
		List<Vehicle> vehList = new ArrayList<>();
		List<Driver> driList = new ArrayList<>();
		try {
			if (null != epodDtls) {
				for (int i = 0; i < epodDtls.size(); i++) {
					Vehicle existingVeh = vehRepo.findByvehReg(epodDtls.get(i).getVehRegNo());
					Driver existingDri = driRepo.findBydriverContact(epodDtls.get(i).getDriverContact());
					if (null == existingVeh) {
						Vehicle vehicle = new Vehicle();
						vehicle.setVehReg(epodDtls.get(i).getVehRegNo());
						vehicle.setCreatedBy(epodDtls.get(i).getS_create_by());
						if (vehList.size() == 0) {
							vehList.add(vehicle);
						} else if (vehList.size() != 0) {
							for (int vlen = 0; vlen < vehList.size(); vlen++) {
								if (!vehList.get(vlen).getVehReg().contains(vehicle.getVehReg())) {
									vehList.add(vehicle);
								}
							}
						}
						epodDtls.get(i).setVehId(vehicle.getVehId());
					} else if (null != existingVeh) {
						epodDtls.get(i).setVehId(existingVeh.getVehId());
					}
					if (null == existingDri) {
						Driver driver = new Driver();
						driver.setDriverContact(epodDtls.get(i).getDriverContact());
						driver.setDriverName(epodDtls.get(i).getS_epod_dtls().get("driverName").toString());
						if (driList.size() == 0) {
							driList.add(driver);
						} else if (driList.size() != 0) {
							for (int dlen = 0; dlen < driList.size(); dlen++) {
								if (!driList.get(dlen).getDriverContact().contains(driver.getDriverContact())) {
									driList.add(driver);
								}
							}
						}
						epodDtls.get(i).setDriId(driver.getDriverId());
					} else if (null != existingDri) {
						epodDtls.get(i).setDriId(existingDri.getDriverId());
					}
				}
				vehRepo.save(vehList);
				driRepo.save(driList);
				repository.save(epodDtls);
				response.setCode("0");
				response.setMsg("ePOD created");

			} else {
				response.setCode("1");
				response.setMsg("Unable to create ePOD");
			}
		} catch (Exception e) {
			response.setCode("1");
			response.setMsg("Unable to create ePOD");
			response.setAdditionalMsg(e.getMessage());
		}
		return response;
	}

	/**
	 * UPDATE EPOD
	 * 
	 * @param epodDtls
	 * @return EpodDtls
	 */
	@Override
	public EpodDtls updateEpod(EpodDtls epodDtls) {
		epodDtls.setStatus(constants.OPEN);
		epodDtls.setDt_update_on(LocalDateTime.now());
		String apiKey = commonService.generateAPIKey();
		epodDtls.setApiKey(apiKey);
		if (null != epodDtls) {
			repository.save(epodDtls);
			// commonService.sendMessageToCustomer(epodDtls.getS_epod_dtls().get("customerContact").toString(),
			// apiKey);
			commonService.sendMessageToCustomer("7890715835", apiKey);

		}
		return epodDtls;
	}

	/**
	 * UPDATE EPOD STATUS AFTER ACKNOWLEDGMENT
	 * 
	 * @param epodId
	 * @return Boolean
	 */
	@Override
	public Boolean updateEpodStatusForACK(int epodId) {
		String status = constants.ACKNOWLEDGED;
		int count = repository.setStatusForEpodDtls(status, epodId);
		if (count > 0)
			return true;
		else
			return false;
	}

	/**
	 * FETCH SINGLE EPOD DATA
	 * 
	 * @param epodId
	 * @return EpodDtls
	 */
	@Override
	public EpodDtls getEpodById(int epodId) {
		EpodDtls epodData = repository.findOne(epodId);
		return epodData;
	}

	/**
	 * UPDATE EPOD TABLE FOR FEEDBACK AND DOCUMENT UPLOAD
	 * 
	 * @param files
	 * @param epod
	 * @return boolean
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean updateEpodForDoc(MultipartFile[] files, String remarks, int epodId) {
		boolean updated = false;
		String filePath = "";
		JSONObject feedBackJson = new JSONObject();
		EpodDtls epod = new EpodDtls();
		JSONArray jArray = new JSONArray();
		if (null != epod && null != files && files.length > 0) {
			for (MultipartFile file : files) {
				log.info(file.getOriginalFilename());
				filePath = uploadDocService.storeFile(file);
				log.info("filePath : " + filePath);
				if (null != filePath && !filePath.equals("")) {
					JSONObject custDoc = new JSONObject();
					custDoc.put("docName", file.getOriginalFilename());
					custDoc.put("docPath", filePath);
					jArray.add(custDoc);
				}
			}

			log.info(jArray.size());
			if (null != jArray && jArray.size() > 0) {
				feedBackJson.put("DOC", jArray);
				feedBackJson.put("REM", remarks);
				String status = "RCV";
				repository.sets_feedbackForEpodDtls(feedBackJson, status, epodId);
				updated = true;
			}

		}
		return updated;
	}

	/**
	 * FETCH ALL EPOD
	 * 
	 * @return List<EpodDtls>
	 */
	@Override
	public List<EpodDtls> epodList() {
		List<EpodDtls> epodlist = (List<EpodDtls>) repository.findAll();
		return epodlist;
	}

	@Override
	public boolean updateEpodDtls(EpodDtls epodDtls) throws BussinessException {
		boolean updated = false;
		Vehicle existingVeh = vehRepo.findByvehReg(epodDtls.getVehRegNo());
		Driver existingDri = null;
		if (null != epodDtls.getVehRegNo()) {
			if (null == existingVeh) {
				Vehicle vehicle = new Vehicle();
				vehicle.setVehReg(epodDtls.getVehRegNo());
				vehicle.setCreatedBy(epodDtls.getS_create_by());
				vehRepo.save(vehicle);
				epodDtls.setVehId(vehicle.getVehId());
			} else if (null != existingVeh) {

				validation.checkVehicleAvailability(epodDtls.getVehRegNo());

				epodDtls.setVehId(existingVeh.getVehId());
			}
		}
		if (null != epodDtls.getDriver()) {
			existingDri = driRepo.findBydriverContact(epodDtls.getDriverContact());
			if (null == existingDri) {
				Driver driver = new Driver();
				driver.setDriverContact(epodDtls.getDriverContact());
				driver.setDriverName(
						epodDtls.getDriver().getDriverName() == null ? null : epodDtls.getDriver().getDriverName());
				driver.setLicenseNo(
						epodDtls.getDriver().getLicenseNo() == null ? null : epodDtls.getDriver().getLicenseNo());
				driver.setLicenseValidity(epodDtls.getDriver().getLicenseValidity() == null ? null
						: epodDtls.getDriver().getLicenseValidity());

				driRepo.save(driver);
				epodDtls.setDriId(driver.getDriverId());
			} else if (null != existingDri) {

				validation.checkDriverAvailability(epodDtls.getDriverContact());

				Driver driver = new Driver();
				driver.setDriverContact(epodDtls.getDriverContact());
				driver.setDriverName(
						epodDtls.getDriver().getDriverName() == null ? null : epodDtls.getDriver().getDriverName());
				driver.setLicenseNo(
						epodDtls.getDriver().getLicenseNo() == null ? null : epodDtls.getDriver().getLicenseNo());
				driver.setLicenseValidity(epodDtls.getDriver().getLicenseValidity() == null ? null
						: epodDtls.getDriver().getLicenseValidity());
				driver.setDriverId(existingDri.getDriverId());
				driRepo.save(driver);
				epodDtls.setDriId(existingDri.getDriverId());

			}
		}
		int count = repository.updateEpod(epodDtls.getVehRegNo(), epodDtls.getVehId(), epodDtls.getDriverContact(),
				epodDtls.getDriId(), epodDtls.getI_epod_id());

		if (count > 0)
			updated = true;

		return updated;
	}

	/**
	 * FIND VEHICLE AGAINST API KEY AND GET CURRENT LOCATION
	 * 
	 * @param apiKey
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> findVehicleByApiKey(String apiKey) {
		EpodDtls epodDtls = repository.findByApiKey(apiKey);
		List<Map<String, Object>> trackInfo = new ArrayList<Map<String, Object>>();
		String vehicle = epodDtls.getVehRegNo();
		if (null != vehicle) {
			String jsession = msgClient.getAuthKeyForTracking(constants.TRACK_USERNAME, constants.TRACK_PASSWORD);
			if (null != jsession) {
				JSONObject vehJson = new JSONObject();
				JSONArray jarray = new JSONArray();
				jarray.add("AP04X2589");
				vehJson.put("vehicles", jarray);
				Map<String, Object> response = (Map<String, Object>) msgClient.getLocation(jsession, vehJson);
				if (response.get("result").equals(0)) {
					trackInfo = (List<Map<String, Object>>) response.get("trackinfo");
				}
			}
		}
		if (trackInfo.size() > 0)
			return trackInfo.get(0);
		else
			return null;
	}
}
