package kr.co.soo.mostpopulargames.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import kr.co.soo.mostpopulargames.api.impl.IdgbApiCallImpl;
import kr.co.soo.mostpopulargames.api.util.IgdbApiUtil;
import kr.co.soo.mostpopulargames.web.dto.IdgbGameDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class IgdbApiConnectTest {

	ObjectMapper mapper = new ObjectMapper();

	{
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
	}

	@Test
	public void oauth() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate
				.postForEntity("https://id.twitch.tv/oauth2/token" +
									   "?client_id=zya3ijhz2mutp9vxcg5zyhguqt9tou" +
									   "&client_secret=ica4o6brilkuop9bma8gnmwbi7u466" +
									   "&grant_type=client_credentials", null, String.class);
		log.info(responseEntity.getBody());

	}

	@Test
	public void igdbConnectTest() throws JsonProcessingException {
		RestTemplate test = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)" +
				" AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		headers.set("Authorization", "Bearer 0d58whomn7csf5dsexkymy75nyi598");
		headers.set("Client-ID", "zya3ijhz2mutp9vxcg5zyhguqt9tou");
		String body = "fields name, platforms; search \"TEKKEN 7\";";
		HttpEntity<String> entity = new HttpEntity<>(body, headers);
		log.info(entity.getBody());
		log.info(entity.getHeaders().toString());

		ResponseEntity<Object> responseEntity = test
				.postForEntity("https://api.igdb.com/v4/games",
							   entity,
							   Object.class);

		log.info(mapper.writeValueAsString(responseEntity.getBody()));

	}

	@Test
	public void 플랫폼조회() throws JsonProcessingException {
		RestTemplate test = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)" +
				" AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		headers.set("Authorization", "Bearer 0d58whomn7csf5dsexkymy75nyi598");
		headers.set("Client-ID", "zya3ijhz2mutp9vxcg5zyhguqt9tou");
//		String body = "fields *; where id=(6, 14); exclude alternative_name;";
		String body = "fields name; limit 400;";
		HttpEntity<String> entity = new HttpEntity<>(body, headers);
		log.info(entity.getBody());
		log.info(entity.getHeaders().toString());

		ResponseEntity<Object> responseEntity = test
				.postForEntity("https://api.igdb.com/v4/platforms",
							   entity,
							   Object.class);

		log.info(mapper.writeValueAsString(responseEntity.getBody()));
	}

	// TODO: 2021-03-24 POST
	@Test
	public void Json파일에서조회하기() throws IOException {
		Resource resource = new ClassPathResource("platforms.json");
		File json = resource.getFile();
		ObjectMapper mapper = new ObjectMapper();
		List<IdgbGameDto> platforms = mapper.readValue(json, new TypeReference<List<IdgbGameDto>>() {
		});

		int[] platformsId = new int[]{6, 14};

		List<String> platformsName = new ArrayList<>(platformsId.length);
		for (IdgbGameDto platform : platforms) {
			for (int i : platformsId) {
				if (platform.getId() == i) {
					platformsName.add(platform.getName());
				}
			}
		}

		log.info(platformsName.toString());

	}

	@Test
	public void 게임정보조회_APIUTIL사용() throws IOException, NoSuchFieldException, IllegalAccessException {
		IgdbApiUtil igdbApiUtil = new IgdbApiUtil();
		IdgbApiCallImpl idgbApiCall = new IdgbApiCallImpl();
		Field API_URL = idgbApiCall.getClass().getDeclaredField("API_URL");
		API_URL.setAccessible(true);
		Field SEARCH_GAME = idgbApiCall.getClass().getDeclaredField("SEARCH_GAME");
		SEARCH_GAME.setAccessible(true);

		String gameName = "LEAGUE OF LEGENDS";
		StringBuilder body = new StringBuilder();
		body.append("fields name, platforms;").append(" ");
		body.append("search ").append("\"").append(gameName).append("\";");

		ResponseEntity<String> result = igdbApiUtil.getResponseEntity(
				API_URL.get(idgbApiCall).toString() + SEARCH_GAME.get(idgbApiCall).toString(), body.toString(),
				String.class);

		List<IdgbGameDto> tt = mapper.readValue(result.getBody(), new TypeReference<List<IdgbGameDto>>() {
		});
	}
}