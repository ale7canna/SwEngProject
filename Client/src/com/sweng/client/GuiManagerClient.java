package com.sweng.client;

import java.rmi.RemoteException;
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
import com.sweng.common.beans.ActivityResponsible;
import com.sweng.common.beans.Participant;
import com.sweng.common.beans.Project;
import com.sweng.common.beans.ProjectInfo;
import com.sweng.common.beans.User;
import com.sweng.common.gui.ActivityInfoGui;
import com.sweng.common.gui.ICommonGui;
import com.sweng.common.gui.NoticeInfoGui;
import com.sweng.common.gui.ProjectInfoGui;
import com.sweng.common.notice.Notice;
import com.sweng.common.utils.CustomException;

public class GuiManagerClient {

	private static ClientGUI gui = null;
	private static IClientManager clientManager;
	GUIPanelHome home = null;
	EventListenerGUI _listener = new GuiListener();

	public GuiManagerClient(IClientManager clientManager) {

		this.clientManager = clientManager;
		gui = new ClientGUI(_listener);
		switchGui(new GUIPanelSignIn(_listener));

	}

	public void switchGui(JPanel panel) {
		gui.getContentPane().removeAll();
		gui.getContentPane().add(panel);

		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setVisible(true);
		gui.setSize(600, 400);
		SwingUtilities.updateComponentTreeUI(gui);
	}

	public void goToUserHomePage() {
		if (home == null) {
			home = new GUIPanelHome(new GuiListener());
		}

		switchGui(home);
		home.setUserInfo(clientManager.getUser(), clientManager.getFriendships(), clientManager.getActivity(),
				clientManager.getProject(), clientManager.getNotices());

	}
	
