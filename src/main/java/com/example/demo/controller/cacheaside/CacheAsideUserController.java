package com.example.demo.controller.cacheaside;

import com.example.demo.dto.UserCacheDto;
import com.example.demo.service.CacheAsideUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis/cache-aside")
@RequiredArgsConstructor
public class CacheAsideUserController {

	private final CacheAsideUserService service;

	/**
	 * 예) GET /redis/cache-aside/user?id=1
	 * - 첫 호출: cache miss -> fake DB -> cache write
	 * - 다음 호출: cache hit
	 */
	@GetMapping("/user")
	public ResponseEntity<?> getUser(@RequestParam long id) {
		UserCacheDto user = service.getUser(id);
		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
		}
		return ResponseEntity.ok(user);
	}

	/**
	 * 예) GET /redis/cache-aside/ttl?id=1
	 */
	@GetMapping("/ttl")
	public ResponseEntity<?> ttl(@RequestParam long id) {
		return ResponseEntity.ok(service.ttlSeconds(id));
	}

	/**
	 * 예) POST /redis/cache-aside/db
	 * body: {"id": 1, "username": "alice2", "userAge": 99}
	 */
	@PostMapping("/db")
	public ResponseEntity<UserCacheDto> upsertDb(@RequestBody UserCacheDto dto) {
		return ResponseEntity.ok(service.upsertDb(dto));
	}

	/**
	 * 예) DELETE /redis/cache-aside/cache?id=1
	 */
	@DeleteMapping("/cache")
	public ResponseEntity<?> evictCache(@RequestParam long id) {
		service.evictCache(id);
		return ResponseEntity.noContent().build();
	}
}
