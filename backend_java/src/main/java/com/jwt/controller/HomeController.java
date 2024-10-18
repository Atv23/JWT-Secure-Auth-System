package com.jwt.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jwt.model.User;
import com.jwt.service.UserService;

@RestController
@CrossOrigin(origins = "*")
public class HomeController {
		
	@Autowired
	private UserService userService;
	
	@GetMapping("/welcome")
	public String welcome()
	{
		String text = "this is pvt page";
		
		return text;
	}
	@GetMapping("/getuser")
	public Map<String, Object> getUser(Principal principal) throws IOException {

		Map<String, Object> response = new HashMap<>();
		String email = principal.getName();
		User user = this.userService.findUserByUsername(email);
		if (user != null) {
	        response.put("name", user.getName());
	        response.put("email", user.getEmail());
	        response.put("age", user.getAge());
	        response.put("gender", user.getGender());
	        response.put("description", user.getDescription());
	        response.put("role", user.getRole());
	        // Construct the image URL
	        String imagePath = "/img/" + URLEncoder.encode(user.getImage(), StandardCharsets.UTF_8);
	        String imageUrl = "http://localhost:8081" + imagePath; // Full URL
	        response.put("imagePath", imageUrl); // Return the complete URL
	    } else {
	        response.put("error", "User not found");
	    }
	    return response;
	}
	@PostMapping(value="/signup",consumes = "multipart/form-data")
	public ResponseEntity<?> handleSignup(
			 	@RequestParam("name") String name,
		        @RequestParam("email") String email,
		        @RequestParam("password") String password,
		        @RequestParam("gender") String gender,
		        @RequestParam("age") Integer age,
		        @RequestParam("description") String description,
		        @RequestParam(value= "image", required = false) MultipartFile file
			){
		System.out.println("Received signup req");
		User user = new User(); // Create a new User object
	    user.setName(name);
	    user.setEmail(email);
	    user.setGender(gender);
	    user.setAge(age);
	    user.setDescription(description);
	    user.setRole("ROLE_ADMIN");
		try {
			if (file==null || file.isEmpty() ) {
				System.out.println("Image not uploaded");
				user.setImage("default.png");
			}
			else {
				String originalFilename = file.getOriginalFilename();
				System.out.println(file.getOriginalFilename());
				// uploading file to project folder
				File uploadedImg = new ClassPathResource("static/img").getFile();
				if (!uploadedImg.exists()) {
			        System.out.println("not able to find static/img"); 
			        uploadedImg.mkdirs(); // Create the directory if it doesn't exist // Create the directory if it doesn't exist
			    }
				String suffix = "img" + String.valueOf(user.getId()) + getFileExtension(originalFilename);
				Path path = Paths.get(uploadedImg.getAbsolutePath() + File.separator + suffix);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				System.out.println("Image Uploaded to: " + path.toAbsolutePath());
				user.setImage(suffix);
			}
		} catch (Exception e) {
			 e.printStackTrace();
			 System.out.println("Catch block of file upload");
			 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(password);
		user.setPassword(encodedPassword); // Set the encoded password
		
		User saveUser = this.userService.createUser(user);
		System.out.println("User Saved in DB: " + saveUser);
		if(user==null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	// Helper method to extract file extension
	private String getFileExtension(String filename) {
	    return filename.substring(filename.lastIndexOf('.'));
	}

}
