package com.appspot.demo.server.paypal.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.appspot.demo.server.paypal.service.MailerService;

@Configuration
public class MailerServiceConfig {
	@Bean
	public MailerService mailerService(){
		String fromEmail ="soulofpeace@gmail.com";
		return new MailerService(fromEmail);
	}
}
