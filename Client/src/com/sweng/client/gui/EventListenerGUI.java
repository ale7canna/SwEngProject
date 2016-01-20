package com.sweng.client.gui;

import java.sql.Date;
import java.util.ArrayList;

import com.sweng.common.beans.Project;
import com.sweng.common.beans.User;

public interface EventListenerGUI {

	public User SignInRequest(String username, String password); 
	
	public Project addProject(String name, ArrayList<Integer> participants, boolean isActive);
	
	public void addProjectView();
	
	public void addActivityView(Project project);
	
	public void addActivity(String nameActivity, String place, Date hour, ArrayList<Integer> respActivity);
	
	public void addFriendsView();
	
	public void addFriends( ArrayList<Integer> friends);
	
	public void buttonclickedAddProject(Project proj);
	
}
