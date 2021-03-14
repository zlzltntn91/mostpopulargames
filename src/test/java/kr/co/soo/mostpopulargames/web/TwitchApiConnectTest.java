package kr.co.soo.mostpopulargames.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.soo.mostpopulargames.api.TwitchApiCall;
import kr.co.soo.mostpopulargames.api.TwitchApiCallImpl;
import kr.co.soo.mostpopulargames.api.TwitchApiUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
		log.debug(responseEntity.getBody());
	}

	@Test
	public void 현재한국어로방송중인채널조회_20개() {
		StreamsDto responseDto = api.searchLiveKoreanStreams();

		assertThat(responseDto.getData().size()).isEqualTo(20);
		assertThat(responseDto.getData().stream()
				.filter(v -> "ko".equalsIgnoreCase(v.getLanguage()))
				.collect(Collectors.toList())
				.size())
				.isEqualTo(20);
	}

	@Test
	public void 현재한국어로방송중인채널조회_50개() throws JsonProcessingException {
		StreamsDto responseDto = api.searchLiveKoreanStreams(50);
		assertThat(responseDto.getData().size()).isEqualTo(50);
		log.info(mapper.writeValueAsString(responseDto));
	}

	@Test
	public void 현재한국어로방송중인채널_게임명으로그룹() throws JsonProcessingException {
		StreamsDto streamsDto = api.searchLiveKoreanStreams(100);
		Map<String, List<StreamsDto.StreamerDto>> result = streamsDto.getData().stream()
				.collect(Collectors.groupingBy(StreamsDto.StreamerDto::getGame_name));

		log.info(mapper.writeValueAsString(result));
	}

	@Test
	public void 현재한국어로방송중인채널_게임명으로그룹핑_시청자가많은순() throws JsonProcessingException {
		/**
		 * 	Comparator<Map.Entry<String, Integer>> totalViewerCountDesc = (game1, game2) -> {
		 * 		return game1.getValue() - game2.getValue();
		 *  };
		 *
		 *  Comparator<Map.Entry<String, Integer>> totalViewerCountDesc = (game1, game2) -> game1.getValue() - game2.getValue();
		 *
		 *  Comparator<Map.Entry<String, Integer>> totalViewerCountDesc = Comparator.comparingInt(Map.Entry::getValue);
		 */

		StreamsDto streamsDto = api.searchLiveKoreanStreams(100);

		Map<String, Integer> groupingByGameName = streamsDto.getData().stream()
				.collect(Collectors.groupingBy(StreamsDto.StreamerDto::getGame_name,
						Collectors.summingInt(StreamsDto.StreamerDto::getViewer_count)));

		Comparator<Map.Entry<String, Integer>> totalViewerComparator = Comparator.comparingInt(Map.Entry::getValue);

		List<Map.Entry<String, Integer>> gameList_descTotalViewer = groupingByGameName.entrySet().stream()
				.sorted(totalViewerComparator.reversed())
				.collect(Collectors.toList());

		log.info(mapper.writeValueAsString(gameList_descTotalViewer));
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

	/**
	 * 첫번째 인자가 두번쨰 인자보다
	 * 작다면 음수
	 * 같다면 0
	 * 크다면 양수
	 *
	 * @throws JsonProcessingException
	 */
	@Test
	public void compratorTest() throws JsonProcessingException {

		Comparator<StreamsDto.StreamerDto> comparator = (o1, o2) -> {
			int result = o2.getViewer_count() - o1.getViewer_count();
			return result;
		};

		StreamsDto streamsDto = api.searchLiveKoreanStreams(10);
		streamsDto.getData().stream().sorted(comparator).forEach(v -> System.out.println(v.getViewer_count()));

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
	public void 풍월량() throws JsonProcessingException {
		StreamsDto dto = api.searchLiveKoreanStreams();
		log.info("풍월량: {}", mapper.writeValueAsString(dto.getData().stream().filter(streamer -> streamer.getUser_name().equals("풍월량")).collect(Collectors.toList())));
	}
}
