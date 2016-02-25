package com.sweng.common.beans;

import java.io.Serializable;

public class Project  implements Serializable{

	private String name;
	private int idProject;
	private int idAdmin;
	private boolean isActive;
	private boolean isComplete;
	
	public Project(int _idProject){
		idProject = _idProject;
	}
	
	public Project(int _idAdmin, String _name, boolean _isActive)
	{
		idAdmin = _idAdmin;
		name = _name;
		isActive = _isActive;
	}
	
	public Project(int _idProject, int _idAdmin, String _name, boolean _isActive, boolean _isComplete)
	{
		idProject = _idProject;
		idAdmin = _idAdmin;
		name = _name;
		isActive = _isActive;
		isComplete = _isComplete;
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

	
	public boolean isComplete() {
		
		return isComplete;
	}

	
	public void setComplete(boolean isComplete) {
		
		this.isComplete = isComplete;
	}
	
	
}
