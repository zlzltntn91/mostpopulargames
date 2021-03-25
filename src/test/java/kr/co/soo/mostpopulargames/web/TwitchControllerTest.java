package kr.co.soo.mostpopulargames.web;

import kr.co.soo.mostpopulargames.web.dto.StreamsDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TwitchControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void 라이브스트림조회_기본ko_100() {
		ResponseEntity<StreamsDto> result = restTemplate.getForEntity("/livestreams", StreamsDto.class);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

		assertThat(result.getBody().getData().size())
				.isEqualTo(100);

		assertThat(result.getBody().getData().stream()
						   .filter(v -> "ko".equals(v.getLanguage()))
						   .count())
				.isEqualTo(100);

		assertThat(result.getBody().getData().stream()
						   .filter(v -> !"ko".equals(v.getLanguage()))
						   .count())
				.isEqualTo(0);
	}

}