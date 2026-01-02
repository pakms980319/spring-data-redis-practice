package com.example.demo.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JsonUtil {
	private final ObjectMapper mapper;

	public <T> T stringToObject(String v, Class<T> clz) {
		if (v == null) {
			return null;
		}

		try {
			return mapper.readValue(v, clz);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public String objectToString(Object obj) {
		if (obj == null) {
			return null;
		}

		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
