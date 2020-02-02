package com.leovegas.wallet.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class ObjectMapperUtil {
	
	public static String stringifyObject(Object object) {		
		String value = "null";
		
		if (object != null) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				value = mapper.writeValueAsString(object);
			} catch (JsonProcessingException e) {
				value = "Unserializable object";
			};
		}
		
		return value;		
	}

}
