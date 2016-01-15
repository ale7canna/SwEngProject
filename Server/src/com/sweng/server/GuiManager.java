package com.sweng.server;

import java.awt.Dimension;
import java.security.Guard;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.sweng.common.beans.Project;
import com.sweng.common.beans.User;
import com.sweng.common.utils.CustomException;
import com.sweng.server.gui.ServerGUI;
import com.sweng.server.gui.GUIListener;
import com.sweng.server.gui.ProjectInfoGui;

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
		public void UserClicked(User user) {
			System.out.println(user.getName());
			ArrayList<Project> progetti = null;
			
			try {
				progetti = DBManager.getProjectsFromUser(user);
			}
			catch (CustomException e)
			{
				
			}
			GUI.AddProjectsToList(progetti);
		}

		@Override
		public void ProjectClicked(Project project) {
						
			if (project != null)
			{	System.out.println(project.getName());
			
				try {
				ProjectInfoGui projectGui = new ProjectInfoGui(DBManager.getProjectInfo(project));
				projectGui.setSize(new Dimension(800, 600));
				projectGui.setVisible(true);
				}
				catch (CustomException e)
				{}
			}
		}
	}


}