	public void refreshHomeContent()
	{
		home.setUserInfo(clientManager.getUser(), clientManager.getFriendships(), clientManager.getActivity(),
				clientManager.getProject(), clientManager.getNotices());

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
	
	class GuiListener implements EventListenerGUI, ICommonGui {

		GUIaddComponent addProjectFrame = null;
		GUIaddComponent addActivityFrame = null;
		GUIaddComponent addFriendsFrame = null;
		JFrame activityInfoFrame = null;
		JFrame projectInfoFrame = null;
		JFrame noticeInfoFrame = null;
		
		Project project = null;
		Activity activity = null;

		ArrayList<User> friendships = null;
		ArrayList<Activity> activities = null;
		ArrayList<Project> projects = null;
		ArrayList<User> participants = null;
		ArrayList<User> notmyfriends = null;

		public User SignInRequest(String username, String password) {
			
			clientManager.SignInRequest(username, password);

			return null;
		}

		// ADDING VIEW
		public void addProjectView() {

			friendships = clientManager.getFriendships();
			addProjectFrame = new GUIaddComponent(this, friendships, true, false);
			addProjectFrame.setVisible(true);
			addProjectFrame.setSize(600, 600);
		}

		public void addActivityView(Project project) {
			participants = clientManager.getParticipant(project);
			addActivityFrame = new GUIaddComponent(this, participants, false, false);
			addActivityFrame.setVisible(true);
			addActivityFrame.setSize(600, 600);
		}

		public void addFriendsView() {
			// DA CREARE QUERY PER FAR SI DI TROVARE TUTTI GLI UTENTI NON MIEI
			// AMICI
			// notmyfriends = clientManager.getNotmyFriends();
			addFriendsFrame = new GUIaddComponent(this, clientManager.getNotmyFriends(), true, true);
			addFriendsFrame.setVisible(true);
			addFriendsFrame.setSize(600, 600);
			System.out.println("Add friends view");

		}

		// ADDING TO DB
		public void addProject(String name, ArrayList<Integer> participants, boolean isActive) {

			// Marzo questo project come viene creato? È quello che crea il
			// client da inviare al server. Quindi non ha l'ID.
			// non ha senso quindi fare poi l'addParticipants su quell'ID.
			// Modifico la funzione del server addProject. Le faccio restituire
			// un bean Progetto con l'id giusto.
			project = clientManager.addProject(name, clientManager.getUser().getIdUser(), isActive);
			clientManager.addParticipants(participants, project.getIdProject());

			JOptionPane.showMessageDialog(null, "Project was added correctly");
			addProjectFrame.setVisible(false);
			addProjectFrame.dispose();
			addActivityView(project);

		}

		public void addActivityContinue(String nameActivity, String place, Date hour, ArrayList<Integer> respActivity) {

			activity = clientManager.addActivity(nameActivity, project.getIdProject(), place, hour);
			clientManager.addRespActivity(activity.getIdActivity(), respActivity);

			JOptionPane.showMessageDialog(null, "Activity was added correctly");
			addActivityFrame.setVisible(false);
			addActivityFrame.dispose();
			addActivityView(project);
		}

		public void addActivityFinish(String nameActivity, String place, Date hour, ArrayList<Integer> respActivity) {

			activity = clientManager.addActivity(nameActivity, project.getIdProject(), place, hour);
			clientManager.addRespActivity(activity.getIdActivity(), respActivity);
			addActivityFrame.setVisible(false);
			addActivityFrame.dispose();
			
			if (project.isActive())
				clientManager.startProject(project);
		}

		public ArrayList<User> addFriends(ArrayList<Integer> friends) {
			clientManager.addFriends(friends);
			JOptionPane.showMessageDialog(null, "Friend added correctly, please close the window");

			addFriendsFrame.setVisible(false);
			addFriendsFrame.dispose();
			return clientManager.getFriendships();

		}

		// REMOVING FROM DB
		public void removeFriend(User u) {

			clientManager.removeFriend(u);

		}

		@Override
		public void RemoveProjectPressed(ProjectInfo projectInfo) {
			// TODO Auto-generated method stub
			clientManager.removeProject(projectInfo);
			projectInfoFrame.setVisible(false);
		}

		// showing info
		public void showProjectInfo(Project p) {
			boolean isAdmin;
			if(p.getIdAdmin()== clientManager.getId())
				isAdmin=true;
			else
				isAdmin=false;
			
			Project project = new Project(p.getIdProject(), p.getIdAdmin(), p.getName(), p.isActive());
			ProjectInfo projectInfo = clientManager.getProjectInfo(project);
			projectInfoFrame = new JFrame();
			ProjectInfoGui piGui = new ProjectInfoGui(projectInfo, this, isAdmin);
			projectInfoFrame.getContentPane().add(piGui);
			projectInfoFrame.setSize(800, 600);
			projectInfoFrame.setVisible(true);
		}

		public void showActivityInfo(Activity a) {
			ActivityInfo activityInfo = clientManager.getActivityInfo(a);
			activityInfoFrame = new JFrame();
			ActivityInfoGui aiGui = new ActivityInfoGui(activityInfo, this);
			activityInfoFrame.getContentPane().add(aiGui);
			activityInfoFrame.setSize(800, 600);
			activityInfoFrame.setVisible(true);

		}

		@Override
		public void showNoticeInfo(Notice n) {
			
			noticeInfoFrame = new JFrame();
			NoticeInfoGui niGui = new NoticeInfoGui(n, this);
			noticeInfoFrame.getContentPane().add(niGui);
			noticeInfoFrame.setSize(800, 600);
			noticeInfoFrame.setVisible(true);
			
		}
		
		
		// GETTING ARRAYLIST
		public ArrayList<User> getFriends() {
			return clientManager.getFriendships();
		}

		public ArrayList<Activity> getActivities() {
			return clientManager.getActivity();
		}

		public ArrayList<Project> getProjects() {
			return clientManager.getProject();
		}

		// SET COMPLETED
		public void completeActivity(ActivityInfo activityInfo) {
			clientManager.setActivityInfoDone(activityInfo);
			activityInfoFrame.setVisible(false);
		}

		// REFRESHING AFTER ADDING OR DELETE
		public void refreshAll() {
			refreshHomeContent();
		}

		public void buttonclickedAddProject(Project proj) {
			System.out.println("Add Project");

		}

		public void performLogout() {
			if (clientManager.getUser() != null) {
				clientManager.logout();
				JOptionPane.showMessageDialog(null, "Logout performed");
				switchGui(new GUIPanelSignIn(_listener));
			}
		}
		
		public void closeLogout(){
			if(clientManager.getUser()!=null){
				clientManager.logout();
				JOptionPane.showMessageDialog(null, "Logout performed");
			}
			
			gui.setVisible(false);
			gui.dispose();
			System.out.println("GuiClient chiuso");
			
		}

		@Override
		public void performSignUp(String username, String password, String name, String surname) {
			if(username.isEmpty() || password.isEmpty()|| surname.isEmpty() || name.isEmpty() || username == null || password==null|| surname==null || name==null)
				JOptionPane.showMessageDialog(null, "Please complete in correct way the Signup form!");
			else
				clientManager.performRegistration (username, password, name, surname);
			
		}

		@Override
		public void setNoticeRead(Notice notice) {
			clientManager.removeNotice(notice);
			noticeInfoFrame.setVisible(false);
		}

		@Override
		public ArrayList<User> removeParticipant(Participant part) {
			return clientManager.removeParticipant(part);
		}

		@Override
		public ArrayList<User> removeResponsible(ActivityResponsible resp) {
			// TODO Auto-generated method stub
			return clientManager.removeResponsible(resp);
		}

		@Override
		public void startProject(Project project) throws RemoteException,
				CustomException {
			// TODO Auto-generated method stub
			
		}

		
//		public void addText(ActivityInfo activityInfo, String text) {
//			// TODO Auto-generated method stub
//			clientManager.addTexttoActivity(activityInfo, text);
//		}

	
	}
	
	public void notifyPopUser(Notice notice) {
		
		int scelta = JOptionPane.showConfirmDialog(null, "A new notice is arrived "+ notice.getTitle(), "Notice", JOptionPane.YES_NO_OPTION);
		if (scelta == JOptionPane.YES_OPTION){
			
		}
		
	}

	public void showError(String message){
		
		if (!SwingUtilities.isEventDispatchThread()) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					JOptionPane.showMessageDialog(null, message+ ". Please try again! ");
				}
			});
		}

		System.out.println(message);
	}

}
