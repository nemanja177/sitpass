package com.ftn.fitpass.services;

import org.springframework.web.multipart.MultipartFile;

import com.ftn.fitpass.model.SportskiObjekat;

public interface SportskiObjekatService {
	
	public void indexSportskiObjekat(SportskiObjekat sportskiObjekat);
	
	public String parsePdf(MultipartFile file);
	
	public String uploadFile(MultipartFile file, String folder);
	
}
