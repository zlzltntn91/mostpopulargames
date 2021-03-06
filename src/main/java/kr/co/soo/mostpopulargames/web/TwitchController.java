package kr.co.soo.mostpopulargames.web;

import kr.co.soo.mostpopulargames.api.TwitchApiCall;
import kr.co.soo.mostpopulargames.web.dto.TwitchGameDto;
import kr.co.soo.mostpopulargames.web.dto.StreamsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TwitchController {

	private final TwitchApiCall api;

	@GetMapping(value = {"/livestreams/{language}/{streamerId}"})
	public ResponseEntity<StreamsDto> searchKoreaLiveStream(@PathVariable(required = false) String language,
															@PathVariable(required = false) String streamerId) {

		StreamsDto result = null;
		language = Optional.ofNullable(language).orElse("ko");
		streamerId = Optional.ofNullable(streamerId).orElse("");

		result = api.searchLiveKoreanStreams(100);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/games/ranking")
	public ResponseEntity<List<TwitchGameDto>> getRanking() throws IOException {
		return new ResponseEntity<>(api.getGameRankingByViewerCount(), HttpStatus.OK);
	}
}
