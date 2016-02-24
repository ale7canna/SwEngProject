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
		gui.repaint();
		gui.revalidate();
	}
	
	public void goToUserHomePage() {
		
		if (home == null) {
			home = new GUIPanelHome(new GuiListener());
		}
		
		switchGui(home);
		home.setUserInfo(clientManager.getUser(), clientManager.getFriendships(), clientManager.getActivity(),
				clientManager.getProject(), clientManager.getNotices());
				
	}
	
	public void refreshHomeContent() {
		
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
		
		public User SignInRequest(String username, String password) {
			
			clientManager.SignInRequest(username, password);
			
			return null;
		}
		
		// ADDING VIEW
		public void addProjectView() {
			
			ArrayList<User> friendships = clientManager.getFriendships();
			addProjectFrame = new GUIaddComponent(this, friendships, true, false, 0);
			addProjectFrame.setVisible(true);
			addProjectFrame.setBounds(700, 0, 600, 400);
			// addProjectFrame.setSize(600, 600);
		}
		
		public void addActivityView(Project project) {
			
			ArrayList<User> participants = clientManager.getParticipant(project);
			addActivityFrame = new GUIaddComponent(this, participants, false, false, 0);
			addActivityFrame.setVisible(true);
			addActivityFrame.setBounds(700, 0, 600, 500);
			// addActivityFrame.setSize(600, 600);
		}
		
		public void addFriendsView() {
			
			// DA CREARE QUERY PER FAR SI DI TROVARE TUTTI GLI UTENTI NON MIEI
			// AMICI
			// notmyfriends = clientManager.getNotmyFriends();
			addFriendsFrame = new GUIaddComponent(this, clientManager.getNotmyFriends(), true, true, 0);
			addFriendsFrame.setVisible(true);
			addFriendsFrame.setBounds(700, 0, 600, 400);
			// addFriendsFrame.setSize(600, 600);
			System.out.println("Add friends view");
			
		}
		
		@Override
		public void addFriendsView(ProjectInfo projectInfo) {
			ArrayList<User> friendsNotInProject = (ArrayList<User>) clientManager.getFriendships().clone();
			
			
//				for(User u1 : clientManager.getFriendships()){
//					
//					for(User u : projectInfo.getParticipants()){
////						if(u1.getIdUser()= u.getIdUser())
////							
////						else
////							break;
//					}
//				}
			
			for (User u : projectInfo.getParticipants()){
				friendsNotInProject.remove(u);
			}
			
			addFriendsFrame = new GUIaddComponent(this, friendsNotInProject, true, true, projectInfo.getIdProject());
			addFriendsFrame.setVisible(true);
			addFriendsFrame.setBounds(700, 0, 600, 400);
			
			
		}
		
		// ADDING TO DB
		public void addProject(String name, ArrayList<Integer> participants, boolean isActive) {
			
			if (name.isEmpty() || participants.size() <= 0) {
				String error = "";
				if (name.isEmpty())
					error += "You must define a name project \n";
				if (participants.size() <= 0)
					error += "You must define at least one participant";
					
				showError(error);
			} else {
				project = clientManager.addProject(name, clientManager.getUser().getIdUser(), isActive, participants);
				
				JOptionPane.showMessageDialog(null, "Project was added correctly");
				addProjectFrame.setVisible(false);
				addProjectFrame.dispose();
				addActivityView(project);
			}
		}
		
		public void addActivityContinue(String nameActivity, String place, Date hour, ArrayList<Integer> respActivity,
				String text) {
				
			if (nameActivity.isEmpty() || place.isEmpty() || respActivity.size() <= 0) {
				String error = "";
				if (nameActivity.isEmpty())
					error += "You must define a name activity. \n";
				if (place.isEmpty())
					error += "You must define a place for the activity. \n";
				if (respActivity.size() <= 0)
					error += "You must define at least one responsible for the activity";
					
				showError(error);
			} else {
				
				activity = clientManager.addActivity(nameActivity, project.getIdProject(), place, hour, text,
						respActivity);
						
				JOptionPane.showMessageDialog(null, "Activity was added correctly");
				addActivityFrame.clearall();
			}
		}
		
		public void addActivityFinish(String nameActivity, String place, Date hour, ArrayList<Integer> respActivity,
				String text) {
				
			if (nameActivity.isEmpty() || place.isEmpty() || respActivity.size() <= 0) {
				String error = "";
				if (nameActivity.isEmpty())
					error += "You must define a name activity. \n";
				if (place.isEmpty())
					error += "You must define a place for the activity. \n";
				if (respActivity.size() <= 0)
					error += "You must define at least one responsible for the activity";
					
				showError(error);
			} else {
				activity = clientManager.addActivity(nameActivity, project.getIdProject(), place, hour, text,
						respActivity);
						
				addActivityFrame.setVisible(false);
				addActivityFrame.dispose();
				
				if (project.isActive())
					clientManager.startProject(project);
			}
		}
		
		public ArrayList<User> addFriends(ArrayList<Integer> friends) {
			
			clientManager.addFriends(friends);
			JOptionPane.showMessageDialog(null, "Friend added correctly, please close the window");
			
			addFriendsFrame.setVisible(false);
			addFriendsFrame.dispose();
			return clientManager.getFriendships();
			
		}
		
		@Override
		public void addParticipantstoExistingProject(ArrayList<Integer> selectedItems, int i) {
			// TODO Auto-generated method stub
			clientManager.addParticipantstoExistingProject(selectedItems, i);
		}

		
		// REMOVING FROM DB
		public void removeFriend(User u) {
			
			clientManager.removeFriend(u);
			
		}
		
		@Override
		public void RemoveProjectPressed(ProjectInfo projectInfo) {
			
			clientManager.removeProject(projectInfo);
			projectInfoFrame.setVisible(false);
		}
		
		// showing info
		public void showProjectInfo(Project p) {
			
			boolean isAdmin;
			if (p.getIdAdmin() == clientManager.getId())
				isAdmin = true;
			else
				isAdmin = false;
				
			Project project = new Project(p.getIdProject(), p.getIdAdmin(), p.getName(), p.isActive());
			ProjectInfo projectInfo = clientManager.getProjectInfo(project);
			projectInfoFrame = new JFrame();
			ProjectInfoGui piGui = new ProjectInfoGui(projectInfo, this, isAdmin, clientManager.getId());
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
				
				if (activityInfoFrame != null)
					activityInfoFrame.dispose();
				if (noticeInfoFrame != null)
					noticeInfoFrame.dispose();
				if (projectInfoFrame != null)
					projectInfoFrame.dispose();
			}
		}
		
		public void closeLogout() {
			
			if (clientManager.getUser() != null) {
				clientManager.logout();
				JOptionPane.showMessageDialog(null, "Logout performed");
			}
			
			gui.setVisible(false);
			gui.dispose();
			System.out.println("GuiClient chiuso");
			
		}
		
		@Override
		public void performSignUp(String username, String password, String name, String surname) {
			
			if (username.isEmpty() || password.isEmpty() || surname.isEmpty() || name.isEmpty() || username == null
					|| password == null || surname == null || name == null)
				JOptionPane.showMessageDialog(null, "Please complete in correct way the Signup form!");
			else
				clientManager.performRegistration(username, password, name, surname);
				
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
			
			ArrayList<User> removeResponsible = clientManager.removeResponsible(resp);
			refreshAll();
			if (resp.getIdUser() == clientManager.getUser().getIdUser())
			{
				activityInfoFrame.setVisible(false);
				activityInfoFrame.dispose();
			}
			return removeResponsible;
		}
		
		@Override
		public void startProject(Project project) throws RemoteException, CustomException {
		
		}
		
		public void addText(ActivityInfo activityInfo, String text) {
			
			clientManager.addTexttoActivity(activityInfo, text);
		}
		
		@Override
		public boolean WindowWasClosed() {
			
			if (project != null) {
				if (activity != null) {
					clientManager.startProject(project);
					return true;
				}
			}
			
			return false;
		}

		@Override
		public void startProjectClicked(Project project) {
			
			clientManager.startProject(project);
		}

	
	
		
	}
	
	public void notifyPopUser(Notice notice) {
		
		JOptionPane.showMessageDialog(null, "A new notice is arrived " + notice.getTitle());
				
	}
	
	public void showError(String message) {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				
				JOptionPane.showMessageDialog(null, message + ". Please try again! ");
			}
		});
		
		System.out.println(message);
	}
	
}
