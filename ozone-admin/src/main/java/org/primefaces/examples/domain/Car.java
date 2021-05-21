package org.primefaces.examples.domain;

public class Car {
	
	private String model;
	private String year;
	private String manufacturer;
	private String color;

	public Car(){
		
	}
	
	public Car(String randomModel, int randomYear, String randomManufacturer,
			String randomColor) {
		this.model=randomModel;
		this.year=""+randomYear;
		this.manufacturer=randomManufacturer;
		this.color=randomColor;		
	}
	
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}

}
