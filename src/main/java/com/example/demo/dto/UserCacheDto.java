package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCacheDto {
	Long id;
	String username;
	Integer userAge;
}
