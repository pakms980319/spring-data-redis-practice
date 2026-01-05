package com.example.demo.service;

import com.example.demo.dto.RankingDtos;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RankingService {

	private final StringRedisTemplate redis;

	private static final String KEY = "ranking";

	public void putScore(String member, double score) {
		redis.opsForZSet().add(KEY, member, score);
	}

	public Double incrementScore(String member, double delta) {
		return redis.opsForZSet().incrementScore(KEY, member, delta);
	}

	public List<RankingDtos.MemberScore> top(int limit) {
		// score 높은 순서대로
		Set<ZSetOperations.TypedTuple<String>> tuples = redis.opsForZSet()
		                                                     .reverseRangeByScoreWithScores(KEY, 0, Math.max(0, limit - 1));

		List<RankingDtos.MemberScore> out = new ArrayList<>();
		if (tuples == null) {
			return out;
		}

		for (ZSetOperations.TypedTuple<String> tuple : tuples) {
			out.add(new RankingDtos.MemberScore(tuple.getValue(), tuple.getScore()));
		}
		return out;
	}

	public RankingDtos.MemberRank rankOf(String member) {
		Long rank = redis.opsForZSet().reverseRank(KEY, member);
		Double score = redis.opsForZSet().score(KEY, member);
		if (rank == null && score == null) {
			return null;
		}
		return new RankingDtos.MemberRank(member, rank, score);
	}

	public void clear() {
		redis.delete(KEY);
	}
}
