package com.ftn.fitpass.model;

import java.time.LocalDate;

import com.ftn.fitpass.enums.Role;

public class UserRequest {

	private String email;
    private String password;
    private String name;
    private String surname;
    private LocalDate createdAt;
    private String phoneNumber;
    private LocalDate birthday;
    private String address;
    private String city;
    private String zipCode;
    private Role role;
    
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public LocalDate getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public LocalDate getBirthday() {
		return birthday;
	}
	
	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getZipCode() {
		return zipCode;
	}
	
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	public Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	@Override
	public String toString() {
		return "UserRequest [email=" + email + ", password=" + password + ", name=" + name + ", surname=" + surname
				+ ", createdAt=" + createdAt + ", phoneNumber=" + phoneNumber + ", birthday=" + birthday + ", address="
				+ address + ", city=" + city + ", zipCode=" + zipCode + ", role=" + role + "]";
	}
    
    
}
