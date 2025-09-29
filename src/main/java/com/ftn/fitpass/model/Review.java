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
}
