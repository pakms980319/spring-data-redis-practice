package com.example.demo.controller.repository;

import com.example.demo.dto.UserCacheDto;
import com.example.demo.service.UserRedisRepositoryService;
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
@RequestMapping("/redis/repository")
@RequiredArgsConstructor
public class UserRedisRepositoryController {

	private final UserRedisRepositoryService service;

	/**
	 * 예) POST /redis/repository/user
	 * body: {"id": 1, "username": "pak", "userAge": 30}
	 */
	@PostMapping("/user")
	public ResponseEntity<?> save(@RequestBody UserCacheDto dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
	}

	/**
	 * 예) GET /redis/repository/user?id=1
	 * - @RedisHash(timeToLive=60) 때문에 60초 지나면 조회가 null (404)
	 */
	@GetMapping("/user")
	public ResponseEntity<?> get(@RequestParam long id) {
		UserCacheDto found = service.findById(id);
		if (found == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found (or expired)");
		}
		return ResponseEntity.ok(found);
	}

	/**
	 * 예) DELETE /redis/repository/user?id=1
	 */
	@DeleteMapping("/user")
	public ResponseEntity<Void> delete(@RequestParam long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
