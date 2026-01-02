package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UniqueService {
	private final StringRedisTemplate redis;
	private static final String KEY = "set:unique";

	public boolean add(String value) {
		Long result = redis.opsForSet().add(KEY, value);
		return result != null && result == 1;
	}

	public boolean contains(String value) {
		Boolean member = redis.opsForSet().isMember(KEY, value);
		return Boolean.TRUE.equals(member);
	}

	public boolean delete(String value) {
		Long removed = redis.opsForSet().remove(KEY, value);
		return removed != null && removed > 0;
	}

	public long count() {
		Long size = redis.opsForSet().size(KEY);
		return size != null ? size : 0;
	}
}
