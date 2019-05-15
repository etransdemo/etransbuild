package com.epodSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epodSystem.model.Response;
import com.epodSystem.model.Vehicle;
import com.epodSystem.service.VehicleService;

@RestController
@RequestMapping(value = "/vehicle")
public class VehicleController {

	Response response;
	
	@Autowired
	VehicleService vehicleService;
	
	/**
	 * @return Response
	 */
	@GetMapping
	public Response fetchVehicleList() {
		response = new Response();
		try {
			List<Vehicle> vehList = vehicleService.findAllVehicle();
			if (null!=vehList) {
				response.setCode("0");
				response.setMsg("Success");
				response.setResult(vehList);
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
