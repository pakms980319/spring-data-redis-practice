package com.example.demo.service;

import com.example.demo.config.JsonUtil;
import com.example.demo.dto.UserCacheDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueueService {

	private final StringRedisTemplate redis;
	private final JsonUtil jsonUtil;

	private static final String QUEUE_KEY = "queue:jobs";

	public Long enqueue(UserCacheDto dto) {
		return redis.opsForList().rightPush(QUEUE_KEY, jsonUtil.objectToString(dto));
	}

	public UserCacheDto dequeue() {
		// FIFO
		return jsonUtil.stringToObject(redis.opsForList().leftPop(QUEUE_KEY), UserCacheDto.class);
	}

	public Long size() {
		Long size = redis.opsForList().size(QUEUE_KEY);
		return size != null ? size : 0;
	}
}
