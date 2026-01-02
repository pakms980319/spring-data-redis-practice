package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import static com.example.demo.config.RedisConfig.*;

@Service
@RequiredArgsConstructor
public class RedisHelloService {

	private final StringRedisTemplate redisTemplate;

	public void setFoo(String value) {
		redisTemplate.opsForValue().set("foo", value);
	}

	public String getFoo() {
		return redisTemplate.opsForValue().get("foo");
	}

	@Cacheable(cacheNames = HELLO_CACHE, key = "#name")
	public String expensiveHello(String name) {
		try { Thread.sleep(500); } catch (InterruptedException ignored) {}
		return "Hello, " + name;
	}
}
