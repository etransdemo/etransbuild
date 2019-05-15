package com.epodSystem.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.epodSystem.converter.JsonConverter;



@Component
@PropertySource("file:/opt/etrans/app_properties/epod_app.properties")
public class HttpURLConnectionClient {
	
	@Value("${_BASE_URL}")
	public String _BASE_URL;
	
	@Value("${_CONTEXT_URL_4}")
	public String _CONTEXT_URL_4;
	
	@Value("${_GET_AUTH_KEY_URL}")
	public String _GET_AUTH_KEY_URL;
	
	private final Logger log = Logger.getLogger(HttpURLConnectionClient.class);

	public Object sendMessage(Map<String,Object> data) {
		Object resp = null;
		try {
			String SERVER_URI = _BASE_URL+"/messages";
			//String SERVER_URI = "http://192.168.10.108:9001/messages";
			System.out.println(SERVER_URI);
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Map<String,Object>> request = new HttpEntity<Map<String,Object>>(data, headers);
			resp = restTemplate.postForObject(SERVER_URI,request, Object.class);
			log.info(JsonConverter.objToJson(resp));
			return resp;
		} catch (Exception e) {
			return resp;
		}
	}
	
	public Object sendOTPMessage(String mobileNo, String message) {
		Object resp = null;
		try {
			System.out.println("sending message ...");
			String SERVER_URI = _BASE_URL+_CONTEXT_URL_4+"/sendMessage?";
			String rqstBody= "mobileNo=" +mobileNo + "&message=" + message;
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			HttpEntity<String> request = new HttpEntity<String>(rqstBody, headers);
			resp = restTemplate.postForObject(SERVER_URI,request, Object.class);
			System.out.println(JsonConverter.objToJson(resp));
			log.info(JsonConverter.objToJson(resp));
			return resp;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return resp;
		}
	}
	
	public String getAuthKeyForTracking(String username, String password) {
		Map<String,Object> resp = new HashMap<String,Object>();
		String jsession=null;
		try {
			
			//String SERVER_URI = _GET_AUTH_KEY_URL+"username=" +username + "&password=" + password;
			String SERVER_URI = "https://etranssolutions.com/eTransRestApi/reports/authenticate?username=" +username + "&password=" + password;
			System.out.println(SERVER_URI);
			RestTemplate restTemplate = new RestTemplate();
			 resp = (Map<String, Object>) restTemplate.getForObject(SERVER_URI, Object.class);
			System.out.println(JsonConverter.objToJson(resp));
			System.out.println(resp.get("result"));
			if(resp.get("result").equals(0)) {
				jsession = resp.get("jsession").toString();
			}
			
			return jsession;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return jsession;
		}
	}
	
	public Object getLocation(String sessionKey,JSONObject vehicle) {
		Object resp = null;
		try {
			System.out.println("sending message ...");
			String SERVER_URI = "https://etranssolutions.com/eTransRestApi/reports/getVehicleTrackInfo?jsession="+sessionKey;
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Map<String,Object>> request = new HttpEntity<Map<String,Object>>(vehicle, headers);
			resp = restTemplate.postForObject(SERVER_URI,request, Object.class);
			System.out.println(JsonConverter.objToJson(resp));
			return resp;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return resp;
		}
	}


	public static void main(String[] args) {
		HttpURLConnectionClient httpObj = new HttpURLConnectionClient();
//		httpObj.sendMessage("8240464840", "Hey");
		List<Map<String,Object>> recipientList = new ArrayList<Map<String,Object>>();
		Map<String,Object> data1 = new HashMap<String,Object>();
		data1.put("mailId", "varsha@etranssolutions.com");
		data1.put("mobile", 7890715835L);
		data1.put("usertype", "CORPORATE");
		
		recipientList.add(data1);
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("id", 10);
		data.put("event", "EPOD");
		data.put("status", "New");
		data.put("recipient",recipientList );
		System.out.println(data);
		//httpObj.sendMessage(data);
		
		//httpObj.getAuthKeyForTracking("EtransUser", "10hstc6Xa1ODTW9f40");
		JSONObject vehJson = new JSONObject();
		JSONArray jarray = new JSONArray();
		jarray.add("AP04X2589");
		vehJson.put("vehicles", jarray);
		httpObj.getLocation("b930df7e-bf09-4fca-bf2f-8d02c460f325", vehJson);
	}
}
