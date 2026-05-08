package com.buyNotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.buyNotes.controller.ListasController;

@SpringBootApplication
public class MainNotes {

	//Añadir el limitRateFilter, brevo y railway
	
	public static void main(String[] args) {
		SpringApplication.run(MainNotes.class, args);
	}
	
}
