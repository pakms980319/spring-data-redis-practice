package com.example.demo.service;

import com.example.demo.dto.PipelineSetRequest;
import com.example.demo.dto.PipelineSetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PipelineService {

	private final StringRedisTemplate redis;

	public PipelineSetResponse setMany(PipelineSetRequest req) {
		long start = System.currentTimeMillis();

		redis.executePipelined((RedisCallback<?>) connection -> {
			for (int i = 0; i < req.getCount(); i++) {
				String key = req.getPrefix() + ":" + i;
				String value = "value-" + i;

				connection.stringCommands()
				          .set(key.getBytes(), value.getBytes());
			}

			return null;
		});

		long elapsed = System.currentTimeMillis() - start;

		return new PipelineSetResponse(req.getCount(), elapsed);
	}
}
