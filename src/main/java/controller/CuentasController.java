package controller;

import java.util.HashMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
//@CrossOrigin(origins = "*")
public class CuentasController {
	
	  @GetMapping("/crear")
	  public ResponseEntity<?> crearPartida(@RequestParam(value = "username") String username) {
		return null;
		
	  
	  
	  }
	
	
	//meter jackson y jwt y hibernate
	

	public static void main(String[] args) {
		SpringApplication.run(CuentasController.class, args);
		SpringApplication.run(ListasController.class, args);
	}
	
	
	

}
