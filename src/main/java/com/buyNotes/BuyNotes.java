package com.buyNotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BuyNotes {

	//Añadir el limitRateFilter, brevo y railway y el captcha
	
	public static void main(String[] args) {
		SpringApplication.run(BuyNotes.class, args);
	}
	
}
