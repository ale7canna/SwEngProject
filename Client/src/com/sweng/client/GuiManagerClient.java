package com.sweng.client;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sweng.client.gui.ClientGUI;
import com.sweng.client.gui.EventListenerGUI;
import com.sweng.client.gui.GUIPanelHome;
import com.sweng.client.gui.GUIPanelSignIn;
import com.sweng.client.gui.GUIaddComponent;
import com.sweng.common.IServer;
import com.sweng.common.beans.Activity;
import com.sweng.common.beans.Participant;
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
		
		 GUIaddComponent addProjectFrame= null;
		 User user = null;
		 GUIaddComponent addActivity = null;
		 
			
			ArrayList<User> friendships = null;
			ArrayList<Activity> activities = null;
			ArrayList<Project> projects = null;
			
		 
		 public User SignInRequest(String username, String password) {
			// TODO Auto-generated method stub
			
			try {			
				
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
				
					GUIPanelHome home = new GUIPanelHome(this);
					switchGui(home);
					home.setUserInfo(user, friendships, activities, projects);
					
				}
			catch (RemoteException e)
			{
				e.printStackTrace();
			}
			
			return user;
		}
		 
		
		public void addProject(){
			
			try {
				friendships= server.getFriendsFromUser(user);
				addProjectFrame = new GUIaddComponent(this, friendships, true);
			} catch (RemoteException | CustomException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
						
		}
		
		public void addActivity(Project project){
			
			ArrayList<User> participant = null;
			try {
				participant = server.getParticipantsFromProject(project);
			} catch (CustomException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			addActivity = new GUIaddComponent(this, participant, false);
		}


		@Override
		public void buttonclickedAddProject(Project proj) {
			// TODO Auto-generated method stub
			
		}

	
	}

}




