package kr.co.soo.mostpopulargames.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
/**
 * SpringMvc(WEB)에 집중할 수 있는 어노테이션
 * @Controller, @ControllerAdivce 테스트 가능
 */
@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest {

	/**
	 * 웹 API 테스트시 사용
	 */
	@Autowired
	private MockMvc mvc;

	@Test
	public void hello() throws Exception {
		String hello = "hello";
		mvc.perform(get("/hello"))
				.andExpect(status().isOk())
				.andExpect(content().string(hello));
	}

	@Test
	public void helloDto_name_amount_jsonPath() throws Exception {
		String name = "김은수";
		int amount = 3000;
		mvc.perform(get("/hello/dto")
							.param("name", name)
							.param("amount", String.valueOf(3000))) // String만 허용
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(name)))
				.andExpect(jsonPath("$.amount", is(amount))); // $를 기준을 필드명 명시
	}
}