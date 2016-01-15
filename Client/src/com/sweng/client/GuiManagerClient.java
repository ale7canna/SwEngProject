package com.sweng.client;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sweng.common.IServer;
import com.sweng.common.beans.Activity;
import com.sweng.common.beans.Friendship;
import com.sweng.common.beans.Project;
import com.sweng.common.beans.User;
import com.sweng.common.utils.CustomException;

public class GuiManagerClient {
	
	private static IServer server = null;
	private static ClientGUI gui = null;
	
	public GuiManagerClient(IServer server){
		
		this.server= server;
		gui = new ClientGUI();
		switchGui(new GUIPanelSignIn(new GuiListener()));
	
		
	}
	
	
	public void switchGui(JPanel panel)
	{
		gui.getContentPane().removeAll();
		gui.getContentPane().add(panel);
		
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setVisible(true);
		gui.setSize(600, 400);
	}
	
	
	 class GuiListener implements EventListenerGUI {
		
		 GUIaddProject addProjectFrame= null;
		 public  User user = null;
		public void SignInRequest(String username, String password) {
			// TODO Auto-generated method stub
			
			try {
<<<<<<< HEAD
				user = server.performLogin(username, password);
				ArrayList<User> friendship = server.getFriendsFromUser(user);
				ArrayList<Activity> activity = server.getActivityFromUser(user);
				ArrayList<Project> project = server.getProjectsFromUsers(user);
				GUIPanelHome home = new GUIPanelHome(this);
				switchGui(home);
				home.setUserInfo(user, friendship, activity, project);
=======
>>>>>>> branch 'master' of https://github.com/ale7canna/SwEngProject.git
				
				User user = null;
				ArrayList<User> friendships = null;
				ArrayList<Activity> activities = null;
				ArrayList<Project> projects = null;
				
				try {
					user = server.performLogin(username, password);
				}
				catch (CustomException e)
				{
				}
				try {
					friendships = server.getFriendsFromUser(user);
				} catch (CustomException e) {}
				try {
					activities = server.getActivityFromUser(user);
				}
				catch (CustomException e) {}
				try{
					projects = server.getProjectsFromUsers(user);
				}
				catch (CustomException e) {}
				
					GUIPanelHome home = new GUIPanelHome();
					switchGui(home);
					home.setUserInfo(user, friendships, activities, projects);
					
				}
			catch (RemoteException e)
			{
				e.printStackTrace();
			}
		}
		
		
		public void addProject(){
		addProjectFrame = new GUIaddProject();
						
		}
	}

}




