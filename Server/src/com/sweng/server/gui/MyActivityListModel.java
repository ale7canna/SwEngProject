package com.sweng.server.gui;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.DefaultListModel;

import com.sweng.common.beans.Activity;
import com.sweng.common.beans.Project;
import com.sweng.common.beans.User;

public class MyActivityListModel extends DefaultListModel<String> {

	private HashMap<Activity, User> activitiesAndUSers;
	
	public void addElement(Activity a, User u)
	{
		super.addElement(a.getName() + " - " + u.getUsername());
		if (activitiesAndUSers == null)
			activitiesAndUSers = new HashMap<Activity, User>();
		activitiesAndUSers.put(a, u);
	}
	
//	public Project getProjectAt(int index)
//	{
//		if (listaActivity != null)
//			return listaActivity.get(index);
//		return null;
//	}
	
	@Override
	public void removeAllElements()
	{
		super.removeAllElements();
		if (activitiesAndUSers != null)
			activitiesAndUSers.clear();
		activitiesAndUSers = null;
	}
	

}
