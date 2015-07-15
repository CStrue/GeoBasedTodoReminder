package com.avilyne.rest.resource;


import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

//@XMLAccessorType wird für JSON benötigt, um JSON zu sagen, dass die String-Values aus den Getter/Setter-Methoden verwendet werden sollen
//Alternativ können auch direkt die Attribute verwendet werden.
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Errinnerung implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String errinnerungstext = "";
	public String strasse = "";
	public int hausnummer = 0;
	public int plz = 0;
	public String ort ="";
	public String land = "";
	public double latitude;
	public double longitude;
		
	public Errinnerung (String Errinnerungstext, String Straße, int Hausnummer, int Plz, String Ort, String Land, double Lattitude, double Logitude) {
		this.errinnerungstext = Errinnerungstext;
		this.strasse = Straße;
		this.hausnummer = Hausnummer;
		this.plz = Plz;
		this.ort = Ort;
		this.land = Land;
		this.latitude = Lattitude;
		this.longitude = Logitude;
	}
	
	public Errinnerung () {
		
	}
	
	public String getErrinnerungstext() {
		return errinnerungstext;
	}

	public void setErrinnerungstext(String errinnerungstext) {
		this.errinnerungstext = errinnerungstext;
	}

	
	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String straße) {
		this.strasse = straße;
	}

	public int getHausnummer() {
		return hausnummer;
	}

	public void setHausnummer(int hausnummer) {
		this.hausnummer = hausnummer;
	}

	public int getPlz() {
		return plz;
	}

	public void setPlz(int plz) {
		this.plz = plz;
	}

	public String getOrt() {
		return ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	public String getLand() {
		return land;
	}

	public void setLand(String land) {
		this.land = land;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double Logitude) {
		this.longitude = Logitude;
	}
}
	