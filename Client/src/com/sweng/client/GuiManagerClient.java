package com.sweng.client;

import java.sql.Date;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sweng.client.gui.ClientGUI;
import com.sweng.client.gui.EventListenerGUI;
import com.sweng.client.gui.GUIPanelSignIn;
import com.sweng.client.gui.GUIaddComponent;
import com.sweng.common.beans.Activity;
import com.sweng.common.beans.Friendship;
import com.sweng.common.beans.Project;
import com.sweng.common.beans.User;

public class GuiManagerClient {

	private static ClientGUI gui = null;
	private static IClientManager clientManager;
	
	public GuiManagerClient(IClientManager clientManager){
		
		this.clientManager= clientManager;
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
		Project project = null;
		Activity activity= null;
		
		ArrayList<User> friendships = null;
		ArrayList<Activity> activities = null;
		ArrayList<Project> projects = null;
		ArrayList<User> participants=null;
			
		 
		public User SignInRequest(String username, String password) {
			
			user= clientManager.SignInRequest(username, password);
			friendships = clientManager.getFriendships(user);
			activities = clientManager.getActivity(user);
			projects = clientManager.getProject(user);
			
			return user;
			}

		public void addProjectView(){
			friendships= clientManager.getFriendships(user);
			addProjectFrame = new GUIaddComponent(this, friendships, true);
		}
		
		public void addActivityView(Project project){
			participants = clientManager.getParticipant(project);
			addActivity = new GUIaddComponent(this, participants, false);
		}

		public void addProject(String name, ArrayList<Integer> participants, boolean isActive){
			
			project = clientManager.addProject(name, user.getIdUser(), isActive);
			clientManager.addParticipants(participants, project.getIdProject());
		}
		
		public void addActivity(String nameActivity, String place, Date hour, ArrayList<Integer> respActivity){
			
			activity = clientManager.addActivity(nameActivity, project.getIdProject(), place, hour);
			clientManager.addRespActivity(activity.getIdActivity(), respActivity);
			
		}

		public void addFriends( ArrayList<Integer> friends){
			
			clientManager.addFriends(user.getIdUser(), friends);
			
		}
		@Override
		public void buttonclickedAddProject(Project proj) {
			System.out.println("Add Project");
			
		}

		@Override
		public void addFriendsView() {
			System.out.println("Add friends view");
			
		}

	
	
		
		

	
	}

}




