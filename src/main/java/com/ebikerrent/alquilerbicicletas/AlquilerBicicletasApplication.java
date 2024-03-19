package com.ebikerrent.alquilerbicicletas;


import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;


@SpringBootApplication
@RestController
public class AlquilerBicicletasApplication {
	private static final Logger LOGGER= LoggerFactory.getLogger(AlquilerBicicletasApplication.class);
	public static void main(String[] args) throws SQLException, ClassNotFoundException {

		SpringApplication.run(AlquilerBicicletasApplication.class, args) ;
		LOGGER.info("eBikeRent ejecutándose...");
	}
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

}
