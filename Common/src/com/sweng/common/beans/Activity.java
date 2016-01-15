package com.sweng.common.beans;

import java.io.Serializable;
import java.sql.Date;

public class Activity implements Serializable{
	
	private String name;
	private String place;
	private Date hour;
	private int idActivity;
	private int idProject;
	private boolean isDone;

	

	public Activity(int _idProject, int _idActivity, String _name, String _place, Date _hour, boolean _isDone)
	{
		idProject = _idProject;
		idActivity = _idActivity;
		name = _name;
		place = _place;
		hour = _hour;
		isDone = _isDone;
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
	
	public boolean getIsDone() {
		return isDone;
	}

	public void setIsDone(boolean isDone) {
		this.isDone = isDone;
	}
	
	
	
}
