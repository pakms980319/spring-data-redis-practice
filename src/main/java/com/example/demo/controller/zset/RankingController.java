package com.example.demo.controller.zset;

import com.example.demo.dto.RankingDtos;
import com.example.demo.service.RankingService;
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
@RequestMapping("/redis/ranking")
@RequiredArgsConstructor
public class RankingController {

	private final RankingService service;

	/**
	 * 예) POST /redis/ranking/score
	 * body: {"member": "alice", "score": 100}
	 */
	@PostMapping("/score")
	public ResponseEntity<?> putScore(@RequestBody RankingDtos.PutScoreRequest req) {
		service.putScore(req.getMember(), req.getScore());
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	/**
	 * 예) POST /redis/ranking/incr?member=alice&delta=10
	 */
	@PostMapping("incr")
	public ResponseEntity<?> incr(@RequestParam String member,
	                              @RequestParam double delta) {
		return ResponseEntity.ok(service.incrementScore(member, delta));
	}

	/**
	 * 예) GET /redis/ranking/top?limit=5
	 */
	@GetMapping("/top")
	public ResponseEntity<?> top(@RequestParam(defaultValue = "10") int limit) {
		return ResponseEntity.ok(service.top(limit));
	}

	/**
	 * 예) GET /redis/ranking/rank?member=alice
	 */
	@GetMapping("/rank")
	public ResponseEntity<?> rank(@RequestParam String member) {
		RankingDtos.MemberRank memberRank = service.rankOf(member);
		if (memberRank == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("member not found");
		}
		return ResponseEntity.ok(memberRank);
	}

	/**
	 * 예) DELETE /redis/ranking
	 */
	@DeleteMapping
	public ResponseEntity<?> clear() {
		service.clear();
		return ResponseEntity.noContent().build();
	}
}
