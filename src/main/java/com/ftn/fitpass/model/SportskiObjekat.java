package com.ftn.fitpass.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "objekti")
public class SportskiObjekat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String naziv;

    @Column(nullable = false, length = 2000)
    private String opis;

    @Column(nullable = true)
    private Integer brojReviewa;

    @Column(nullable = true)
    private Double prosecnaOcena;

    @Column(nullable = true)
    private String kategorija;

    private String pdfPath;

    private String slikaPath;

    @Transient
    private String pdfText;
    
	public SportskiObjekat() {
		super();
	}

	public SportskiObjekat(Long id, String naziv, String opis, Integer brojReviewa, Double prosecnaOcena, String kategorija,
			String pdfPath, String slikaPath, String pdfText) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.opis = opis;
		this.brojReviewa = brojReviewa;
		this.prosecnaOcena = prosecnaOcena;
		this.kategorija = kategorija;
		this.pdfPath = pdfPath;
		this.slikaPath = slikaPath;
		this.pdfText = pdfText;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public Integer getBrojReviewa() {
		return brojReviewa;
	}

	public void setBrojReviewa(Integer brojReviewa) {
		this.brojReviewa = brojReviewa;
	}

	public Double getProsecnaOcena() {
		return prosecnaOcena;
	}

	public void setProsecnaOcena(Double prosecnaOcena) {
		this.prosecnaOcena = prosecnaOcena;
	}

	public String getKategorija() {
		return kategorija;
	}

	public void setKategorija(String kategorija) {
		this.kategorija = kategorija;
	}

	public String getPdfPath() {
		return pdfPath;
	}

	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}

	public String getSlikaPath() {
		return slikaPath;
	}

	public void setSlikaPath(String slikaPath) {
		this.slikaPath = slikaPath;
	}

	public String getPdfText() {
		return pdfText;
	}

	public void setPdfText(String pdfText) {
		this.pdfText = pdfText;
	}

	@Override
	public String toString() {
		return "Objekat [id=" + id + ", naziv=" + naziv + ", opis=" + opis + ", brojReviewa=" + brojReviewa
				+ ", prosecnaOcena=" + prosecnaOcena + ", kategorija=" + kategorija + ", pdfPath=" + pdfPath
				+ ", slikaPath=" + slikaPath + ", pdfText=" + pdfText + "]";
	}

    
}

