package com.epodSystem.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epodSystem.util.Constants;
import com.epodSystem.util.HttpURLConnectionClient;

@Service
public class CommonServiceImpl implements CommonService {

	@Autowired
	HttpURLConnectionClient msgClient;
	
	
	Constants constants;
	
	public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";
	public final static String SERVER_API_KEY = "AAAAnUv9fWw:APA91bHH-M1bs0MH-_fcPg8oHv2WEITEr6lzKtF3pM3ySGQyg1Ou-xWL9WdWxE75MDI5K_fuqjK1UmGdYmov8Y6CAjlQHlleQvJFaaE2iyB_vqzCV1L5aXQCMyLEx8FFkFiWIuNXAiPq";

	@Override
	@SuppressWarnings("unchecked")
	public int pushNotification(String fcmKey, String msg) throws IOException {
		URL url = new URL(API_URL_FCM);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		con.setDoOutput(true);
		con.setDoInput(true);
		con.setRequestMethod("POST");
		con.setRequestProperty("Authorization", "key=" + SERVER_API_KEY);
		con.setRequestProperty("Content-Type", "application/json");

		JSONObject data = new JSONObject();
		data.put("to", fcmKey.trim());
//		JSONObject info = new JSONObject();
//		info.put("body", msg);
//		data.put("data", info);
		
		JSONObject infopayload = new JSONObject();      
        infopayload.put("body", msg);  
        JSONObject infoData = new JSONObject();
        infoData.put("payload", infopayload);
        data.put("data", infoData);

		OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
		wr.write(data.toString());
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		System.out.println(responseCode);
		
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return responseCode;
	}

	public static void main(String[] args) throws IOException {
		CommonServiceImpl cs = new CommonServiceImpl();
		String msg = "ok";
		String fcmKey = "fKXZBimd4Sk:APA91bGUz3uakHTP2og5sYBrU78yrg8Kv_O7mHueLufT-4ZG3mADvq7XO_poWqQk76-aPEpCsxQynpVoIALXW6BHRWU3UMrd_kNj5137-TA7fexsG5xOQZ87X_WXcL_BaMwzIJkjlCKR";
		//cs.pushNotification(fcmKey, msg);
		cs.generateAPIKey();
	}
	
	/**
	 * SEND MESSAGE TO CUSTOMER
	 * 
	 * @param contactNo
	 * @return boolean
	 */
	@Override
	public boolean sendMessageToCustomer(String contactNo,String apiKey) {
		boolean sent = false;
		String msg = constants.TRACK_URL+apiKey;
		Object msgResp = msgClient.sendOTPMessage(contactNo, msg);
		return sent;
	}
	
	@Override
	public String generateAPIKey() {
		String key = UUID.randomUUID().toString();
		System.out.println(key.length());
		return key;
	}
	
	
}
