package kr.co.soo.mostpopulargames.api;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class TwitchApiUtil {

	private static HttpHeaders HTTP_HEADERS_INSTANCE = setHttpHeadersAccessTokenClientId();

	private static HttpHeaders setHttpHeadersAccessTokenClientId() {
		HttpHeaders headers = new HttpHeaders();
		// FIXME: 2021/03/12 properties
		String clientId = "zya3ijhz2mutp9vxcg5zyhguqt9tou";
		String accesToken = "s46yaix8pot0u4o40o2lt761ecyrb3";
		headers.set("Authorization", "Bearer " + accesToken);
		headers.set("Client-Id", clientId);
		return headers;
	}

	public <T> ResponseEntity getResponseEntity(String url, Class<T> t) {
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(HTTP_HEADERS_INSTANCE);
		return restTemplate.exchange(url, HttpMethod.GET, httpEntity, t);
	}
}
