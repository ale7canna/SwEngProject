package com.sweng.common.beans;

public class Project {

	private String name;
	private int idProject;
	private int idActivity;
	private boolean isActive;
	
	public Project(int _idProject, int _idActivity, String _name, boolean _isActive)
	{
		idProject = _idProject;
		idActivity = _idActivity;
		name = _name;
		isActive = _isActive;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIdProject() {
		return idProject;
	}

	public void setIdProject(int idProject) {
		this.idProject = idProject;
	}

	public int getIdActivity() {
		return idActivity;
	}

	public void setIdActivity(int idActivity) {
		this.idActivity = idActivity;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}
