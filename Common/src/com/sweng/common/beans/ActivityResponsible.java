package com.sweng.common.beans;

public class ActivityResponsible {

	private int idUser;
	private int idActivity;
	
	public ActivityResponsible(int _idUser, int _idActivity)
	{
		idUser = _idUser;
		idActivity = _idActivity;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public int getIdActivity() {
		return idActivity;
	}

	public void setIdActivity(int idActivity) {
		this.idActivity = idActivity;
	}
}
