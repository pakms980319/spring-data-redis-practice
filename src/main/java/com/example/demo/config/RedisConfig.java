package com.example.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

import java.time.Duration;

@Configuration
@EnableCaching
@EnableRedisRepositories
@RequiredArgsConstructor
public class RedisConfig {

	private final RedisProperties redisProperties;

	public static final String HELLO_CACHE = "helloCache";

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration standalone = new RedisStandaloneConfiguration();
		standalone.setHostName(redisProperties.getHost());
		standalone.setPort(redisProperties.getPort());

		String password = redisProperties.getPassword();
		if (StringUtils.hasText(password)) {
			standalone.setPassword(RedisPassword.of(password));
		}

		LettuceClientConfiguration clientConfig = (redisProperties.getSsl() != null && redisProperties.getSsl().isEnabled()) ? LettuceClientConfiguration.builder().useSsl().build()
			: LettuceClientConfiguration.builder().build();

		return new LettuceConnectionFactory(standalone, clientConfig);
	}

	@Bean
	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, String> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);

		StringRedisSerializer stringSerializer = new StringRedisSerializer();
		template.setKeySerializer(stringSerializer);
		template.setValueSerializer(stringSerializer);
		template.setHashKeySerializer(stringSerializer);
		template.setHashValueSerializer(stringSerializer);

		template.afterPropertiesSet();
		return template;
	}

	@Bean
	public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
		RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
		                                                               .entryTtl(Duration.ofSeconds(10));

		RedisCacheConfiguration helloCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
		                                                                  .entryTtl(Duration.ofSeconds(20));

		return RedisCacheManager.builder(connectionFactory)
		                        .withCacheConfiguration(HELLO_CACHE, helloCacheConfig)
		                        .cacheDefaults(defaultConfig)
		                        .build();
	}
}
