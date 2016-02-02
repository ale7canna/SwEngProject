package com.sweng.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.sweng.client.gui.ClientGUI;
import com.sweng.client.gui.GUIPanelHome;
import com.sweng.common.IClient;
import com.sweng.common.IServer;
import com.sweng.common.beans.Activity;
import com.sweng.common.beans.ActivityInfo;
import com.sweng.common.beans.ActivityResponsible;
import com.sweng.common.beans.Friendship;
import com.sweng.common.beans.Participant;
import com.sweng.common.beans.Project;
import com.sweng.common.beans.ProjectInfo;
import com.sweng.common.beans.User;
import com.sweng.common.notice.Notice;
import com.sweng.common.utils.CustomException;
import com.sweng.common.utils.Errors;

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
	ArrayList<Notice> notices = null;

	protected Client(IServer server) throws RemoteException {
		super();

		this.server = server;
		guiManagerClient = new GuiManagerClient(this);

	}

	public void SignInRequest(String username, String password) {
		try {
			user = server.performLogin(username, password);
			server.addObserver(this);
			loadFriendsfromServer(user);
			loadActivitiesfromServer(user);
			loadProjectsfromServer(user);
			loadNotice(user);
			
			guiManagerClient.goToUserHomePage();
			
		} catch (CustomException | RemoteException e) {
			if (e instanceof CustomException){
				JOptionPane.showMessageDialog(null, e.getMessage());
				System.out.println(e.getMessage());
			}
			else
				e.printStackTrace();
		}
		
	
	}

	
	//LOADING FROM DB
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
	
	public ArrayList<Notice> loadNotice(User user) {
		try{
			notices = server.getNoticeFromUser(user);
		}catch (RemoteException|CustomException e){
			e.printStackTrace();
		}
		return notices;
	}
	
	//GETTER FOR GUIMANAGER
	public ArrayList<User> getFriendships() {
		return friendships;
	}

	public ArrayList<Activity> getActivity() {
		return activities;
	}
	
	public ArrayList<Project> getProject() {
		return projects;
	}

	public ArrayList<Notice> getNotices()
	{
		return notices;
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
			// TODO Gestire Eccezione
			if (e instanceof CustomException)
				guiManagerClient.showError(e.getMessage()+ ". May the Server had problems loading the Project Info.");
			e.printStackTrace();
		}
		return res;
	}
	
	public ActivityInfo getActivityInfo(Activity activity){
		ActivityInfo res = null;
		try{
			res = server.getActivityInfo(activity);
		}catch (RemoteException | CustomException e){
			e.printStackTrace();
		}
		return res;
	}
	
	public ArrayList<Notice> getNotice(User user){
		return notices;
	}
	
	// ADD METHODS
 	public Project addProject(String nameProject, int idAdmin, boolean isActive) {
		Project _project = new Project(idAdmin, nameProject, isActive);
		
		try {
			_project = server.addProject(_project);
			Participant p = new Participant(user.getIdUser(), _project.getIdProject());
			server.addParticipant(p);

		} catch (RemoteException | CustomException e) {
			e.printStackTrace();
		}
		projects = loadProjectsfromServer(user);
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
		projects = loadProjectsfromServer(user);
		
	}

	public Activity addActivity(String nameActivity, int idProject, String place, java.util.Date hour, boolean isLast) {

		Activity _activity = new Activity(idProject, nameActivity, place, hour, false);

		try {
			_activity = server.addActivity(_activity, isLast);
		} catch (RemoteException | CustomException e) {
			e.printStackTrace();
		}
		
		activities = loadActivitiesfromServer(user);
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
		activities = loadActivitiesfromServer(user);
		
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
		friendships = loadFriendsfromServer(user);
		
	}
	
	@Override
	public User getUser() {
		return user;
	}

	//REMOVE 
	
	public void removeFriend(User u){
		Friendship friendship = new Friendship(user.getIdUser(), u.getIdUser());
		try {
			server.removeFriendship(friendship);
		} catch (RemoteException | CustomException e) {
			// TODO Gestire Eccezione
			if(e instanceof CustomException)
				guiManagerClient.showError(e.getMessage());
			e.printStackTrace();
		}
		friendships = loadFriendsfromServer(user);
	}

	public void removeProject(ProjectInfo p){
		
		try {
			Project proj = new Project(p.getIdProject(), p.getAdmin().getIdUser(), p.getName(), p.isActive());
			server.removeProject(proj);
		} catch (RemoteException | CustomException e) {
			// TODO Auto-generated catch block
			if(e instanceof CustomException)
				guiManagerClient.showError(e.getMessage());
			e.printStackTrace();
		}
		
		projects = loadProjectsfromServer(user);
	}
	//SET BEANS
	public void setActivityInfoDone(ActivityInfo activityInfo){
		
		try {
			server.setActivityDone(activityInfo);
		} catch (RemoteException | CustomException e) {
			// TODO Auto-generated catch block
			if (e instanceof CustomException){

				guiManagerClient.showError(e.getMessage()+ ". Error while loading of Activity Info.");
				}
			e.printStackTrace();
		}
		activities = loadActivitiesfromServer(user);
	}

	public void logout(){
		try {
			server.removeObserver(this);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			
			guiManagerClient.showError(e.getMessage());
			e.printStackTrace();
		}
		
		if(friendships!=null)
			friendships.clear();
		if(activities!=null)
			activities.clear();
		if(notmyFriends!=null)	
			notmyFriends.clear();
		if(participants!=null)
			participants.clear();
		if(projects!=null)
			projects.clear();
		if(responsible!=null)
			responsible.clear();
		user = null;
	
		
	}

	@Override
	public int getId() {
		if(user!=null)
			return user.getIdUser();
		else
			return -1;
	}

	@Override
	public String getUsername() {
		if(user!=null)
			return user.getUsername();
		else
			return null;
	}

	@Override
	public void sendMessage(String message) throws RemoteException {
		guiManagerClient.showMessage(message);
	}

	@Override
	public void performRegistration(String username, String password, String name, String surname){
		if(user==null){
			user = new User(name, surname, username, password);
			try {
				server.addUser(user);
				SignInRequest(username, password);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
			
				guiManagerClient.showError(e.getMessage());
				e.printStackTrace();
			}
		}
		
		
		
		
	}

	@Override
	public void update(Notice notice) throws RemoteException {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				guiManagerClient.notifyPopUser(notice);
				notices = loadNotice(user);
			}
		});
		loadNotice(user);
		guiManagerClient.refreshHomeContent();
	}

	@Override
	public Notice getNoticeInfo(Notice n) {
		// TODO: Marzo questo metodo (messo qua) mi fa pensare che tu stia per andare al server a chiedere notice Info.
		// 			Ma tutto ciò che serve è già in notice. Dipenderà dalla classe. NON LO SO :)ERA PER CREARE LANOTICE INFO MA DIFATTI E INUTILE
		return null;
	}


}
