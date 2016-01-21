package com.sweng.server;

import java.awt.Dimension;
import java.security.Guard;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.sweng.common.beans.Project;
import com.sweng.common.beans.ProjectInfo;
import com.sweng.common.beans.User;
import com.sweng.common.utils.CustomException;
import com.sweng.server.gui.ServerGUI;
import com.sweng.server.gui.GUIListener;
import com.sweng.server.gui.ProjectInfoGui;

public class GuiManager{
	
	private static ServerGUI GUI;
	ProjectInfoGui projectInfoGui;

	
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
	
	public void refreshInfo()
	{
		
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
			GUI.AddUserProjectsToList(progetti);
		}

		@Override
		public void UserProjectClicked(Project project) {
						
			if (project != null)
			{	System.out.println(project.getName());
			
				try {
					projectInfoGui = new ProjectInfoGui(DBManager.getProjectInfo(project), this);
					JFrame frame = new JFrame();
					frame.getContentPane().add(projectInfoGui);
					frame.setVisible(true);
					frame.setSize(800, 600);
					LoadProjects(null);
				}
				catch (CustomException e)
				{}
			}
		}

		@Override
		public void RemoveProjectPressed(ProjectInfo projectInfo) {
			Project p = new Project(projectInfo.getIdProject(), projectInfo.getAdmin().getIdUser(), projectInfo.getName(), projectInfo.isActive());
			try {
				DBManager.removeProject(p);
				projectInfoGui.setVisible(false);
//				projectInfoGui.dispose();
				
			}
			catch (CustomException e )
			{
				
			}
		}

		@Override
		public void ProjectClicked(Project project) {
			// TODO Auto-generated method stub
			ProjectInfo pi = null;
			try {
				pi = DBManager.getProjectInfo(project);
			} catch (CustomException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			ProjectInfoGui projectInfo = new ProjectInfoGui(pi, this);
			GUI.ChangeProjectInfo(projectInfo);
		}

	}


}
