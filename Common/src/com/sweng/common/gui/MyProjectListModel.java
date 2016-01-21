package com.sweng.common.gui;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

import com.sweng.common.beans.Project;

public class MyProjectListModel extends DefaultListModel<String> {

	private ArrayList<Project> listaProgetti = null;
	
	public void addElement(Project u)
	{
		super.addElement(u.getName());
		if (listaProgetti == null)
			listaProgetti = new ArrayList<>();
		listaProgetti.add(u);
	}
	
	public Project getProjectAt(int index)
	{
		if (listaProgetti != null)
			return listaProgetti.get(index);
		return null;
	}
	
	@Override
	public void removeAllElements()
	{
		super.removeAllElements();
		if (listaProgetti != null)
			listaProgetti.clear();
		listaProgetti = null;
	}
	

}
