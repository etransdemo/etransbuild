package com.epodSystem.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epodSystem.model.EpodDtls;
import com.epodSystem.model.Response;
import com.epodSystem.service.EpodService;

@Controller
@RequestMapping("track")
public class TripTrackController {
	
	@Autowired
	@Qualifier("epodServiceImpl")
	EpodService epodService;
	Map<String, String> map = new HashMap<String, String>();
	Response response;
	
	@RequestMapping(value="/{apiKey}",method = RequestMethod.GET)
	public String getDefault(@PathVariable String apiKey) {
		System.out.println("root url");
		System.out.println(apiKey);
		map.put("apiKey", apiKey);
		return "locationTracking";
	}
	
	
	/*@RequestMapping(method = RequestMethod.GET)
	public String trackTrip(@RequestParam String apiKey,Model model) {
		Map<String,Object> trackInfo=epodService.findVehicleByApiKey(apiKey);
		model.addAllAttributes(trackInfo);
		model.addAttribute("lat", trackInfo.get("latitude"));
		model.addAttribute("lng",trackInfo.get("longitude"));
		return "locationTracking";
	}*/
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Response trackTrip() {
		response = new Response();
		String apiKey= map.get("apiKey");
		System.out.println(apiKey);
		//EpodDtls epod = epodService.
		Map<String,Object> trackInfo=epodService.findVehicleByApiKey(apiKey);
		
		response.setResult(trackInfo);
		
		return response;
	}

}
