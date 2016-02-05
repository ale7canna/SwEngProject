package com.sweng.server;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.sweng.common.beans.Activity;
import com.sweng.common.beans.ActivityInfo;
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

public class GuiManager{
	
	private static ServerGUI GUI;
	ProjectInfoGui projectInfoGui;

	
	public GuiManager()
	{
		GUI = new ServerGUI(new GuiListener());
		GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GUI.setSize(800, 600);
		GUI.setVisible(true);
	
	}
	
	public void LoadUser(ArrayList<User> users){
		GUI.AddUsersToList(users);		
	}
	
	public void LoadProjects(ArrayList<Project> projects, int activeProjects, int totalProjects)
	{
		GUI.AddProjectsToList(projects);
		GUI.ChangeProjectsSummary(activeProjects, totalProjects);
	}
	
	public void LoadActivities(ArrayList<Activity> activities, int attivitaCompletate, int attivitaTotali)
	{
		GUI.AddActivitiesToList(activities);
		GUI.ChangeActivitiesSummary(attivitaCompletate, attivitaTotali);
	}
	
	public void refreshInfo()
	{
		
	}
	
	class GuiListener implements GUIListener, ICommonGui
	{

		@Override
		public void UserClicked(User user) {
			System.out.println(user.getName());
			ArrayList<Project> progetti = null;
			
			try {
				progetti = DBManager.getProjectsFromUser(user);
				GUI.AddUserProjectsToList(progetti);
			}
			catch (CustomException e) {
				GUI.AddUserProjectsToList(null);
			}
			
			try {
				ArrayList<User> friends = DBManager.getFriendsFromUser(user);
				GUI.AddFriendsToList(friends);
			}
			catch (CustomException e) {
				GUI.AddFriendsToList(null);
			}
		}

		@Override
		public void UserProjectClicked(Project project) {
						
			if (project != null)
			{	System.out.println(project.getName());
			
				try {
					projectInfoGui = new ProjectInfoGui(DBManager.getProjectInfo(project), this, true);
					JFrame frame = new JFrame();
					frame.getContentPane().add(projectInfoGui);
					frame.setVisible(true);
					frame.setSize(800, 600);
//					LoadProjects(null);
				}
				catch (CustomException e)
				{}
			}
		}

		@Override
		public void RemoveProjectPressed(ProjectInfo projectInfo) {
			Project p = new Project(projectInfo.getIdProject(), projectInfo.getAdmin().getIdUser(), projectInfo.getName(), projectInfo.isActive());
			try {
				DBManager.removeProject(p);
				projectInfoGui.setVisible(false);
//				projectInfoGui.dispose();
				
			}
			catch (CustomException e )
			{
				
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
			// TODO AGGIORNARE DATI
			
		}

		@Override
		public void setNoticeRead(Notice notice) {
			
		}

		@Override
		public ArrayList<User> removeParticipant(Participant part) {
			return null;
		}

	}


}
