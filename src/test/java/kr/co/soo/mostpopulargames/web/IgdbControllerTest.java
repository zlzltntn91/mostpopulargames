package kr.co.soo.mostpopulargames.web;

import kr.co.soo.mostpopulargames.web.dto.IdgbGameDto;
import kr.co.soo.mostpopulargames.web.dto.StreamsDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IgdbControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void getGameInfo(){
		ResponseEntity<IdgbGameDto> result = restTemplate.getForEntity("/games/League of Legends", IdgbGameDto.class);
		log.info(result.getBody().toString());
	}
}
