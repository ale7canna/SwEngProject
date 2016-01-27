package com.sweng.common.gui;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.DefaultListModel;

import com.sweng.common.beans.Activity;
import com.sweng.common.beans.Project;
import com.sweng.common.beans.User;

public class MyActivityListModel extends DefaultListModel<String> {

	private HashMap<Activity, User> activitiesAndUSers;
	private ArrayList<Activity> activities;
	
	public void addElement(Activity a, User u)
	{
		super.addElement(a.getName() + " - " + u.getUsername());
		if (activitiesAndUSers == null)
			activitiesAndUSers = new HashMap<Activity, User>();
		activitiesAndUSers.put(a, u);
	}
	
	public void addElement(Activity a)
	{
		super.addElement(a.getName());
		if (activities == null)
			activities = new ArrayList<Activity>();
		activities.add(a);
	}
	
	public Activity getActivityAt(int index)
	{
		if (activities != null)
			return activities.get(index);
		return null;
	}
	
	@Override
	public void removeAllElements()
	{
		super.removeAllElements();
		if (activitiesAndUSers != null)
			activitiesAndUSers.clear();
		activitiesAndUSers = null;
		
		if (activities != null)
			activities.clear();
		activities = null;
			
	}
	

}
