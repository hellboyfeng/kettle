package com.hm.kettle;

public class KettleType {
	private String name;
	private boolean ktr;
	private String ktrPath;
	private boolean kjb;
	private String kjbPath;
	private String schedule;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isKtr() {
		return ktr;
	}
	public void setKtr(boolean ktr) {
		this.ktr = ktr;
	}
	public String getKtrPath() {
		return ktrPath;
	}
	public void setKtrPath(String ktrPath) {
		this.ktrPath = ktrPath;
	}
	public boolean isKjb() {
		return kjb;
	}
	public void setKjb(boolean kjb) {
		this.kjb = kjb;
	}
	public String getKjbPath() {
		return kjbPath;
	}
	public void setKjbPath(String kjbPath) {
		this.kjbPath = kjbPath;
	}
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
}
