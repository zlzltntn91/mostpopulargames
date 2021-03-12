package kr.co.soo.mostpopulargames.web;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {

	@Test
	public void 롬복기능테스트() {
		HelloResponseDto dto = new HelloResponseDto("김은수", 3000);
		assertThat(dto.getName()).isEqualTo("김은수");
	}
}