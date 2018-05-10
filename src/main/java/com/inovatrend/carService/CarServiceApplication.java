package com.inovatrend.carService;

import com.inovatrend.carService.domain.Permission;
import com.inovatrend.carService.domain.User;
import com.inovatrend.carService.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@SpringBootApplication
public class CarServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarServiceApplication.class, args);
	}

//	@Bean
//	public static PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Autowired
	private UserManager userManager;

	@PostConstruct
	public void onInit() {
		UserDetails admin = userManager.loadUserByUsername("admin");
		if (admin == null) {
//			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//			String password = "admin123";
//
//			password = passwordEncoder.encode(password);

			User adminUser = new User(null, "admin", "admin123",  true, Arrays.asList(Permission.values()));
			userManager.save(adminUser);
		}
	}
}
