package com.ftn.fitpass.model;

import java.time.LocalDate;

import com.ftn.fitpass.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "phone_number", nullable = true, length = 20)
    private String phoneNumber;

    @Column(name = "birthday", nullable = true)
    private LocalDate birthday;

    @Column(name = "address", nullable = true)
    private String address;

    @Column(name = "city", nullable = true)
    private String city;

    @Column(name = "zip_code", nullable = true, length = 10)
    private String zipCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

	public User() {
	}

	public User(String email, String password, String name, String surname, LocalDate createdAt, String phoneNumber,
			LocalDate birthday, String address, String city, String zipCode, Role role) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.createdAt = createdAt;
		this.phoneNumber = phoneNumber;
		this.birthday = birthday;
		this.address = address;
		this.city = city;
		this.zipCode = zipCode;
		this.role = role;
	}

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
		return "User [email=" + email + ", password=" + password + ", name=" + name + ", surname=" + surname
				+ ", createdAt=" + createdAt + ", phoneNumber=" + phoneNumber + ", birthday=" + birthday + ", address="
				+ address + ", city=" + city + ", zipCode=" + zipCode + ", role=" + role + "]";
	}

}

