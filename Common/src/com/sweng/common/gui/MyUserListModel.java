package com.sweng.common.gui;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

import com.sweng.common.beans.User;

public class MyUserListModel extends DefaultListModel<String> {

	private ArrayList<User> listaUtenti = null;
	
	public void addElement(User u)
	{
		super.addElement(u.getName() + " " + u.getSurname());
		if (listaUtenti == null)
			listaUtenti = new ArrayList<>();
		listaUtenti.add(u);
	}
	
	public User getUserAt(int index)
	{
		if (listaUtenti != null)
			return listaUtenti.get(index);
		return null;
	}
	

}
