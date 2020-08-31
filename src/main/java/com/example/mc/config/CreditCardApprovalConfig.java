package com.example.mc.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CreditCardApprovalConfig {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper=new ModelMapper();
		
		mapper.getConfiguration().setAmbiguityIgnored(true);
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		
		return mapper;
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
	return builder.build();
	}
}
