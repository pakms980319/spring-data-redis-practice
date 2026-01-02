package com.example.demo.controller.ttl;

import com.example.demo.service.TtlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/redis/ttl")
@RequiredArgsConstructor
public class TtlController {

	private final TtlService service;

	/**
	 * 예) POST /redis/ttl/set?key=test&value=hello&seconds=30
	 */
	@PostMapping("/set")
	public ResponseEntity<?> setWithTtl(@RequestParam String key,
	                                    @RequestParam String value,
	                                    @RequestParam long seconds) {
		service.setWithTtl(key, value, Duration.ofSeconds(seconds));
		return ResponseEntity.ok().build();
	}

	/**
	 * 예) GET /redis/ttl/get?key=test
	 */
	@GetMapping("/get")
	public ResponseEntity<?> get(@RequestParam String key) {
		return ResponseEntity.ok(service.get(key));
	}

	/**
	 * 예) GET /redis/ttl/remaining?key=test
	 */
	@GetMapping("/remaining")
	public ResponseEntity<?> remaining(@RequestParam String key) {
		return ResponseEntity.ok(service.remainingTtlSeconds(key));
	}

	/**
	 * 예) POST /redis/ttl/extend?key=test&seconds=120
	 */
	@PostMapping("/extend")
	public ResponseEntity<?> extend(@RequestParam String key,
	                                @RequestParam long seconds) {
		service.extendTtl(key, Duration.ofSeconds(seconds));
		return ResponseEntity.ok().build();
	}
}
