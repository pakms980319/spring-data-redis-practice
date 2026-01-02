package com.example.demo.controller.set;

import com.example.demo.service.UniqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis/set")
@RequiredArgsConstructor
public class UniqueController {
	private final UniqueService service;

	@PostMapping("/add")
	public ResponseEntity<?> add(@RequestParam String value) {
		String result = service.add(value) ? "ADDED" : "ALREADY_EXISTS";
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	@GetMapping("/contains")
	public ResponseEntity<?> contains(@RequestParam String value) {
		String result = service.contains(value) ? "CONTAINS" : "NOT CONTAINS";
		return ResponseEntity.ok(result);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> delete(@RequestParam String value) {
		boolean isDeleted = service.delete(value);
		String result = isDeleted ? "DELETED" : "NOT EXIST";
		return ResponseEntity.ok(result);
	}

	@GetMapping("/size")
	public ResponseEntity<?> count() {
		long count = service.count();
		return ResponseEntity.ok(count);
	}
}
