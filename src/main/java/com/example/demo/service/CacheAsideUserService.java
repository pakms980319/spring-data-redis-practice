package com.example.demo.service;

import com.example.demo.config.JsonUtil;
import com.example.demo.dto.UserCacheDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Cache-Aside
 * 1) Redis 조회
 * 2) 없으면(미스) DB 조회(fakeDB)
 * 3) Redis 에 넣고 반환
 */
@Service
@RequiredArgsConstructor
public class CacheAsideUserService {

	private final StringRedisTemplate redis;
	private final JsonUtil json;

	// 실제 DB 대신 테스트용 in-memory 저장소
	private final Map<Long, UserCacheDto> fakeDb = new ConcurrentHashMap<>();

	private static final Duration TTL = Duration.ofSeconds(30);

	private static String cacheKey(long userId) {
		return "cache:user:" + userId;
	}

	@PostConstruct
	void init() {
		fakeDb.put(1L, new UserCacheDto(1L, "alice", 20));
		fakeDb.put(2L, new UserCacheDto(2L, "bob", 25));
	}

	public UserCacheDto getUser(long userId) {
		String key = cacheKey(userId);

		// 1) cache hit
		String cached = redis.opsForValue().get(key);
		if (cached != null) {
			return json.stringToObject(cached, UserCacheDto.class);
		}

		// 2) cache miss -> DB 조회
		UserCacheDto fromDb = fakeDb.get(userId);
		if (fromDb == null) {
			return null;
		}

		// 3) cache write
		redis.opsForValue().set(key, json.objectToString(fromDb), TTL);
		return fromDb;
	}

	public UserCacheDto upsertDb(UserCacheDto dto) {
		fakeDb.put(dto.getId(), dto);
		return dto;
	}

	public void evictCache(long userId) {
		redis.delete(cacheKey(userId));
	}

	public long ttlSeconds(long userId) {
		Long seconds = redis.getExpire(cacheKey(userId));
		return seconds != null ? seconds : -2;
	}
}
