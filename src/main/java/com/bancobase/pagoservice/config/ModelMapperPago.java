package com.bancobase.pagoservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperPago {

	/**
	 * Método que devuelve una instancia de {@link ModelMapper}.
	 * 
	 * @return Instancia de {@link ModelMapper} configurada.
	 */
	@Bean
    ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
