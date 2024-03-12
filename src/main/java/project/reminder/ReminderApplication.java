package project.reminder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * TODO 1.어떤 형식으로 일저을 받든 전부 처리 가능하도록 어댑터들을 만들어 사용(확장 가능한 구조)
 * TODO 2.설정에 따라 카톡만이 아닌 다양한 방법으로 알리미를 받을 수 있도록 하기(확장 가능한 구조)
 * TODO 3.사용자 로그인 방법도 다양한 방법으로 받을 수 있도록 하기(확장 가능한 구조)
 */
@SpringBootApplication
public class ReminderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReminderApplication.class, args);
	}

}
