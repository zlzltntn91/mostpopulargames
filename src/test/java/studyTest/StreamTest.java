package studyTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Java6Assertions.assertThat;


/**
 * TODO: 2021/03/14
 * <p>
 * Stream api
 * Collectors api
 * TreeSet? TreeMap?
 */
@Slf4j
public class StreamTest {

	@Test
	public void stream() {
		Stream stream = Stream.empty();
		Collection collection = (Collection) stream.collect(Collectors.toList());
		log.info("size : {}", collection.size());
	}

	@Test
	public void random() {
		for (int i = 0; i < 100; i++) {
			log.info("Math.random : {}", (int) (Math.random() * 27) + 1);
		}

		Random random = new Random();
		for (int i = 0; i < 50; i++) {

			log.info("Ranom.nextInt() : {}", random.nextInt(20));
		}
	}

	ObjectMapper mapper = new ObjectMapper();

	@Test
	public void randomInt() throws JsonProcessingException {
		String [] lastNames = {"김", "이", "박", "최", "정"};
		String [] firstNames = {"은수", "예림", "규일", "현진", "이커", "날두", "흥민"};
		List<User> users = new ArrayList<>();
		Random random = new Random();
		for (int i = 0; i < 500; i++) {
			User user = new User();
			user.setAge(random.nextInt(50));
			user.setName(lastNames[random.nextInt(5)] + firstNames[random.nextInt(7)]);
			users.add(user);
		}
		assertThat(users.size()).isEqualTo(500);
		log.info("toString: {}", mapper.writeValueAsString(users));
		// 이름별로 그룹핑
		Map<String, List<User>> usersByName = users.stream().collect(Collectors.groupingBy(User::getName));
		log.info(mapper.writeValueAsString(usersByName));

		// 이름 그룹, 명수
		Map<String, Long> usersByName2 = users.stream().collect(Collectors.groupingBy(User::getName, Collectors.counting()));
		log.info(mapper.writeValueAsString(usersByName2));

		// 나이별로 그룹


	}

	@Setter
	@Getter
	class User {
		String name;
		int age;
	}
}
