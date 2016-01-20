package com.sweng.client;

import java.sql.Date;
import java.util.ArrayList;

import javax.swing.JFrame;
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
		User user = null;
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
			
			//Qui Marzo c'erano le funzioni per recuperare USER, FRIENDSHIPS, ACTIVITIES e PROJECTS.
			//Secondo me non se ne deve occupare GuiManager, quindi (molto democraticamente) le ho tolte.
			//Credo che tutte le richieste verso il server debbano partire dalla classe client.
			
			//Qui limitiamoci a fargli fare il login. Sarà la classe client a decidere cosa voler fare dopo il login.
			clientManager.SignInRequest(username, password);
			
			return null;
			}

		public void addProjectView(){
			// Qua mancavano le ultime due istruzioni. Non veniva visualizzato ovviamente.
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

		public Project addProject(String name, ArrayList<Integer> participants, boolean isActive){
			
			//Marzo questo project come viene creato? È quello che crea il client da inviare al server. Quindi non ha l'ID.
			//non ha senso quindi fare poi l'addParticipants su quell'ID. 
			//Modifico la funzione del server addProject. Le faccio restituire un bean Progetto con l'id giusto.
			project = clientManager.addProject(name, clientManager.getUser().getIdUser(), isActive);
			clientManager.addParticipants(participants, project.getIdProject());
			
			return project;
		}
		
		public void addActivity(String nameActivity, String place, Date hour, ArrayList<Integer> respActivity){
			
			activity = clientManager.addActivity(nameActivity, project.getIdProject(), place, hour);
			clientManager.addRespActivity(activity.getIdActivity(), respActivity);
			
		}

		public void addFriends( ArrayList<Integer> friends){
			int idUser = user.getIdUser();
			clientManager.addFriends(idUser, friends);
			
		}
		
		public void buttonclickedAddProject(Project proj) {
			System.out.println("Add Project");
			
		}

	
	
	

	
	
		
		

	
	}


}




