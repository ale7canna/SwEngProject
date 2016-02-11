package com.sweng.server;

import java.awt.List;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.sweng.common.beans.Activity;
import com.sweng.common.beans.ActivityInfo;
import com.sweng.common.beans.ActivityResponsible;
import com.sweng.common.beans.Participant;
import com.sweng.common.beans.Project;
import com.sweng.common.beans.ProjectInfo;
import com.sweng.common.beans.User;
import com.sweng.common.gui.ActivityInfoGui;
import com.sweng.common.gui.ICommonGui;
import com.sweng.common.gui.ProjectInfoGui;
import com.sweng.common.notice.Notice;
import com.sweng.common.utils.CustomException;
import com.sweng.server.gui.GUIListener;
import com.sweng.server.gui.ServerGUI;

public class GuiManager {
	
	private static ServerGUI GUI;
	ProjectInfoGui projectInfoGui;
	
	public GuiManager() {
		GUI = new ServerGUI(new GuiListener());
		GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GUI.setSize(1400, 800);
		GUI.setVisible(true);
		
	}
	
	public void LoadUser() {
		
		try {
			ArrayList<User> allUsers = DBManager.getAllUsers();
			GUI.AddUsersToList(allUsers);
			GUI.ChangeUserSummary(DBManager.getObservers().size(), allUsers.size());
		} catch (CustomException e) {
			showError(e.getMessage());
		}
		
	}
	
	public void LoadProjects() {
		
		try {
			GUI.AddProjectsToList(DBManager.getAllProjects());
			GUI.ChangeProjectsSummary(DBManager.getActiveProjectsCount(), DBManager.getTotalProjectsCount());
		} catch (CustomException e) {
			showError(e.getMessage());
		}
	}
	
	public void LoadActivities() {
		
		try {
			GUI.AddActivitiesToList(DBManager.getAllActivities());
			GUI.ChangeActivitiesSummary(DBManager.getDoneActivitiesCount(), DBManager.getActivitiesCount());
		} catch (CustomException e) {
			showError(e.getMessage());
		}
	}
	
	public void refreshInfo() {
	
	}
	
	class GuiListener implements GUIListener, ICommonGui {
		
		@Override
		public void UserClicked(User user) {
			
			System.out.println(user.getName());
			ArrayList<Project> progetti = null;
			
			try {
				progetti = DBManager.getProjectsFromUser(user);
				GUI.AddUserProjectsToList(progetti);
			} catch (CustomException e) {
				GUI.AddUserProjectsToList(null);
			}
			
			try {
				ArrayList<User> friends = DBManager.getFriendsFromUser(user);
				GUI.AddFriendsToList(friends);
			} catch (CustomException e) {
				GUI.AddFriendsToList(null);
			}
		}
		
		@Override
		public void UserProjectClicked(Project project) {
			
			if (project != null) {
				System.out.println(project.getName());
				
				try {
					projectInfoGui = new ProjectInfoGui(DBManager.getProjectInfo(project), this, true);
					JFrame frame = new JFrame();
					frame.getContentPane().add(projectInfoGui);
					frame.setVisible(true);
					frame.setSize(800, 600);
					// LoadProjects(null);
				} catch (CustomException e) {
				}
			}
		}
		
		@Override
		public void RemoveProjectPressed(ProjectInfo projectInfo) {
			
			Project p = new Project(projectInfo.getIdProject(), projectInfo.getAdmin().getIdUser(),
					projectInfo.getName(), projectInfo.isActive());
			try {
				DBManager.removeProject(p);
				GUI.ChangeProjectInfo(null);
			} catch (CustomException e) {
			
			}
		}
		
		@Override
		public void ProjectClicked(Project project) {
			
			ProjectInfo pi = null;
			try {
				pi = DBManager.getProjectInfo(project);
			} catch (CustomException e) {
				JOptionPane.showMessageDialog(null, "AHIA " + e.getMessage());
			}
			ProjectInfoGui projectInfo = new ProjectInfoGui(pi, this, true);
			GUI.ChangeProjectInfo(projectInfo);
		}
		
		@Override
		public void ActivityClicked(Activity activity) {
			
			ActivityInfo ai = null;
			
			try {
				ai = DBManager.getActivityInfo(activity);
			} catch (CustomException e) {
				JOptionPane.showMessageDialog(null, "AHIA " + e.getMessage());
			}
			ActivityInfoGui activityInfoGui = new ActivityInfoGui(ai, this);
			GUI.ChangeActivityInfo(activityInfoGui);
		}
		
		@Override
		public void completeActivity(ActivityInfo activityInfo) {
			// TODO COMPLETARE ATTIVITA
			
		}
		
		@Override
		public void refreshAll() {
			
			LoadActivities();
			LoadProjects();
			LoadUser();
		}
		
		@Override
		public void setNoticeRead(Notice notice) {
		
		}
		
		@Override
		public ArrayList<User> removeParticipant(Participant part) {
			
			return null;
		}
		
		@Override
		public void showActivityInfo(Activity a) {
		
		}
		
		@Override
		public ArrayList<User> removeResponsible(ActivityResponsible resp) {
			
			return null;
		}
		
		@Override
		public void startProject(Project project) throws RemoteException, CustomException {
			
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void addText(ActivityInfo activityInfo, String text) {
			
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private void showError(String message) {
		
		if (!SwingUtilities.isEventDispatchThread()) {
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					
					JOptionPane.showMessageDialog(null, message + ". Please try again! ");
				}
			});
		}
		
		System.out.println(message);
	}
}
