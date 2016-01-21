package com.sweng.common.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class ActivityInfo implements Serializable {
	
	private String name;
	private String place;
	private java.util.Date hour;
	private int idActivity;
	private Project project;
	private ArrayList<User> responsabili;
	private boolean isDone;
	
	
	
	
	public ActivityInfo(int idActivity, ArrayList<User> responsabili, Project project, String name, String place,
			Date hour, boolean isDone) {
		super();
		this.idActivity = idActivity;
		this.responsabili = responsabili;
		this.project = project;
		this.name = name;
		this.place = place;
		this.hour = hour;
		this.isDone = isDone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public java.util.Date getHour() {
		return hour;
	}
	public void setHour(java.util.Date hour) {
		this.hour = hour;
	}
	public int getIdActivity() {
		return idActivity;
	}
	public void setIdActivity(int idActivity) {
		this.idActivity = idActivity;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public ArrayList<User> getResponsabili() {
		return responsabili;
	}
	public void setResponsabili(ArrayList<User> responsabili) {
		this.responsabili = responsabili;
	}
	public boolean isDone() {
		return isDone;
	}
	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}

	
}
