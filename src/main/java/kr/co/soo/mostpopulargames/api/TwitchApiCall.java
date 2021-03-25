package kr.co.soo.mostpopulargames.api;

import kr.co.soo.mostpopulargames.web.dto.TwitchGameDto;
import kr.co.soo.mostpopulargames.web.dto.StreamsDto;

import java.io.IOException;
import java.util.List;

public interface TwitchApiCall {

	StreamsDto searchLiveKoreanStreams();
	StreamsDto searchLiveKoreanStreams(int size);
	List<TwitchGameDto> getGameRankingByViewerCount() throws IOException;
}
