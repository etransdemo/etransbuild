package com.epodSystem.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.epodSystem.converter.JsonConverter;
import com.epodSystem.model.EpodDtls;
import com.epodSystem.model.Response;
import com.epodSystem.service.EpodService;
import com.epodSystem.util.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/epod")
public class EpodController {

	@Autowired
	@Qualifier("epodServiceImpl")
	EpodService epodService;

	@Autowired
	@Qualifier("epodServiceImpl2")
	EpodService epodService2;

	Response response;

	Constants constants;

	/**
	 * SAVE SINGLE EPOD DATA
	 * 
	 * @param epodDtls
	 * @param httpServletRequest
	 * @return Response
	 */
	@PostMapping
	Response saveEpod(@RequestBody EpodDtls epodDtls, HttpServletRequest httpServletRequest) {
		response = new Response();
		try {
			System.out.println(JsonConverter.objToJson(epodDtls));
			response = epodService.saveEpod(epodDtls);
		} catch (Exception e) {
			response.setAdditionalMsg(e.getMessage());
		}
		return response;
	}

	/**
	 * SAVE EPOD DATA WITH DRIVER AND VEHICLE
	 * 
	 * @param epodDtls
	 * @param httpServletRequest
	 * @return Response
	 */
	@PostMapping(value = "/bulk")
	Response saveEpodList(@RequestBody List<EpodDtls> epodDtls, HttpServletRequest httpServletRequest) {
		response = new Response();
		// System.out.println(JsonConverter.objToJson(epodDtls));
		response = epodService.saveBulkEpod(epodDtls);
		return response;
	}

	/**
	 * SAVE EPOD DATA WITHOUT DRIVER AND VEHICLE
	 * 
	 * @param epodDtls
	 * @param httpServletRequest
	 * @return Response
	 */
	@PostMapping(value = "/excel")
	Response saveEpodExcelData(@RequestBody List<EpodDtls> epodDtls, HttpServletRequest httpServletRequest) {
		response = new Response();
		// System.out.println(JsonConverter.objToJson(epodDtls));
		response = epodService2.saveBulkEpod(epodDtls);
		return response;
	}

	/**
	 * @param epodId
	 * @return Response
	 */
	@GetMapping(value = "/{epodId}")
	public Response fetchEpodById(@PathVariable int epodId) {
		response = new Response();
		try {
			EpodDtls epodDtls = epodService.getEpodById(epodId);
			if (null != epodDtls) {
				response.setCode("0");
				response.setMsg("Success");
				response.setResult(epodDtls);
			} else {
				response.setCode("1");
				response.setMsg("epod not found");
			}
		} catch (Exception e) {
			response.setCode("1");
			response.setMsg("Something went wrong...");
			response.setAdditionalMsg(e.getMessage());
		}
		return response;
	}

	/**
	 * @return Response
	 */
	@GetMapping
	public Response fetchEpodList() {
		response = new Response();
		try {
			List<EpodDtls> epodDtlsList = epodService.epodList();
			if (null != epodDtlsList) {
				response.setCode("0");
				response.setMsg("Success");
				response.setResult(epodDtlsList);
			} else {
				response.setCode("1");
				response.setMsg("epod not found");
			}
		} catch (Exception e) {
			response.setCode("1");
			response.setMsg("Something went wrong...");
			response.setAdditionalMsg(e.getMessage());
		}
		return response;
	}

	/**
	 * @param files
	 * @param epodDtlsObj
	 * @return Response
	 */
	@PutMapping
	Response updateEpodForDoc(@RequestParam("files") MultipartFile[] files, @RequestParam(required = false) String remarks,
			@RequestParam String epodidSt, @RequestParam String indicator) {
		response = new Response();
		// ObjectMapper mapper = new ObjectMapper();
		boolean isUpdated = false;
		try {
			int epodid = Integer.parseInt(epodidSt);
			// EpodDtls epodDtls = mapper.readValue(epodDtlsObj, EpodDtls.class);
			if(indicator.equals(constants.UNLOADED_DOC))
			isUpdated = epodService.updateEpodForDoc(files, remarks, epodid);
			else if(indicator.equals(constants.LOADED_DOC))
				isUpdated = epodService2.updateEpodForDoc(files, remarks, epodid);
			if (isUpdated) {
				response.setCode("0");
				response.setMsg("Feedback Sent");
			} else {
				response.setCode("1");
				response.setMsg("Unable to send Feedback");
			}
		} catch (Exception e) {
			response.setCode("1");
			response.setMsg("Unable to send Feedback");
			response.setAdditionalMsg(e.getMessage());
		}
		return response;
	}

	@PutMapping(value = "/{epodId}")
	Response updateEpod(@RequestBody EpodDtls epodDtls, @PathVariable int epodId) {
		response = new Response();
		// ObjectMapper mapper = new ObjectMapper();
		boolean isUpdated = false;
		try {
			isUpdated = epodService.updateEpodDtls(epodDtls);
			if (isUpdated) {
				response.setCode("0");
				response.setMsg("Item Attached");
			} else {
				response.setCode("1");
				response.setMsg("Failed");
			}
		} catch (Exception e) {
			if (e.getMessage().equals(constants.DRIVER_UNAVAILABLE)
					|| e.getMessage().equals(constants.VEHICLE_UNAVAILABLE)) {
				response.setCode("2");
				response.setMsg(e.getMessage());
			} else {
				response.setCode("1");
				response.setMsg("Failed");
				response.setAdditionalMsg(e.getMessage());
			}
		}
		return response;
	}
	
	@PutMapping(value = "/ACK/{epodId}")
	Response updateEpodForACK( @PathVariable int epodId) {
		response = new Response();
		boolean isUpdated = false;
		try {
			isUpdated = epodService.updateEpodStatusForACK(epodId);
			if (isUpdated) {
				response.setCode("0");
				response.setMsg("ACKNOWLEDGED");
			} else {
				response.setCode("1");
				response.setMsg("Failed");
			}
		} catch (Exception e) {
				response.setCode("1");
				response.setMsg("Failed");
				response.setAdditionalMsg(e.getMessage());
		}
		return response;
	}

}
