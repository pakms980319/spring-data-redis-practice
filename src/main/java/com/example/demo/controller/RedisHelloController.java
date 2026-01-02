package com.example.demo.controller;

import com.example.demo.service.RedisHelloService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RedisHelloController {

	private final RedisHelloService service;

	@PostMapping("/redis/foo")
	public String setFoo(@RequestParam String value) {
		service.setFoo(value);
		return "OK";
	}

	@GetMapping("/redis/foo")
	public String getFoo() {
		return String.valueOf(service.getFoo());
	}

	@GetMapping("/hello")
	public String hello(@RequestParam String name) {
		return service.expensiveHello(name);
	}
}
