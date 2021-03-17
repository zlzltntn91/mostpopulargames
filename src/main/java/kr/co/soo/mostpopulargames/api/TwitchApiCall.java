package kr.co.soo.mostpopulargames.api;

import kr.co.soo.mostpopulargames.web.dto.StreamsDto;

public interface TwitchApiCall {

	StreamsDto searchLiveKoreanStreams();
	StreamsDto searchLiveKoreanStreams(int size);

}
