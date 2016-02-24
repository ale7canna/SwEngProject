package com.sweng.common.beans;

import java.io.Serializable;

public class User implements Serializable {

	private int idUser;
	private String name;
	private String surname;
	private String username;
	private String password;
	
	public User(String _name, String _surname, String _username, String _password)
	{
		name = _name;
		surname = _surname;
		username = _username;
		password = _password;
	}

	
	public User(int _idUser, String _name, String _surname, String _username, String _password)
	{
		idUser = _idUser;
		name = _name;
		surname = _surname;
		username = _username;
		password = _password;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean equals(Object obj)
	{
		User u = (User)obj;
		if (u.idUser == getIdUser() && u.name == getName() && u.surname == getSurname() && u.username == getUsername())
			return true;
		
		return false;
		
	}
}
