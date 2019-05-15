package com.epodSystem.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epodSystem.converter.JsonConverter;
import com.epodSystem.model.Driver;
import com.epodSystem.model.EpodDtls;
import com.epodSystem.model.Response;
import com.epodSystem.service.CommonService;
import com.epodSystem.service.DriverSevice;
import com.epodSystem.service.EpodService;
import com.epodSystem.util.Constants;
import com.epodSystem.util.HttpURLConnectionClient;
import com.epodSystem.util.OTPService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@RestController
@RequestMapping(value = "/driver")
public class DriverController {

	Response response;

	@Autowired
	HttpURLConnectionClient msgClient;

	@Autowired
	DriverSevice driverService;
	
	@Autowired
	@Qualifier("epodServiceImpl")
	EpodService epodService;
	
	Constants constants;
	
	@Autowired
	CommonService commonService;

	private static final Integer EXPIRE_MINS = 10;

	private static LoadingCache<String, Object> driverCache = CacheBuilder.newBuilder()
			.expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES).build(new CacheLoader<String, Object>() {
				public String load(String key) {
					return null;
				}
			});

	@PostMapping(value = "/login")
	Response sendOTP(@RequestBody Map<String, String> driver, HttpServletRequest httpServletRequest) {
		response = new Response();
		Object resp = null;
		try {
			// System.out.println(JsonConverter.objToJson(epodDtls));
			if (null != driver) {

				String mobNo = driver.get("driverContact");
				resp = driverService.sendOTP("DRIVER", mobNo);
				System.out.println(JsonConverter.objToJson(resp));

				if (JsonConverter.objToJson(resp).contains("Success")) {
					response.setCode("0");
					response.setMsg("OTP sent");
					response.setResult(mobNo);
					driverCache.putAll(driver);
				} else {
					response.setCode("1");
					response.setMsg("Error in sending OTP");
				}
			}
		} catch (Exception e) {
			response.setCode("1");
			response.setMsg("Error in sending OTP");
			response.setAdditionalMsg(e.getMessage());
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/validateOTP")
	public Response validateOTP(HttpServletRequest httpServletRequest, @RequestBody Map<String, Object> otpDetails) {

		response = new Response();
		try {
			final ObjectMapper mapper = new ObjectMapper();
			String username = otpDetails.get("username").toString();
			String otpNum = otpDetails.get("otpNum").toString();
			String role = otpDetails.get("role").toString();
			System.out.println(username + "," + otpNum);
			int res = OTPService.validateOtp(username, otpNum);
			System.out.println(res);
			if (res == 1 && role.equals(constants.SAVE_DRIVER)) {
				List<String> driKeys = new ArrayList<String>();
				driKeys.add("driverContact");
				driKeys.add("fcmCode");
				System.out.println(JsonConverter.objToJson(driKeys));
				Map<String, Object> driverData = driverCache.getAll(driKeys);
				System.out.println(JsonConverter.objToJson(driverData));
				
				Driver driver = mapper.convertValue(driverData, Driver.class);
				driverService.saveDriver(driver);
				
				JSONObject jobj=new JSONObject();
				jobj.put("SIG", false);
				jobj.put("REM", true);
				jobj.put("CHK", false);
				jobj.put("DOC", true);
				
				response.setCode("0");
				response.setMsg("Success");
				response.setResult(driver.getDriverContact());
				response.setAdditionalMsg(jobj.toString());
			} else if (res == 1 && role.equals(constants.FETCH_TRIP)) {
				
				EpodDtls updtEpodData = mapper.convertValue(driverCache.get("epodData"), EpodDtls.class);
				EpodDtls epod = epodService.updateEpod(updtEpodData);
				//commonService.sendMessageToCustomer("");
				response.setCode("0");
				response.setMsg("Success");
				response.setResult(epod);

			} else {
				response.setCode("1");
				response.setMsg("Something went wrong...");
			}
		} catch (Exception e) {
			response.setCode("1");
			response.setMsg("Something went wrong...");
			response.setAdditionalMsg(e.getMessage());
		}
		return response;
	}

	@GetMapping(value = "/{vehRegNo}")
	public Response validateDriver(@PathVariable String vehRegNo) {
		response = new Response();
		try {
			EpodDtls epodDtls = driverService.validateDriver(vehRegNo);
			if (null!=epodDtls) {
				response.setCode("0");
				response.setMsg("OTP sent");
				response.setResult(epodDtls.getDriverContact());
				driverCache.put("epodData", epodDtls);
				driverCache.put("epodId", epodDtls.getI_epod_id());
			} else {
				response.setCode("1");
				response.setMsg("Error in sending OTP");
			}
		} catch (Exception e) {
			response.setCode("1");
			response.setMsg("Something went wrong...");
			response.setAdditionalMsg(e.getMessage());
		}
		return response;
	}

	@GetMapping
	public Response fetchDriverList() {
		response = new Response();
		try {
			List<Driver> driList = driverService.fetchAllDriver();
			if (null!=driList) {
				response.setCode("0");
				response.setMsg("Success");
				response.setResult(driList);
			} else {
				response.setCode("1");
				response.setMsg("Vehicle not found");
			}
		} catch (Exception e) {
			response.setCode("1");
			response.setMsg("Something went wrong...");
			response.setAdditionalMsg(e.getMessage());
		}
		return response;
	}
	
}
