package com.sweng.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.util.ArrayList;

import com.sweng.client.gui.ClientGUI;
import com.sweng.client.gui.GUIPanelHome;
import com.sweng.common.IClient;
import com.sweng.common.IServer;
import com.sweng.common.beans.Activity;
import com.sweng.common.beans.ActivityResponsible;
import com.sweng.common.beans.Friendship;
import com.sweng.common.beans.Participant;
import com.sweng.common.beans.Project;
import com.sweng.common.beans.ProjectInfo;
import com.sweng.common.beans.User;
import com.sweng.common.utils.CustomException;

public class Client extends UnicastRemoteObject implements IClient, IClientManager {

	private static IServer server = null;
	private static GuiManagerClient guiManagerClient = null;
	User user = null;
	ArrayList<User> friendships = null;
	ArrayList<Activity> activities = null;
	ArrayList<Project> projects = null;
	ArrayList<User> participants = null;
	ArrayList<User> notmyFriends = null;
	ArrayList<User> responsible=null;

	protected Client(IServer server) throws RemoteException {
		super();

		this.server = server;
		guiManagerClient = new GuiManagerClient(this);

	}

	public void SignInRequest(String username, String password) {
		try {
			user = server.performLogin(username, password);
		} catch (CustomException | RemoteException e) {
			if (e instanceof CustomException)
				System.out.println(e.getMessage());
			else
				e.printStackTrace();
		}
		loadFriendsfromServer(user);
		loadActivitiesfromServer(user);
		loadProjectsfromServer(user);
		
		guiManagerClient.goToUserHomePage();
	}

	public ArrayList<User> loadFriendsfromServer(User user){
		try{
			friendships = server.getFriendsFromUser(user);
		} catch (CustomException | RemoteException e) {
			if (e instanceof CustomException)
				System.out.println(e.getMessage());
			else
				e.printStackTrace();
		}
		return friendships;
	}
	
	public ArrayList<Activity> loadActivitiesfromServer(User user){
		try {			
			activities = server.getActivityFromUser(user);
		} catch (CustomException | RemoteException e) {
			if (e instanceof CustomException)
				System.out.println(e.getMessage());
			else
				e.printStackTrace();
		}
		return activities;
	}
	
	public ArrayList<Project> loadProjectsfromServer(User user){
		try{
			projects = server.getProjectsFromUsers(user);
			//participants = server.getParticipantsFromProject(project);
		} catch (CustomException | RemoteException e) {
			if (e instanceof CustomException)
				System.out.println(e.getMessage());
			else
				e.printStackTrace();
		}
		return projects;
	}
	
	public ArrayList<User> getNotmyFriends(){
		try {
			notmyFriends=server.getNotMyFriends(user.getIdUser());
		} catch (RemoteException | CustomException e) {
			
			e.printStackTrace();
		}
		return notmyFriends;
	}
	
	public ArrayList<User> getFriendships() {
		return friendships;
	}

	public ArrayList<Activity> getActivity() {
		return activities;
	}
	
	public ArrayList<Project> getProject() {
		return projects;
	}

	public ArrayList<User> getParticipant(Project project) {
		try{
			participants = server.getParticipantsFromProject(project);
		} catch (CustomException | RemoteException e) {
			if (e instanceof CustomException)
				System.out.println(e.getMessage());
			else
				e.printStackTrace();
		}
		return participants;
	}

	public ProjectInfo getProjectInfo(Project project){
		ProjectInfo res=null;
		try {
			res= server.getProjectInfo(project);
		} catch (RemoteException | CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	// ADD METHODS
	public Project addProject(String nameProject, int idAdmin, boolean isActive) {
		Project _project = new Project(idAdmin, nameProject, isActive);

		try {
			_project = server.addProject(_project);

		} catch (RemoteException | CustomException e) {
			e.printStackTrace();
		}
		loadProjectsfromServer(user);
		return _project;
	}

	public void addParticipants(ArrayList<Integer> participants, int idProject) {

		Participant _participant = null;
		for (int i : participants) {
			try {
				_participant = new Participant(i, idProject);
				server.addParticipant(_participant);

			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	public Activity addActivity(String nameActivity, int idProject, String place, java.util.Date hour) {

		Activity _activity = new Activity(idProject, nameActivity, place, hour, false);

		try {
			_activity = server.addActivity(_activity);
		} catch (RemoteException | CustomException e) {
			e.printStackTrace();
		}
		loadActivitiesfromServer(user);
		return _activity;
	}

	public void addRespActivity(int idActivity, ArrayList<Integer> responsibles) {
		for (int i : responsibles) {
			ActivityResponsible _activityResponsible = new ActivityResponsible(i, idActivity);
			try {
				server.addActivityResponsible(_activityResponsible);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	public void addFriends(ArrayList<Integer> friends) {
		Friendship _friendship = null;
		int idUser = user.getIdUser();
		for (Integer i : friends) {
			// IO SONO SUO AMICO idUser->i IL CONTRARIO NO
			_friendship = new Friendship(idUser, i);
			try {
				server.addFriendship(_friendship);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		loadFriendsfromServer(user);
	}

	public void update() {
		// TODO Auto-generated method stub

	}

	public void signUp() {

	}

	public void signIn() {

	}
	
	@Override
	public User getUser() {
		return user;
	}



	
}
