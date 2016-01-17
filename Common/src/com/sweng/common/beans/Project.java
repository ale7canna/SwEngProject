package com.sweng.common.beans;

import java.io.Serializable;

public class Project  implements Serializable{

	private String name;
	private int idProject;
	private int idAdmin;
	private boolean isActive;
	
	public Project(int _idAdmin, String _name, boolean _isActive)
	{
		idAdmin = _idAdmin;
		name = _name;
		isActive = _isActive;
	}
	
	public Project(int _idProject, int _idAdmin, String _name, boolean _isActive)
	{
		idProject = _idProject;
		idAdmin = _idAdmin;
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

	public int getIdAdmin() {
		return idAdmin;
	}

	public void setIdAdmin(int idAdmin) {
		this.idAdmin = idAdmin;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}
