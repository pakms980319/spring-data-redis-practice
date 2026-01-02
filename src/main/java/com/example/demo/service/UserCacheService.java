package com.example.demo.service;

import com.example.demo.config.JsonUtil;
import com.example.demo.dto.UserCacheDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class UserCacheService {
	private final StringRedisTemplate redis;
	private final JsonUtil jsonUtil;

	private static final String USER = "USER";

	private static String userKey(Long userId) {
		return "user:" + userId;
	}

	public Long putUser(UserCacheDto dto) {
		String key = userKey(dto.getId());

		redis.opsForHash().put(key, USER, jsonUtil.objectToString(dto));

		// Hash는 field 마다 TTL 이 아닌, key 단위 TTL 설정
		redis.expire(key, Duration.ofMinutes(1));
		return dto.getId();
	}

	public UserCacheDto getUser(Long userId) {
		String key = userKey(userId);
		Object raw = redis.opsForHash().get(key, USER);
		String json = raw != null ? raw.toString() : null;

		return jsonUtil.stringToObject(json, UserCacheDto.class);
	}

	public void deleteUser(long userId) {
		redis.delete(userKey(userId));
	}
}
