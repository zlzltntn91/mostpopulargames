package kr.co.soo.mostpopulargames.api.impl;

import kr.co.soo.mostpopulargames.api.TwitchApiCall;
import kr.co.soo.mostpopulargames.api.util.TwitchApiUtil;
import kr.co.soo.mostpopulargames.web.dto.TwitchGameDto;
import kr.co.soo.mostpopulargames.web.dto.StreamsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
public class TwitchApiCallImpl implements TwitchApiCall {

	// FIXME: 2021/03/12 properties
	private final String API_URL = "https://api.twitch.tv";
	private final String SEARCH_CHANNEL = "/helix/streams";

	@Override
	public StreamsDto searchLiveKoreanStreams() {
		return searchLiveKoreanStreams(20);
	}

	@Override
	public StreamsDto searchLiveKoreanStreams(int size) {
		TwitchApiUtil twitchApiUtil = new TwitchApiUtil();
		if (!(0 < size && size < 101)) {
			throw new IllegalArgumentException("size는 0보다 크고 100보다 작아야 함");
		}
		String url = API_URL + SEARCH_CHANNEL + "?language=ko&first=" + size;
		ResponseEntity<StreamsDto> responseEntity = twitchApiUtil.getResponseEntity(url, StreamsDto.class);
		return responseEntity.getBody();
	}

	@Override
	public List<TwitchGameDto> getGameRankingByViewerCount() {
		StreamsDto streamsDto = searchLiveKoreanStreams(100);

		Map<String, Integer> groupingByGameName = streamsDto.getData().stream()
				.collect(groupingBy(StreamsDto.StreamerDto::getGame_name,
									summingInt(StreamsDto.StreamerDto::getViewer_count)));

		Set<Map.Entry<String, Integer>> entrySet = groupingByGameName.entrySet();

		List<TwitchGameDto> rankings = new ArrayList<>();

		for (Map.Entry<String, Integer> stringIntegerEntry : entrySet) {
			TwitchGameDto game = new TwitchGameDto();
			game.setGame_name(stringIntegerEntry.getKey());
			game.setViewer_count(stringIntegerEntry.getValue());
			game.setStreamers(streamsDto.getData().stream()
									  .filter(v -> v.getGame_name().equals(game.getGame_name()))
									  .sorted(Comparator.comparingInt(StreamsDto.StreamerDto::getViewer_count).reversed())
									  .collect(Collectors.toList()));
			rankings.add(game);
		}

		List<TwitchGameDto> descByViewerCount = rankings.stream()
				.sorted(Comparator.comparingInt(TwitchGameDto::getViewer_count).reversed())
				.collect(toList());

		return descByViewerCount;
	}
}
