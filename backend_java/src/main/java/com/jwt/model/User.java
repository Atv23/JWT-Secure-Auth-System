package com.jwt.model;

public class User {
	private String name;
	private int age;
	private String city;
	private String gender;
	public User(String name, int age, String city, String gender) {
		super();
		this.name = name;
		this.age = age;
		this.city = city;
		this.gender = gender;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	@Override
	public String toString() {
		return "User [name=" + name + ", age=" + age + ", city=" + city + ", gender=" + gender + "]";
	}
	
	
	
}
