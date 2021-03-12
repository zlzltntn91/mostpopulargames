package kr.co.soo.mostpopulargames.web;

import kr.co.soo.mostpopulargames.api.TwitchApiCall;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TwitchController {

	private final TwitchApiCall api;

	@GetMapping("/livestreams/ko")
	public StreamsDto searchKoreaLiveStream(@RequestParam(defaultValue = "100") int size){
		if(size == 0) return api.searchLiveKoreanStreams();
		return api.searchLiveKoreanStreams(size);
	}

	@GetMapping("/games/rainking")
	public String getRanking(){
		return null;
	}
}
