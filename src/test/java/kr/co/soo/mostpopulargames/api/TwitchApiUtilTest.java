package kr.co.soo.mostpopulargames.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.HttpHeaders;

import java.lang.reflect.Field;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@Slf4j
public class TwitchApiUtilTest {

	@Test
	public void HttpHeader는싱글톤이다() throws NoSuchFieldException, IllegalAccessException {
		TwitchApiUtil apiUtil = new TwitchApiUtil();
		Field field = apiUtil.getClass().getDeclaredField("HTTP_HEADERS_INSTANCE");
		field.setAccessible(true);
		HttpHeaders httpHeaders = (HttpHeaders) field.get(apiUtil);

		apiUtil = new TwitchApiUtil();
		field = apiUtil.getClass().getDeclaredField("HTTP_HEADERS_INSTANCE");
		field.setAccessible(true);

		assertThat(httpHeaders).isEqualTo(field.get(apiUtil));
		assertThat(httpHeaders).isSameAs(field.get(apiUtil));

		apiUtil = new TwitchApiUtil();
		field = apiUtil.getClass().getDeclaredField("HTTP_HEADERS_INSTANCE");
		field.setAccessible(true);

		assertThat(httpHeaders).isEqualTo(field.get(apiUtil));
		assertThat(httpHeaders).isSameAs(field.get(apiUtil));

	}

	@Test
	public void HttpHeader는싱글톤이다2() throws NoSuchFieldException, IllegalAccessException {

		TwitchApiUtil apiUtil = new TwitchApiUtil();
		Field field = apiUtil.getClass().getDeclaredField("HTTP_HEADERS_INSTANCE");
		field.setAccessible(true);
		HttpHeaders httpHeaders = (HttpHeaders) field.get(apiUtil);

		apiUtil = new TwitchApiUtil();
		field = apiUtil.getClass().getDeclaredField("HTTP_HEADERS_INSTANCE");
		field.setAccessible(true);

		assertThat(httpHeaders).isEqualTo(field.get(apiUtil));
		assertThat(httpHeaders).isSameAs(field.get(apiUtil));

		apiUtil = new TwitchApiUtil();
		field = apiUtil.getClass().getDeclaredField("HTTP_HEADERS_INSTANCE");
		field.setAccessible(true);

		assertThat(httpHeaders).isEqualTo(field.get(apiUtil));
		assertThat(httpHeaders).isSameAs(field.get(apiUtil));

	}

}