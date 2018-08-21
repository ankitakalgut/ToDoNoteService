package com.bridgelabz.noteservice;

import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.client.RestClientException;

/*******************************************************************************
 * @author Ankita Kalgutkar
 *
 *******************************************************************************/
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@RibbonClient(name="forex-service")
public class ToDoNoteServiceApplication 
{
	public static void main(String[] args) throws RestClientException, IOException 
	{
		SpringApplication.run(ToDoNoteServiceApplication.class, args);		
	}
}
