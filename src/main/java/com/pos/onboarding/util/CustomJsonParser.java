package com.pos.onboarding.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.pos.onboarding.ws.exception.ParseErrorException;

public class CustomJsonParser {


	public static String ObjectToJsonString(Object obj) throws ParseErrorException {
		ObjectMapper mapper = new ObjectMapper();
		String resultString = "";
		try {
			resultString = mapper.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			throw new ParseErrorException(obj, e.getStackTrace());
		} catch (JsonMappingException e) {
			throw new ParseErrorException(obj, e.getStackTrace());
		} catch (IOException e) {
			throw new ParseErrorException(obj, e.getStackTrace());
		}
		
		return resultString;
	}

	public static Map<String, Object> ObjectToJsonMap(Object obj) throws ParseErrorException {
		ObjectMapper mapper = new ObjectMapper();
		
		Map<String, Object> resultString = new HashMap<String, Object>();
		try {
			resultString = mapper.readValue(ObjectToJsonString(obj), HashMap.class);
		} catch (JsonParseException e) {
			throw new ParseErrorException(obj, e.getStackTrace());
		} catch (JsonMappingException e) {
			throw new ParseErrorException(obj, e.getStackTrace());
		} catch (IOException e) {
			throw new ParseErrorException(obj, e.getStackTrace());
		}
		
		return resultString;
	}
	
	public static <T> T JsonStringToObject(String jsonString, Class<T> clazz) throws ParseErrorException {
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			return clazz.cast(mapper.readValue(jsonString, clazz));
		} catch (JsonParseException e) {
			throw new ParseErrorException(jsonString, e.getStackTrace());
		} catch (JsonMappingException e) {
			throw new ParseErrorException(jsonString, e.getStackTrace());
		} catch (IOException e) {
			throw new ParseErrorException(jsonString, e.getStackTrace());
		}
	}
 }
