package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TtlService {
	private final StringRedisTemplate redis;

	public void setWithTtl(String key, String value, Duration ttl) {
		redis.opsForValue().set(key, value, ttl);
	}

	public String get(String key) {
		return redis.opsForValue().get(key);
	}

	/**
	 * -2: 키 없음,
	 * -1: 만료 없음,
	 * 0+: 남은 TTL(초)
	 */
	public long remainingTtlSeconds(String key) {
		Long seconds = redis.getExpire(key);
		return seconds != null ? seconds : -2;
	}

	public void extendTtl(String key, Duration ttl) {
		redis.expire(key, ttl);
	}
}
