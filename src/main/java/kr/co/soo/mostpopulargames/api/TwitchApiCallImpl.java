package kr.co.soo.mostpopulargames.api;

import kr.co.soo.mostpopulargames.web.dto.StreamsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TwitchApiCallImpl implements TwitchApiCall{

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
}
