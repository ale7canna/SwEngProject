package com.sweng.common.beans;

public class Participant {
	private int idUser;
	private int idProject;
	
	public Participant(int _idUser, int _idProject)
	{
		idUser = _idUser;
		idProject = _idProject;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public int getIdProject() {
		return idProject;
	}

	public void setIdProject(int idProject) {
		this.idProject = idProject;
	}
	
	

}
