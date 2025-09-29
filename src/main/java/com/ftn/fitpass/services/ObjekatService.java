package com.ftn.fitpass.services;

import java.util.List;

import com.ftn.fitpass.model.Objekat;


public interface ObjekatService {
	
	public List<Objekat> findAll();
	
	public Objekat findById(Long id);
	
}
