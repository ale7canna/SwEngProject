package com.sweng.client.gui;

import com.sweng.common.beans.Project;
import com.sweng.common.beans.User;

public interface EventListenerGUI {

	public User SignInRequest(String username, String password); 
	public void addProject();
	

	public void buttonclickedAddProject(Project proj);
	
}
