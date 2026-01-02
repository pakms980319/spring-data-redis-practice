package com.example.demo.redisrepo;

import org.springframework.data.repository.CrudRepository;

public interface UserRedisRepository extends CrudRepository<UserRedisEntity, Long> {
}
