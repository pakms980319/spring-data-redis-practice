package com.example.demo.controller.pipeline;

import com.example.demo.dto.PipelineSetRequest;
import com.example.demo.dto.PipelineSetResponse;
import com.example.demo.service.PipelineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis/pipeline")
@RequiredArgsConstructor
public class PipelineController {

	private final PipelineService service;

	/**
	 * POST /redis/pipeline/set
	 * { "prefix": "pipe", "count": 10000 }
	 * 파이프라인 방식 - 네트워크 왕복 : 1번 (빠름 이점)
	 * 다만 원자성 X, 롤백 X
	 */
	@PostMapping("/set")
	public PipelineSetResponse setMany(@RequestBody PipelineSetRequest request) {
		return service.setMany(request);
	}
}
