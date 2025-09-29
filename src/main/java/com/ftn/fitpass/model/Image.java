package com.ftn.fitpass.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String serverFilename;
    
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getServerFilename() {
		return serverFilename;
	}
	
	public void setServerFilename(String serverFilename) {
		this.serverFilename = serverFilename;
	}
    
    
}
