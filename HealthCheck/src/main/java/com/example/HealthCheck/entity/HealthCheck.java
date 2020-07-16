package com.example.HealthCheck.entity;

public class HealthCheck {

	private int id;
	private double bodytemperature;
	private int pulse;
	private int systolicbloodpressure;
	private int diastolicbloodpressure;
	private int aturation;

	public HealthCheck() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getBodytemperature() {
		return bodytemperature;
	}

	public void setBodytemperature(double bodytemperature) {
		this.bodytemperature = bodytemperature;
	}

	public int getPulse() {
		return pulse;
	}

	public void setPulse(int pulse) {
		this.pulse = pulse;
	}

	public int getSystolicbloodpressure() {
		return systolicbloodpressure;
	}

	public void setSystolicbloodpressure(int systolicbloodpressure) {
		this.systolicbloodpressure = systolicbloodpressure;
	}

	public int getDiastolicbloodpressure() {
		return diastolicbloodpressure;
	}

	public void setDiastolicbloodpressure(int diastolicbloodpressure) {
		this.diastolicbloodpressure = diastolicbloodpressure;
	}

	public int getSaturation() {
		return aturation;
	}

	public void setSaturation(int saturation) {
		this.aturation = saturation;
	}

}
