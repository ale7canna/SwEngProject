package com.sweng.common.beans;

import java.io.Serializable;

public class Participant implements Serializable {
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
	
	@Override
	public boolean equals(Object other)
	{
		if (other instanceof Participant)
		{
			Participant o = (Participant)other;
			if (o.getIdProject() == idProject && o.getIdUser() == idUser)
				return true;
			else
				return false;
		}
		else
			return false;
	}
	
	

}
