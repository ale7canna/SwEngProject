package com.sweng;

import java.rmi.RemoteException;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.junit.Test;
import static org.junit.Assert.*;

import com.sweng.common.IServer;
import com.sweng.common.beans.Activity;
import com.sweng.common.beans.ActivityInfo;
import com.sweng.common.beans.ActivityResponsible;
import com.sweng.common.beans.Participant;
import com.sweng.common.beans.Project;
import com.sweng.common.beans.ProjectInfo;
import com.sweng.common.beans.User;
import com.sweng.common.utils.CustomException;
import com.sweng.server.Server;

public class CompleteProjectTest {
	
	IServer server;
	
	@Test
	public void test() {
		
		try {
			server = Server.getInstance(null);
			try {
			aggiungiUtenti();
			}
			catch (Exception e) {}
			Project project = aggiungiProgetto();
			server.startProject(project);
			
			ProjectInfo projectInfo = server.getProjectInfo(project);
			
			HashMap<Activity, ArrayList<User>> activitiesInResponsible = projectInfo.getActivitiesInResponsible();
			Set<Activity> activities = activitiesInResponsible.keySet();
			
			int activitiesCount = activities.size();
			while (activitiesCount > 0)
			{
				projectInfo = server.getProjectInfo(project);
				activitiesInResponsible = projectInfo.getActivitiesInResponsible();
				activities = activitiesInResponsible.keySet();
				
				for (Activity act : activities)
				{
					if (act.isFinishable() && !act.getIsDone())
					{
						ActivityInfo activityInfo = server.getActivityInfo(act);
						server.setActivityDone(activityInfo, activitiesInResponsible.get(act).get(0));
						break;
					}
				}
				activitiesCount --;
				
			}
			projectInfo = server.getProjectInfo(project);
			
			assertEquals((int)(projectInfo.getCompletetionPercentage() * 100), 100);
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (CustomException e) {
			e.printStackTrace();
		}
		
	}
	
	private Project aggiungiProgetto() throws RemoteException, CustomException {
		
		Project p = new Project(1, "Nome_Progetto", true);
		Project addedProject = server.addProject(p);
		
		aggiungiPartecipanti(addedProject.getIdProject());
		aggiungiAttivita(addedProject.getIdProject());
		
		return addedProject;
		
	}
	
	private void aggiungiUtenti() throws RemoteException, CustomException {
		
		User u = new User("Utente1", "U1", "user1", "123");
		server.addUser(u);
		
		u = new User("Utente2", "U2", "user2", "123");
		server.addUser(u);
		
		u = new User("Utente3", "U3", "user3", "123");
		server.addUser(u);
		
		u = new User("Utente4", "U4", "user4", "123");
		server.addUser(u);
		
		u = new User("Utente5", "U5", "user5", "123");
		server.addUser(u);
		
	}
	
	private void aggiungiAttivita(int idProject) throws RemoteException, CustomException {
		
		Activity act = new Activity(idProject, "Attivita1", "Posto1", Date.from(Instant.now()), false, "");
		Activity addedActivity = server.addActivity(act);
		aggiungiResponsabili(addedActivity.getIdActivity());
		
		act = new Activity(idProject, "Attivita2", "Posto2", Date.from(Instant.now()), false, "");
		addedActivity = server.addActivity(act);
		aggiungiResponsabili(addedActivity.getIdActivity());
		
		act = new Activity(idProject, "Attivita3", "Posto3", Date.from(Instant.now()), false, "");
		addedActivity = server.addActivity(act);
		aggiungiResponsabili(addedActivity.getIdActivity());
		
		act = new Activity(idProject, "Attivita4", "Posto4", Date.from(Instant.now()), false, "");
		addedActivity = server.addActivity(act);
		aggiungiResponsabili(addedActivity.getIdActivity());
	}
	
	private void aggiungiResponsabili(int idActivity) throws RemoteException, CustomException {
		
		ActivityResponsible activityResponsible = new ActivityResponsible(1, idActivity);
		server.addActivityResponsible(activityResponsible);
		
		activityResponsible = new ActivityResponsible(2, idActivity);
		server.addActivityResponsible(activityResponsible);
	}
	
	private void aggiungiPartecipanti(int idProject) throws RemoteException, CustomException {
		
		Participant participant = new Participant(1, idProject);
		server.addParticipant(participant);
		
		participant = new Participant(2, idProject);
		server.addParticipant(participant);
		
		participant = new Participant(3, idProject);
		server.addParticipant(participant);
		
		participant = new Participant(4, idProject);
		server.addParticipant(participant);
		
		participant = new Participant(5, idProject);
		server.addParticipant(participant);
		
	}
	
}
