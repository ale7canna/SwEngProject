package com.sweng.client;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.sweng.client.gui.ClientGUI;
import com.sweng.client.gui.EventListenerGUI;
import com.sweng.client.gui.GUIPanelHome;
import com.sweng.client.gui.GUIPanelSignIn;
import com.sweng.client.gui.GUIaddComponent;
import com.sweng.common.beans.Activity;
import com.sweng.common.beans.ActivityInfo;
import com.sweng.common.beans.Project;
import com.sweng.common.beans.ProjectInfo;
import com.sweng.common.beans.User;
import com.sweng.common.gui.ActivityInfoGui;
import com.sweng.common.gui.ICommonGui;
import com.sweng.common.gui.ProjectInfoGui;

public class GuiManagerClient {

	private static ClientGUI gui = null;
	private static IClientManager clientManager;
	GUIPanelHome home = null;
	EventListenerGUI _listener = new GuiListener(); 
	
	public GuiManagerClient(IClientManager clientManager){
		
		this.clientManager = clientManager;
		gui = new ClientGUI(_listener);
		switchGui(new GUIPanelSignIn(_listener));
	
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
		if(home==null)
		{	home = new GUIPanelHome(new GuiListener());
			switchGui(home);
			}
		
		home.setUserInfo(clientManager.getUser(), clientManager.getFriendships(), clientManager.getActivity(), clientManager.getProject());
		
	}
	
	
	 class GuiListener implements EventListenerGUI, ICommonGui {
		
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

		//ADDING VIEW
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
			//notmyfriends = clientManager.getNotmyFriends();
			addFriendsFrame = new GUIaddComponent(this, clientManager.getNotmyFriends(), true, true);
			addFriendsFrame.setVisible(true);
			addFriendsFrame.setSize(600, 600);
			System.out.println("Add friends view");
			
		}

		
		//ADDING TO DB
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
		
		public void addActivityContinue(String nameActivity, String place, Date hour, ArrayList<Integer> respActivity){
			
			activity = clientManager.addActivity(nameActivity, project.getIdProject(), place, hour);
			clientManager.addRespActivity(activity.getIdActivity(), respActivity);
			
			JOptionPane.showMessageDialog(null, "Activity was added correctly");
			addActivityFrame.setVisible(false);
			addActivityFrame.dispose();
			addActivityView(project);
		}
		
		public void addActivityFinish(String nameActivity, String place, Date hour, ArrayList<Integer> respActivity){
					
					activity = clientManager.addActivity(nameActivity, project.getIdProject(), place, hour);
					clientManager.addRespActivity(activity.getIdActivity(), respActivity);
					
					JOptionPane.showMessageDialog(null, "Activity was added correctly");
					addActivityFrame.setVisible(false);
					addActivityFrame.dispose();
					

				}

		public ArrayList<User> addFriends( ArrayList<Integer> friends){
			clientManager.addFriends(friends);
			JOptionPane.showMessageDialog(null, "Friend added correctly, please close the window");
			
			addFriendsFrame.setVisible(false);
			addFriendsFrame.dispose();
			return clientManager.getFriendships();
			
		}

		//REMOVING FROM DB
		public void removeFriend(User u){
			
			clientManager.removeFriend(u);
			
		}
		
		@Override
		public void RemoveProjectPressed(ProjectInfo projectInfo) {
			// TODO Auto-generated method stub
			
		}

		
		//showing info
		public void showProjectInfo(Project p){
			Project project = new Project(p.getIdProject(), p.getIdAdmin(), p.getName(), p.isActive());
			ProjectInfo projectInfo = clientManager.getProjectInfo(project);
			JFrame projectInfoFrame = new JFrame();
			ProjectInfoGui piGui = new ProjectInfoGui(projectInfo, this);
			projectInfoFrame.getContentPane().add(piGui);
			projectInfoFrame.setSize(800, 600);
			projectInfoFrame.setVisible(true);
		}
		
		public void showActivityInfo(Activity a)
		{
			ActivityInfo activityInfo = clientManager.getActivityInfo(a);
			JFrame activityInfoFrame = new JFrame();
			ActivityInfoGui aiGui = new ActivityInfoGui(activityInfo, this);
			activityInfoFrame.getContentPane().add(aiGui);
			activityInfoFrame.setSize(800, 600);
			activityInfoFrame.setVisible(true);
			
		}
		
		
		//GETTING ARRAYLIST
		public ArrayList<User> getFriends(){
			return clientManager.getFriendships();
		}
		
		public ArrayList<Activity> getActivities(){
			return clientManager.getActivity();
		}

		public ArrayList<Project> getProjects(){
			return clientManager.getProject();
		}
		
		//SET COMPLETED
		public void completeActivity(ActivityInfo activityInfo)
		{
			clientManager.setActivityInfoDone(activityInfo);
		}
		
		//REFRESHING AFTER ADDING OR DELETE
		public void refreshAll(){
			goToUserHomePage();
		}

		public void buttonclickedAddProject(Project proj) {
			System.out.println("Add Project");
			
		}
	
		public void performLogout(){
			clientManager.logout();
			JOptionPane.showMessageDialog(null, "Logout performed");
			gui.setVisible(false);
			gui.dispose();
			gui = null;
			gui = new ClientGUI(_listener);
			switchGui(new GUIPanelSignIn(_listener));
		}
	 }


	public void showMessage(String message) {
		
		if (!SwingUtilities.isEventDispatchThread()) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					JOptionPane.showMessageDialog(null, "Ciao ciao");
				}
			});
		}
		
		
		
		System.out.println(message);
	}


}




