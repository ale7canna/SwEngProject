package com.sweng.common.beans;

import java.sql.Date;

public class Activity {
	
	private String name;
	private String place;
	private Date hour;
	private int idActivity;
	private int idProject;

	public Activity(int _idProject, int _idActivity, String _name, String _place, Date _hour)
	{
		idProject = _idProject;
		idActivity = _idActivity;
		name = _name;
		place = _place;
		hour = _hour;
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

	public Date getHour() {
		return hour;
	}

	public void setHour(Date hour) {
		this.hour = hour;
	}

	public int getIdActivity() {
		return idActivity;
	}

	public void setIdActivity(int idActivity) {
		this.idActivity = idActivity;
	}

	public int getIdProject() {
		return idProject;
	}

	public void setIdProject(int idProject) {
		this.idProject = idProject;
	}
	
	
	
	
	
}
