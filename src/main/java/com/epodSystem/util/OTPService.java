package com.epodSystem.util;

import java.util.Random;
import java.util.concurrent.TimeUnit;



import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class OTPService {

	//cache based on username and OPT MAX 8 

	private static final Integer EXPIRE_MINS = 10;

	private static LoadingCache<String,String> otpCache = CacheBuilder.newBuilder().
			expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES).build(new CacheLoader<String, String>() {
				public String load(String key) {
					return null;
				}
			});
	

	/*public OTPService(){
		super();
		otpCache = CacheBuilder.newBuilder().
				expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES).build(new CacheLoader<String, String>() {
					public String load(String key) {
						return null;
					}
				});
	}*/

	public static String OTP(String key, int len) 
	{ 
		System.out.println("Generating OTP using random() : "); 
		System.out.print("You OTP is : "); 

		// Using numeric values 
		String numbers = "0123456789"; 

		// Using random method 
		Random rndm_method = new Random(); 

		char[] otp = new char[len]; 

		for (int i = 0; i < len; i++) 
		{ 
			// Use of charAt() method : to get character value 
			// Use of nextInt() as it is scanning the value as int 
			otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length())); 
		} 
		otpCache.put(key, String.valueOf(otp));
		return String.valueOf(otp); 
	} 

	public static int validateOtp(String username,String otpNum) {
		int res=0;
		if(otpNum == null || otpNum.equals(""))
			res=0;//otp is invalid
		else {
				String serverOtp = getOtp(username);
				if(serverOtp !=null && otpNum.equals(serverOtp)){
					clearOTP(username);
					res=1;//otp is valid
				}else {
					res=0;//otp is invalid
				}
			
	}
		return res;
	}

	//This method is used to return the OPT number against Key->Key values is username
	public static String getOtp(String key){ 
		try{
			return otpCache.get(key).toString(); 
		}catch (Exception e){
			return null; 
		}
	}


	//This method is used to clear the OTP catched already
	public static void clearOTP(String key){ 
		otpCache.invalidate(key);
	}


public static void main(String[] args) 
{ 
	int length = 6; 
	String key="Krishna";
	System.out.println(OTP(key,length));
	System.out.println(getOtp(key));
	System.out.println(validateOtp(key, getOtp(key)));
}

}
