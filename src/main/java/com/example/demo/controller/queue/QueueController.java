package com.example.demo.controller.queue;

import com.example.demo.dto.UserCacheDto;
import com.example.demo.service.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis/queue")
@RequiredArgsConstructor
public class QueueController {

	private final QueueService queue;

	@PostMapping("/enqueue")
	public ResponseEntity<?> enqueue(@RequestBody UserCacheDto dto) {
		Long totalSize = queue.enqueue(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(totalSize);
	}

	@GetMapping("/dequeue")
	public ResponseEntity<?> dequeue() {
		UserCacheDto dequeue = queue.dequeue();
		return ResponseEntity.ok(dequeue);
	}

	@GetMapping("/size")
	public ResponseEntity<?> size() {
		Long queueTotalSize = queue.size();
		return ResponseEntity.ok(queueTotalSize);
	}
}
