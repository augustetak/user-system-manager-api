package com.airfrance.ums;

import com.airfrance.ums.entities.AppConfig;
import com.airfrance.ums.entities.User;
import com.airfrance.ums.repository.MongoAppConfigRepository;
import com.airfrance.ums.repository.UserRepository;
import com.airfrance.ums.utility.Constants;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.Objects;

@SpringBootApplication
public class UserManagerRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserManagerRestApplication.class, args);
	}
	@Bean
	CommandLineRunner initDb(UserRepository repository, MongoAppConfigRepository mongoAppConfigRepository){
		return args -> {
			User user = User.builder().name("Bob").surname("Dilan").email("bob.dilan@gmail.com").country("England")
					.birthday(LocalDateTime.of(2000,02,03,0,0)).build();
			if(Objects.isNull( repository.findUserByEmail(user.getEmail()))) repository.insert(user);
			User user2 = User.builder().name("Franklin").surname("Dupont").email("franklin.dupon@gmail.com").country("France")
					.birthday(LocalDateTime.of(2001,04,05,0,0)).build();
			if(Objects.isNull( repository.findUserByEmail(user2.getEmail()))) repository.insert(user2);

			AppConfig app = AppConfig.builder().configName(Constants.ALLOWED_COUNTRY).configValue("France|FR").build();
			if(Objects.isNull( mongoAppConfigRepository.findAppConfigByConfigName(app.getConfigName()))) mongoAppConfigRepository.insert(app);
		};
	}

}
