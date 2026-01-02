package com.example.demo.controller.hash;

import com.example.demo.dto.UserCacheDto;
import com.example.demo.service.UserCacheService;
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
@RequestMapping(value = "/redis/hash")
@RequiredArgsConstructor
public class HashCacheController {

	private final UserCacheService service;

	@PostMapping
	public ResponseEntity<?> userPut(@RequestBody UserCacheDto dto) {
		Long createdId = service.putUser(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdId);
	}

	@GetMapping
	public ResponseEntity<?> getUser(@RequestParam Long id) {
		UserCacheDto user = service.getUser(id);
		return ResponseEntity.ok(user);
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteUser(@RequestParam Long id) {
		service.deleteUser(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
