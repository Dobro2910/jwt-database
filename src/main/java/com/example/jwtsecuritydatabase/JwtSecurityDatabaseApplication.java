package com.example.jwtsecuritydatabase;

import com.example.jwtsecuritydatabase.Entity.AppUser;
import com.example.jwtsecuritydatabase.Entity.Role;
import com.example.jwtsecuritydatabase.Service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
@EntityScan(basePackages = "com/example/jwtsecuritydatabase/Entity")
public class JwtSecurityDatabaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtSecurityDatabaseApplication.class, args);
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> { //everything inside of this curley braces will run after the application is initialised
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));

			userService.saveUser(new AppUser(null, "nam", "Nam", "hello", new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Bob", "Boby", "hello", new ArrayList<>()));

			userService.addRoleToUser("Nam", "ROLE_ADMIN");
			userService.addRoleToUser("Nam", "ROLE_USER");
			userService.addRoleToUser("Boby", "ROLE_USER");
		};

	}

}
