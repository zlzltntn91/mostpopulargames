package kr.co.soo.mostpopulargames.web;

import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringRunner.class)
@WebMvcTest(TwitchController.class)
public class TwitchControllerTest {

	@Autowired
	MockMvc mvc;

	@Test
	@Ignore
	public void 한국어라이브스트림조회() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/livestreams/ko"))
				.andExpect(status().isOk());
	}

}