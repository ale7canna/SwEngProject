package com.sweng.server;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.sweng.common.IServer;
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
	private IServer server;
	private ProjectInfoGui projectInfoGui;
	
	
	public GuiManager(IServer _server) {
		GUI = new ServerGUI(new GuiListener());
		GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GUI.setSize(1400, 800);
		GUI.setVisible(true);
		
		server = _server;
		
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
			try {
				server.setActivityDone(activityInfo, null);
				LoadActivities();
			} catch (RemoteException | CustomException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
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
			try {
				return server.removeParticipant(part);
			} catch (RemoteException | CustomException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			
		}
		
		@Override
		public void showActivityInfo(Activity a) {
			if (a != null) {
				System.out.println(a.getName());
				
				try {
					ActivityInfoGui activityInfoGui = new ActivityInfoGui(server.getActivityInfo(a), this);
					JFrame frame = new JFrame();
					frame.getContentPane().add(activityInfoGui);
					frame.setVisible(true);
					frame.setSize(800, 600);
				} catch (CustomException | RemoteException e) {
				}
			}
		}
		
		@Override
		public ArrayList<User> removeResponsible(ActivityResponsible resp) {

			try {
				return server.removeActivityResponsible(resp);
			} catch (RemoteException | CustomException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		
		@Override
		public void startProject(Project project) throws RemoteException, CustomException {

			server.startProject(project);
			
		}
		
		@Override
		public void addText(ActivityInfo activityInfo, String text) {
			activityInfo.setText(text);
			try {
				server.addTexttoActivity(activityInfo);
			} catch (RemoteException | CustomException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
