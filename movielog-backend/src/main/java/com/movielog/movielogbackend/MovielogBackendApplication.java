package com.movielog.movielogbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // JPA Auditing 활성화
@SpringBootApplication
public class MovielogBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovielogBackendApplication.class, args);
	}

}
