package com.sweng;

import static org.junit.Assert.*;

import java.rmi.RemoteException;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import com.sweng.common.IServer;
import com.sweng.common.beans.Activity;
import com.sweng.common.beans.ActivityResponsible;
import com.sweng.common.beans.Participant;
import com.sweng.common.beans.Project;
import com.sweng.common.beans.User;
import com.sweng.common.utils.CustomException;
import com.sweng.server.Server;


public class AddProjectTest {
	
	IServer server;
	ArrayList<Participant> participants;
	ArrayList<Activity> activities;
	HashMap<Integer, Integer> activitiesAndResponsibles;
		
	@Test
	public void test() throws RemoteException, CustomException {
		
		server = Server.getInstance(null);
		
		aggiungiUtenti();
		
		Project p = new Project(1, "Nome_Progetto", true);
		Project addedProject = server.addProject(p);
		
		aggiungiPartecipanti(addedProject.getIdProject());
		aggiungiAttivita(addedProject.getIdProject());
		
		
		ArrayList<User> participantsFromProject = server.getParticipantsFromProject(addedProject);
		for (User u : participantsFromProject)
		{
			Participant participant = new Participant(u.getIdUser(), addedProject.getIdProject());
			assertTrue(participants.contains(participant));
			//			if (participants.contains(participant))
//				assertTrue(true);
//			else
//				assertTrue(false);
		}		
		assertEquals(p.getName(), addedProject.getName());

		HashMap<Activity, User> map = server.getProjectInfo(addedProject).getActivitiesInResponsible();
		for (Activity act : map.keySet())
		{
			assertTrue(activitiesAndResponsibles.containsKey(act.getIdActivity()));
		}
		
				
		
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
		
		activities = new ArrayList<>();
		activitiesAndResponsibles = new HashMap<>();
		
		Activity act = new Activity(idProject, "Attivita1", "Posto1", Date.from(Instant.now()), false, "");
		Activity addedActivity = server.addActivity(act);
		aggiungiResponsabili(addedActivity.getIdActivity());
		activities.add(act);
		
		act = new Activity(idProject, "Attivita2", "Posto2", Date.from(Instant.now()), false, "");
		addedActivity = server.addActivity(act);
		aggiungiResponsabili(addedActivity.getIdActivity());
		activities.add(act);
		
		act = new Activity(idProject, "Attivita3", "Posto3", Date.from(Instant.now()), false, "");
		addedActivity = server.addActivity(act);
		aggiungiResponsabili(addedActivity.getIdActivity());
		activities.add(act);
		
		act = new Activity(idProject, "Attivita4", "Posto4", Date.from(Instant.now()), false, "");
		addedActivity = server.addActivity(act);
		aggiungiResponsabili(addedActivity.getIdActivity());
		activities.add(act);
	}

	private void aggiungiResponsabili(int idActivity) throws RemoteException, CustomException
	{	
		ActivityResponsible activityResponsible = new ActivityResponsible(1, idActivity);
		server.addActivityResponsible(activityResponsible);
		activitiesAndResponsibles.put(new Integer(idActivity), new Integer(1));
		
		activityResponsible = new ActivityResponsible(2, idActivity);
		server.addActivityResponsible(activityResponsible);
		activitiesAndResponsibles.put(new Integer(idActivity), new Integer(2));
	}
	
	private void aggiungiPartecipanti(int idProject) throws RemoteException, CustomException
	{
		participants = new ArrayList<>();
		
		Participant participant = new Participant(1, idProject);
		server.addParticipant(participant);
		participants.add(participant);
		
		participant = new Participant(2, idProject);
		server.addParticipant(participant);
		participants.add(participant);
		
		participant = new Participant(3, idProject);
		server.addParticipant(participant);
		participants.add(participant);
		
		participant = new Participant(4, idProject);
		server.addParticipant(participant);
		participants.add(participant);
		
		participant = new Participant(5, idProject);
		server.addParticipant(participant);
		participants.add(participant);
		
	}
	
}
