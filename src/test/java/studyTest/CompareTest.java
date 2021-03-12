package studyTest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@Slf4j
public class CompareTest {

	@Test
	public void basic() {
		String[] strings = {"3", "2", "5", "4"};
		Arrays.sort(strings);
		log.debug(Arrays.toString(strings));
		Arrays.sort(strings, Comparator.naturalOrder());
		log.debug(Arrays.toString(strings));
		Arrays.sort(strings, Comparator.reverseOrder());
		log.debug(Arrays.toString(strings));
	}

	@Test
	public void objectCompare(){

		/**
		 * 지역클래스(Method Local Inner Class)
		 */
		@Getter
		@ToString
		@RequiredArgsConstructor
		class User{
			private final String userName;
			private final int age;
		}

		// TODO: 2021/03/12 나이별로 정렬하고 싶음
		User user = new User("김은수", 32);
		User user2 = new User("최은수", 16);
		User user3 = new User("박은수", 2);
		User user4 = new User("이은수", 54);
		User user5 = new User("노은수", 23);
		User user6 = new User("정은수", 89);

		List<User> users = new ArrayList<>();
		users.add(user);
		users.add(user2);
		users.add(user3);
		users.add(user4);
		users.add(user5);
		users.add(user6);

		assertThat(users.size()).isEqualTo(6);

		users = users.stream().sorted(Comparator.comparing(User::getAge)).collect(Collectors.toList());
		log.debug(users.toString());
	}


	@Test
	public void test(){
		List<User> users = new ArrayList<>();
		users.add(new User("김은수", 33, new User.Job(7)));
		users.add(new User("김은수", 32, new User.Job(1)));
		users.add(new User("김은수", 31, new User.Job(3)));
		users.add(new User("김은수", 32, new User.Job(3)));
		users.add(new User("김은수", 37, new User.Job(4)));

		users = users.stream().sorted(Comparator.comparing(User::getAge).reversed()).sorted((o1, o2) -> o2.getJob().getType()- o1.getJob().getType()).collect(Collectors.toList());
		log.debug(users.toString());
	}
}

@Getter
@ToString
@RequiredArgsConstructor
class User{
	private final String name;
	private final int age;
	private final Job job;

	@Getter
	@ToString
	@RequiredArgsConstructor
	static class Job{
		private final int type;
	}

}

