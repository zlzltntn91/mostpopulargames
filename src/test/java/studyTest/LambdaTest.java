package studyTest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
public class LambdaTest {

	@Test
	public void methodReference(){
		Function function = LambdaObject::sout;

		function.apply(null);
	}

	@Test
	public void listToMap(){
		@Setter
		@Getter
		@ToString
		@RequiredArgsConstructor
		class User{
			private final String name;
		}

		List<User> map = new ArrayList<>();
		map.add(new User("sdfsdf"));
		map.add(new User("sdfssdfdf"));
		map.add(new User("sdfsdxvzcxf"));

		Map<String, ?> mapee = map.stream().collect(Collectors.toMap(User::getName, Function.identity()));
		log.info(mapee.toString());

	}

}

@Slf4j
class LambdaObject{
	public static Object sout(Object o) {
		log.debug("hello lamba");
		return null;
	}

}

