package com.jwt.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.model.User;

@RestController
@CrossOrigin(origins = "*")
public class HomeController {
		
	@GetMapping("/welcome")
	public String welcome()
	{
		String text = "this is pvt page";
		
		return text;
	}
	@GetMapping("/getuser")
	public Map<String, String> getUser(Principal principal) {
//		User user1 = new User("Atharva",23,"Lucknow","Male");
		Map<String, String> response = new HashMap<>();
		String name = principal.getName();
		response.put("name", name);
	    return response;
	}
}
