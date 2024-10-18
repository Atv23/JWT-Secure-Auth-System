package com.jwt.model;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	@Column(unique = true, nullable = false)
	private String email;
	private String password;
	private int age;
	private String gender;
	private String description;
	private String role;
	private String image;
	
	public User(int id, String name, String email, String password, int age, String gender, String description,
			String role, String image) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.age = age;
		this.gender = gender;
		this.description = description;
		this.role = role;
		this.image = image;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	// Getters and Setters
	
    public String getName() { return name; }
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
   
    public String getRole() {	return role; }
	public void setRole(String role) {	this.role = role; }
	
	public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", age=" + age
				+ ", gender=" + gender + ", description=" + description + ", role=" + role + ", image=" + image + "]";
	}
    
    
	
}
