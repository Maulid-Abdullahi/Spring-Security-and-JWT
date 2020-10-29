package com.project.Bookshop;

import com.project.Bookshop.entity.User;
import com.project.Bookshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@SpringBootApplication
public class BookshopApplication {

	@Autowired
	private UserRepository userRepository;

	/**
	 * Load this method on application start up
	 * and populate in the Database
	 */

	@PostConstruct
	public void initUser(){
		List<User> users = Stream.of(
				new User((long) 101,"Maulid","password","maulid@gmail.com"),
				new User((long) 102,"Hamza","12345","hamza@gmail.com"),
				new User((long) 103,"Juve","123456","juve@gmail.com"),
				new User((long) 104,"Hani","1234567","hani@gmail.com")
		).collect(Collectors.toList());
		userRepository.saveAll(users);
	}
	public static void main(String[] args) {
		SpringApplication.run(BookshopApplication.class, args);
	}
}
