package com.ftn.fitpass.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Review {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int equipmentGrade;
    private int staffGrade;
    private int hygieneGrade;
    private int spaceGrade;

    private String text;
    private LocalDateTime createdAt;

    @ManyToOne
    private Facility facility;

    @ManyToOne
    private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getEquipmentGrade() {
		return equipmentGrade;
	}

	public void setEquipmentGrade(int equipmentGrade) {
		this.equipmentGrade = equipmentGrade;
	}

	public int getStaffGrade() {
		return staffGrade;
	}

	public void setStaffGrade(int staffGrade) {
		this.staffGrade = staffGrade;
	}

	public int getHygieneGrade() {
		return hygieneGrade;
	}

	public void setHygieneGrade(int hygieneGrade) {
		this.hygieneGrade = hygieneGrade;
	}

	public int getSpaceGrade() {
		return spaceGrade;
	}

	public void setSpaceGrade(int spaceGrade) {
		this.spaceGrade = spaceGrade;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Facility getFacility() {
		return facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
    
    
    
}
