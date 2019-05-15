package com.epodSystem.converter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Converter(autoApply = true)
public class JpaConverterJson implements AttributeConverter<JSONObject, HashMap<String, Object>> {

	 private final static ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public HashMap<String, Object> convertToDatabaseColumn(JSONObject attribute) {
		try {
			System.out.println("******************");
			System.out.println(attribute);
			if (null == attribute)
				return new HashMap<>();
			//return objectMapper.writeValueAsString(attribute);
			return attribute;
		} catch (Exception ex) {
			return null;
		}
	}

	@Override
	public JSONObject convertToEntityAttribute(HashMap<String, Object> dbData) {
		try {
			System.out.println("&&&&&&&&&&&&&&&&&&&");
			System.out.println(dbData);
			if (null == dbData)
				return null;
			//return objectMapper.readValue(dbData, Object.class);
			return (JSONObject) dbData;
		} catch (Exception ex) {
			return null;
		}
	}

	// @SuppressWarnings("unchecked")
	// @Override
	// public Map<String, Object> convertToDatabaseColumn(String attribute) {
	// if (attribute == null) {
	// return new HashMap<>();
	// }
	// try {
	// ObjectMapper objectMapper = new ObjectMapper();
	// return objectMapper.readValue(attribute, HashMap.class);
	// } catch (IOException e) {
	// System.out.println(e.getMessage());
	// }
	// return new HashMap<>();
	// }
	//
	// @Override
	// public String convertToEntityAttribute(Map<String, Object> dbData) {
	// try {
	// if (null == dbData) {
	// return null;
	// } else {
	// ObjectMapper objectMapper = new ObjectMapper();
	// return objectMapper.writeValueAsString(dbData);
	// }
	// } catch (JsonProcessingException e) {
	// System.out.println(e.getMessage());
	// ;
	// return null;
	// }
	// }

	// @SuppressWarnings("deprecation")
	// @Override
	// public String convertToDatabaseColumn(Object attribute) {
	// try {
	// if (null == attribute) {
	// return null;
	// } else {
	// ObjectMapper objectMapper = new ObjectMapper();
	// return new
	// ObjectMapper().getJsonFactory().createJsonParser(objectMapper.writeValueAsString(attribute));
	// }
	// } catch (IOException e) {
	// System.out.println(e.getMessage());
	// ;
	// return null;
	// }
	// }
	//
	// @SuppressWarnings("unchecked")
	// @Override
	// public Map<String, Object> convertToEntityAttribute(JsonParser dbData) {
	// if (dbData == null) {
	// return new HashMap<>();
	// }
	// try {
	// ObjectMapper objectMapper = new ObjectMapper();
	// return objectMapper.readValue(dbData, HashMap.class);
	// } catch (IOException e) {
	// System.out.println(e.getMessage());
	// }
	// return new HashMap<>();
	// }

}
