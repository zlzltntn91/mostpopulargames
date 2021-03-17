package kr.co.soo.mostpopulargames.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import kr.co.soo.mostpopulargames.api.TwitchApiCall;
import kr.co.soo.mostpopulargames.api.TwitchApiCallImpl;
import kr.co.soo.mostpopulargames.api.TwitchApiUtil;
import kr.co.soo.mostpopulargames.web.dto.StreamsDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
public class TwitchApiConnectTest {

	private final String API_URL = "https://api.twitch.tv/helix";
	private ObjectMapper mapper = new ObjectMapper();
	private TwitchApiCall api = new TwitchApiCallImpl();

	{
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
	}

	@Test
	public void 토큰정보조회() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "OAuth " + "s46yaix8pot0u4o40o2lt761ecyrb3");
		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(httpHeaders);
		ResponseEntity<String> responseEntity = restTemplate.exchange("https://id.twitch.tv/oauth2/validate",
				HttpMethod.GET,
				httpEntity,
				String.class);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		log.debug(responseEntity.getBody());
	}

	@Test
	public void 현재한국어로방송중인채널조회_20개() {
		StreamsDto responseDto = api.searchLiveKoreanStreams();
		assertThat(responseDto.getData().size()).isEqualTo(20);
	}

	@Test
	public void 현재한국어로방송중인채널조회_50개() throws JsonProcessingException {
		StreamsDto responseDto = api.searchLiveKoreanStreams(50);
		assertThat(responseDto.getData().size()).isEqualTo(50);
	}

	@Test
	public void 현재한국어로방송중인채널_게임명으로그룹_100개중() throws JsonProcessingException {
		StreamsDto apiResult = api.searchLiveKoreanStreams(100);
		assertThat(apiResult.getData().size()).isEqualTo(100);

		Map<String, List<StreamsDto.StreamerDto>> groupByGameName = apiResult.getData().stream()
				.collect(Collectors.groupingBy(StreamsDto.StreamerDto::getGame_name));
		assertThat(groupByGameName.keySet().size()).isNotZero();

		int totalStreamerSize = groupByGameName.entrySet().stream()
				.mapToInt(v -> v.getValue().size())
				.sum();
		assertThat(totalStreamerSize).isEqualTo(apiResult.getData().size());

		for (Map.Entry<String, List<StreamsDto.StreamerDto>> streamsEntrySet : groupByGameName.entrySet()) {
			for (StreamsDto.StreamerDto streamer : streamsEntrySet.getValue()) {
				assertThat(streamsEntrySet.getKey()).isEqualTo((streamer.getGame_name()));
			}
		}
	}

	@Test
	public void 현재한국어로방송중인채널_게임명으로그룹핑_시청자가많은순() throws JsonProcessingException {
		StreamsDto streamsDto = api.searchLiveKoreanStreams(100);
		long totalViewer = streamsDto.getData().stream().collect(Collectors.summingInt(StreamsDto.StreamerDto::getViewer_count));
		assertThat(totalViewer).isNotZero();

		Map<String, Integer> groupingByGameName = streamsDto.getData().stream()
				.collect(Collectors.groupingBy(StreamsDto.StreamerDto::getGame_name, Collectors.summingInt(StreamsDto.StreamerDto::getViewer_count)));

		long groupTotalViewerCount = groupingByGameName.entrySet().stream().collect(Collectors.summingInt(v -> v.getValue()));
		assertThat(totalViewer).isEqualTo(groupTotalViewerCount);

		Comparator<Map.Entry<String, Integer>> totalViewerComparator = Comparator.comparingInt(Map.Entry::getValue);

		List<Map.Entry<String, Integer>> gameList_descTotalViewer = groupingByGameName.entrySet().stream()
				.sorted(totalViewerComparator.reversed())
				.collect(Collectors.toList());
	}

	@Test
	public void 현재한국어로방송중인채널_게임명으로그룹핑_스트리머가많은순() throws JsonProcessingException {
		StreamsDto streamsDto = api.searchLiveKoreanStreams(100);

		// 게임명으로 그룹핑
		Map<String, List<StreamsDto.StreamerDto>> result = streamsDto.getData().stream()
				.map(v -> {
					if (v.getGame_name().length() == 0) {
						StreamsDto.StreamerDto t = new StreamsDto.StreamerDto();
						t.setGame_name("게임명 알 수 없음");
						return t;
					}
					return v;
				})
				.collect(Collectors.groupingBy(StreamsDto.StreamerDto::getGame_name));

		// 엔트리셋으로 변환
		Set<Map.Entry<String, List<StreamsDto.StreamerDto>>> entrySet = result.entrySet();

		// 엔트리를 맵 gameName=streamerSize 로 변환
		Map<String, Integer> countStreams = new HashMap<>();
		for (Map.Entry<String, List<StreamsDto.StreamerDto>> entry : entrySet) {
			countStreams.put(entry.getKey(), entry.getValue().size());
		}
		// 스트리머가 많은순으로 정렬
		Set<?> resultSet = countStreams.entrySet().stream()
				.sorted((v1, v2) -> v2.getValue().compareTo(v1.getValue()))
				.collect(Collectors.toCollection(LinkedHashSet::new));

		log.info(mapper.writeValueAsString(resultSet));
	}

	@Test
	public void 현재한국어로방송중인채널중_첫번째게임조회() {
		TwitchApiUtil twitchApiUtil = new TwitchApiUtil();
		StreamsDto responseDto = api.searchLiveKoreanStreams(50);
		assertThat(responseDto.getData().size()).isEqualTo(50);

		String firstStreamsGameId = responseDto.getData().get(1).getGame_id();
		ResponseEntity<String> result = twitchApiUtil.getResponseEntity(API_URL + "/games?id=" + firstStreamsGameId, String.class);
		log.info(result.getBody());
	}

	@Test
	public void 특정스트리머조회_스트리머아이디() throws JsonProcessingException {
		StreamsDto dto = api.searchLiveKoreanStreams();

	}
}
