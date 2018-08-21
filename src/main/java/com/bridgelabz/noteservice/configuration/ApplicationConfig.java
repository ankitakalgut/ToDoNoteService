package com.bridgelabz.noteservice.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/************************************************************************************
 * @author Ankita Kalgutkar
 *
 * 
 *PURPOSE:Configuration class To map to particular bean
 ************************************************************************************/
@Configuration
public class ApplicationConfig 
{
	@Bean
	public ModelMapper modelMapper() 
	{
		return new ModelMapper();
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() 
	{
		return new BCryptPasswordEncoder();
	}
}
