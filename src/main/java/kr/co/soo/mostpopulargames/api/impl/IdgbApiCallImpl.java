package kr.co.soo.mostpopulargames.api.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.soo.mostpopulargames.api.IgdbApiCall;
import kr.co.soo.mostpopulargames.api.util.IgdbApiUtil;
import kr.co.soo.mostpopulargames.web.dto.IdgbGameDto;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IdgbApiCallImpl implements IgdbApiCall {

	private final String API_URL = "https://api.igdb.com/v4/";
	private final String SEARCH_GAME = "games";
	ObjectMapper mapper = new ObjectMapper();
	private List<IdgbGameDto> jsonData;

	@SneakyThrows
	@PostConstruct
	public void getPlatformJson() {
		Resource resource = new ClassPathResource("platforms.json");
		File json = resource.getFile();
		List<IdgbGameDto> platforms = mapper.readValue(json, new TypeReference<List<IdgbGameDto>>() {
		});
		jsonData = platforms;
	}

	@Override
	public IdgbGameDto getGameInfo(String gameName) throws IOException {
		IgdbApiUtil igdbApiUtil = new IgdbApiUtil();
		StringBuilder body = new StringBuilder();
		body.append("fields name, platforms;").append(" ");
		body.append("search ").append("\"").append(gameName).append("\";");

		ResponseEntity<String> result = igdbApiUtil.getResponseEntity(
				API_URL + SEARCH_GAME, body.toString(),
				String.class);

		List<IdgbGameDto> parsingResult = (List<IdgbGameDto>) Optional.ofNullable(
				mapper.readValue(result.getBody(), new TypeReference<List<IdgbGameDto>>() {
				})).orElseGet(() ->  new ArrayList<IdgbGameDto>());
		IdgbGameDto platform = Optional.ofNullable(parsingResult.get(0)).orElseGet(IdgbGameDto::new);
		int[] platformIds = Optional.of(platform.getPlatforms()).orElse(new int[]{});
		parsingResult.get(0).setPlatformsName(getPlatformNames(platformIds));
		return parsingResult.get(0);
	}

	private List<String> getPlatformNames(int[] platformsId) {

		List<String> platformsName = new ArrayList<>();
		for (IdgbGameDto platform : jsonData) {
			for (int i : platformsId) {
				if (platform.getId() == i) {
					platformsName.add(platform.getName());
				}
			}
		}

		return Optional.ofNullable(platformsName).orElseGet(ArrayList::new);
	}
}
