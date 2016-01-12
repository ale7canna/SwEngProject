package com.sweng.server;

import java.util.ArrayList;

import javax.swing.JFrame;

import com.sweng.common.beans.Project;
import com.sweng.common.beans.User;
import com.sweng.server.ServerGUI.GUIListener;

public class GuiManager{
	
	private static ServerGUI GUI;

	
	public GuiManager()
	{
		GUI = new ServerGUI(new GuiListener());
		GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GUI.setSize(600, 400);
		GUI.setVisible(true);
		
		
		
		
	}
	
	public void LoadUser(ArrayList<User> users){
		GUI.AddUsersToList(users);		
	}
	
	public void LoadProjects(ArrayList<Project> projects)
	{
		GUI.AddProjectsToList(projects);
	}
	
	class GuiListener implements GUIListener
	{

		@Override
		public void UserClicked() {
			// TODO Auto-generated method stub
			
		}
	}


}
