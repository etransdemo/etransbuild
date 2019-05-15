package com.epodSystem.model;

public class Response {

	private String code;
	private String msg;
	private Object result;
	private String additionalMsg;
	

	public String getAdditionalMsg() {
		return additionalMsg;
	}

	public void setAdditionalMsg(String additionalMsg) {
		this.additionalMsg = additionalMsg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
	
	

}
