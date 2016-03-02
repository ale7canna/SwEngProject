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
	private Server server;
	private ProjectInfoGui projectInfoGui;
	
	
	public GuiManager(Server _server) {
		GUI = new ServerGUI(new GuiListener());
		GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GUI.setSize(1400, 800);
		GUI.setVisible(true);
		
		server = _server;
		
	}
	
	public void LoadUser() {
		
		try {
			ArrayList<User> allUsers = server.getAllUsers(); 
			GUI.AddUsersToList(allUsers);
			GUI.ChangeUserSummary(server.getObservers().size(), allUsers.size());
		} catch (CustomException e) {
			showError(e.getMessage());
		}
		
	}
	
	public void LoadProjects() {
		
		try {
			ArrayList<Project> allProjects = server.getAllProjects();
			GUI.AddProjectsToList(allProjects);
			GUI.ChangeProjectsSummary(server.getActiveProjectsCount(), allProjects.size());
		} catch (CustomException e) {
			showError(e.getMessage());
		}
	}
	
	public void LoadActivities() {
		
		try {
			ArrayList<Activity> allActivities = server.getAllActivities();
			GUI.AddActivitiesToList(allActivities);
			GUI.ChangeActivitiesSummary(server.getDoneActivitiesCount(), allActivities.size());
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
				progetti = server.getProjectsFromUsers(user);
				GUI.AddUserProjectsToList(progetti);
			} catch (CustomException | RemoteException e) {
				GUI.AddUserProjectsToList(null);
			}
			
			try {
				ArrayList<User> friends = server.getFriendsFromUser(user);
				GUI.AddFriendsToList(friends);
			} catch (CustomException | RemoteException e) {
				GUI.AddFriendsToList(null);
			}
		}
		
		@Override
		public void UserProjectClicked(Project project) {
			
			if (project != null) {
				System.out.println(project.getName());
				
				try {
					projectInfoGui = new ProjectInfoGui(server.getProjectInfo(project), this, false, 0);
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
					projectInfo.getName(), projectInfo.isActive(), projectInfo.isComplete());
			try {
				server.removeProject(p);
				GUI.ChangeProjectInfo(null);
			} catch (CustomException | RemoteException e) {
			
			}
		}
		
		@Override
		public void ProjectClicked(Project project) {
			
			ProjectInfo pi = null;
			try {
				pi = server.getProjectInfo(project);
			} catch (CustomException e) {
				JOptionPane.showMessageDialog(null, "AHIA " + e.getMessage());
			}
			ProjectInfoGui projectInfo = new ProjectInfoGui(pi, this, false, 0);
			GUI.ChangeProjectInfo(projectInfo);
		}
		
		@Override
		public void ActivityClicked(Activity activity) {
			
			ActivityInfo ai = null;
			
			try {
				ai = server.getActivityInfo(activity);
			} catch (CustomException | RemoteException e) {
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

				if(e instanceof CustomException)
					showError(e.getMessage());
				else
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
				if(e instanceof CustomException)
					showError(e.getMessage());
				else
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
				if(e instanceof CustomException)
					showError(e.getMessage());
				else
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
				if(e instanceof CustomException)
					showError(e.getMessage());
				else
					e.printStackTrace();
			}
		}

		@Override
		public void startProjectClicked(Project project) {
			
		}

		@Override
		public void addActivityView(Project project) {
			
			// TODO Auto-generated method stub
			
		}

		@Override
		public void addFriendsView(ProjectInfo projectInfo) {
			
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private void showError(String message) {
		
		if (!SwingUtilities.isEventDispatchThread()) {
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					
					JOptionPane.showMessageDialog(null, message);
				}
			});
		}
		
		System.out.println(message);
	}
}
