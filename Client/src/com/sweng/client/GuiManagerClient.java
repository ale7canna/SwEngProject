package com.sweng.client;

import java.util.Date;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.sweng.client.gui.ClientGUI;
import com.sweng.client.gui.EventListenerGUI;
import com.sweng.client.gui.GUIPanelHome;
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
		
		this.clientManager = clientManager;
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
	
	public void goToUserHomePage() {
		GUIPanelHome home = new GUIPanelHome(new GuiListener());
		switchGui(home);
		home.setUserInfo(clientManager.getUser(), clientManager.getFriendships(), clientManager.getActivity(), clientManager.getProject());
		
	}
	
	
	 class GuiListener implements EventListenerGUI {
		
		GUIaddComponent addProjectFrame= null;
		GUIaddComponent addActivityFrame = null;
		GUIaddComponent addFriendsFrame = null;
		Project project = null;
		Activity activity= null;
		
		ArrayList<User> friendships = null;
		ArrayList<Activity> activities = null;
		ArrayList<Project> projects = null;
		ArrayList<User> participants=null;
		ArrayList<User> notmyfriends=null;
			
		 
		public User SignInRequest(String username, String password) {
			
			clientManager.SignInRequest(username, password);
			
			return null;
			}

		public void addProjectView(){
			
			friendships = clientManager.getFriendships();
			addProjectFrame = new GUIaddComponent(this, friendships, true, false);
			addProjectFrame.setVisible(true);
			addProjectFrame.setSize(600, 600);
		}
		
		public void addActivityView(Project project){
			participants = clientManager.getParticipant(project);
			addActivityFrame = new GUIaddComponent(this, participants, false, false);
			addActivityFrame.setVisible(true);
			addActivityFrame.setSize(600, 600);
		}
		
		public void addFriendsView() {
			//DA CREARE QUERY PER FAR SI DI TROVARE TUTTI GLI UTENTI NON MIEI AMICI
			notmyfriends = clientManager.getNotmyFriends();
			addFriendsFrame = new GUIaddComponent(this, notmyfriends, true, true);
			addFriendsFrame.setVisible(true);
			addFriendsFrame.setSize(600, 600);
			System.out.println("Add friends view");
			
		}

		public void addProject(String name, ArrayList<Integer> participants, boolean isActive){
			
			//Marzo questo project come viene creato? È quello che crea il client da inviare al server. Quindi non ha l'ID.
			//non ha senso quindi fare poi l'addParticipants su quell'ID. 
			//Modifico la funzione del server addProject. Le faccio restituire un bean Progetto con l'id giusto.
			project = clientManager.addProject(name, clientManager.getUser().getIdUser(), isActive);
			clientManager.addParticipants(participants, project.getIdProject());
			
			JOptionPane.showMessageDialog(null, "Project was added correctly");
			addProjectFrame.setVisible(false);
			addProjectFrame.dispose();
			addActivityView(project);
		
		}
		
		public void addActivityFinish(String nameActivity, String place, Date hour, ArrayList<Integer> respActivity){
			
			activity = clientManager.addActivity(nameActivity, project.getIdProject(), place, hour);
			clientManager.addRespActivity(activity.getIdActivity(), respActivity);
			
			JOptionPane.showMessageDialog(null, "Activity was added correctly");
			addActivityFrame.setVisible(false);
			addActivityFrame.dispose();
			addActivityView(project);
		}
		
		public void addActivityContinue(String nameActivity, String place, Date hour, ArrayList<Integer> respActivity){
					
					activity = clientManager.addActivity(nameActivity, project.getIdProject(), place, hour);
					clientManager.addRespActivity(activity.getIdActivity(), respActivity);
					
					JOptionPane.showMessageDialog(null, "Activity was added correctly");
					addActivityFrame.setVisible(false);
					addActivityFrame.dispose();
				}

		public void addFriends( ArrayList<Integer> friends){
			clientManager.addFriends(friends);
			
			JOptionPane.showMessageDialog(null, "Project was added correctly, please close the window or choose add Activity to proceed");
			addFriendsFrame.setVisible(false);
			addFriendsFrame.dispose();
		}
		
		public void buttonclickedAddProject(Project proj) {
			System.out.println("Add Project");
			
		}

	}


}




