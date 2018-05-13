package de.hska.vislab.micro;

import de.hska.vislab.micro.mock.MockUsers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserCoreApplication {

	public static void main(String[] args) {
		MockUsers.mock();
		SpringApplication.run(UserCoreApplication.class, args);
	}
}
