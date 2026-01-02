package com.example.demo.redisrepo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "userRepo", timeToLive = 60)  // value: key, timeToLive: ttl (seconds)
public class UserRedisEntity {

	@Id
	private Long id;

	private String username;

	private Integer userAge;
}
