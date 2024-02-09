package com.ecommerce.ecommerce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}


// Con los comandos siguientes generamos una clave encriptada para agragarla manualmente a la base de datos para
// el usuario ADMIN
//
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//
//	@Bean
//	public CommandLineRunner createpasswordcommand(){
//		return args -> {
//			System.out.println(passwordEncoder.encode("carla2024"));
//		};
//	}
}
