package kr.co.soo.mostpopulargames.web;

import kr.co.soo.mostpopulargames.api.IgdbApiCall;
import kr.co.soo.mostpopulargames.web.dto.IdgbGameDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class IgdbController {

	private final IgdbApiCall api;

	@GetMapping("/games/{gameName}")
	public ResponseEntity<?> getGameInfo(@PathVariable String gameName) throws IOException {
		IdgbGameDto result = api.getGameInfo(gameName);
		log.info("name : {}", gameName);
		log.info("result : {}", result);
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}

}
