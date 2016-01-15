package com.sweng.client.gui;

import java.util.ArrayList;

import com.sweng.common.beans.User;

public interface EventListenerGUI {

	public User SignInRequest(String username, String password); 
	public void addProject();
	

	public void buttonclickedAddProject(String nameProject, ArrayList<Integer> partecipants);
	
}
