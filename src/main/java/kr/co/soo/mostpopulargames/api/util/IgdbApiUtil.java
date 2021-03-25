package kr.co.soo.mostpopulargames.api.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class IgdbApiUtil {
	private static HttpHeaders HTTP_HEADERS_INSTANCE = setHttpHeadersAccessTokenClientId();

	private static HttpHeaders setHttpHeadersAccessTokenClientId() {
		HttpHeaders headers = new HttpHeaders();
		String clientId = "zya3ijhz2mutp9vxcg5zyhguqt9tou";
		String accesToken = "0d58whomn7csf5dsexkymy75nyi598";
		headers.set("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)" +
				" AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		headers.set("Authorization", "Bearer " + accesToken);
		headers.set("Client-ID", clientId);
		return headers;
	}

	public <T> ResponseEntity getResponseEntity(String url, String body, Class<T> t) {
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> httpEntity = new HttpEntity<>(body, HTTP_HEADERS_INSTANCE);
		return restTemplate.exchange(url, HttpMethod.POST, httpEntity, t);
	}
}
