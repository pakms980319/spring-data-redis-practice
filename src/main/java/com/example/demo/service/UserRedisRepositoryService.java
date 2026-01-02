package com.example.demo.service;

import com.example.demo.dto.UserCacheDto;
import com.example.demo.redisrepo.UserRedisEntity;
import com.example.demo.redisrepo.UserRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRedisRepositoryService {

	private final UserRedisRepository repository;

	public UserCacheDto save(UserCacheDto dto) {
		repository.save(new UserRedisEntity(dto.getId(), dto.getUsername(), dto.getUserAge()));
		return dto;
	}

	public UserCacheDto findById(long id) {
		Optional<UserRedisEntity> found = repository.findById(id);
		return found.map(e -> new UserCacheDto(e.getId(), e.getUsername(), e.getUserAge())).orElse(null);
	}

	public void delete(long id) {
		repository.deleteById(id);
	}
}
