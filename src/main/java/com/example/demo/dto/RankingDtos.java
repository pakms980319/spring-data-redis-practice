package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

public class RankingDtos {
	@Data
	public static class PutScoreRequest {
		private String member;
		private Double score;
	}

	@Data
	@AllArgsConstructor
	public static class MemberScore {
		private String member;
		private Double score;
	}

	@Data
	@AllArgsConstructor
	public static class MemberRank {
		private String member;
		private Long rank;  // 0-based
		private Double score;
	}
}
