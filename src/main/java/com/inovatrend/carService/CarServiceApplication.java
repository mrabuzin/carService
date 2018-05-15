package com.inovatrend.carService;

import com.inovatrend.carService.domain.Permission;
import com.inovatrend.carService.domain.User;
import com.inovatrend.carService.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@SpringBootApplication
public class CarServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarServiceApplication.class, args);
	}


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private UserManager userManager;

	@PostConstruct
	public void onInit() {

		try{
			userManager.loadUserByUsername("admin");
		} catch(Exception e) {

			String password = "admin123";
			password = passwordEncoder().encode(password);

			User adminUser = new User(null, "admin","matija.rabuzin94@gmail.com", password,password, "", true, Arrays.asList(Permission.values()));
			userManager.save(adminUser);
		}
	}
}
