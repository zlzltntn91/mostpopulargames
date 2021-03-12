package kr.co.soo.mostpopulargames;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 스프링 부트의 자동 설정, 스프링 Bean 관리 모두 자동 설정
 * 항상 프로젝트의 최상단에 위치해야함
 * Application이 있는 위치부터 설정을 읽어감
 *
 * 프로젝트의 메인 클래스
*/
@SpringBootApplication
public class Application {
	/**
	 * 내장 WAS를 실행함
	 * 서버에 톰켓을 설치할 필요가 없고 스프링 부트로 만들어진
	 * jar 파일로 실행하면 됨
	 *
	 * 스프링 부트에서는 내장 was를 사용하는 것을 권장함
	 * '언제 어디서나 같은 환경에서 프로젝트를 배포할 수 있기 때문'
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
