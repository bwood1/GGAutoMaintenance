package com.example.ggcautomaintenance;

public class Car {
	private String carName;
	private String carMake;
	private String carModel;
	private int odometer;

	public String getCarName() {
		return carName;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}

	public String getCarMake() {
		return carMake;
	}

	public void setCarMake(String carMake) {
		this.carMake = carMake;
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public int getOdometer() {
		return odometer;
	}
	
	public void setOdometer(int odometer) {
		this.odometer = odometer;
	}
}