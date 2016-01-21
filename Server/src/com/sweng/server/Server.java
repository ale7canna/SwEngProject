package com.sweng.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sweng.common.IClient;
import com.sweng.common.IServer;
import com.sweng.common.beans.Activity;
import com.sweng.common.beans.ActivityResponsible;
import com.sweng.common.beans.Friendship;
import com.sweng.common.beans.Participant;
import com.sweng.common.beans.Project;
import com.sweng.common.beans.ProjectInfo;
import com.sweng.common.beans.User;
import com.sweng.common.notice.Notice;
import com.sweng.common.utils.CustomException;

public class Server extends UnicastRemoteObject implements IServer{

	
	
	protected Server() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Activity addActivity(Activity _activity) throws RemoteException, CustomException {
		Activity result = null;
		DBManager.addActivity(_activity);
		result = DBManager.getActivityFromNameAndProject(_activity.getName(), _activity.getIdProject());
		
		return result ;
	}

	@Override
	public void addActivityResponsible(ActivityResponsible _activityResponsible) throws RemoteException {
		try {
			DBManager.addActivityResponsible(_activityResponsible);
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void addFriendship(Friendship _friendship) throws RemoteException {
		try {
			DBManager.addFriendship(_friendship);
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void addParticipant(Participant _participant) throws RemoteException {
		try {
			DBManager.addParticipant(_participant);
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public Project addProject(Project _project) throws RemoteException, CustomException {
		
		Project result = null;
		DBManager.addProject(_project);
		result = DBManager.getProjectFromNameAndAdmin(_project.getName(), _project.getIdAdmin());
		
		return result;
	}

	@Override
	public void addUser(User _user) throws RemoteException {
		try {
			DBManager.addUser(_user);
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public User performLogin(String username, String password) throws RemoteException, CustomException {
		User result = null;
		result = DBManager.getUser(username, password);
		
		return result;
	}

	@Override
	public void addObserver(IClient _client) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeObserver(IClient _client) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyObservers(Notice _notice) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Activity> getActivityFromUser(User user) throws RemoteException, CustomException {
		ArrayList<Activity> result = null;
		result = DBManager.getActivityFromUser(user);		
		
		return result;
	}

	@Override
	public ArrayList<Project> getProjectsFromUsers(User user) throws RemoteException, CustomException {
		ArrayList<Project> result = null;
		result = DBManager.getProjectsFromUser(user);		
		
		return result;
	}

	@Override
	public ArrayList<User> getFriendsFromUser(User user) throws RemoteException, CustomException {
		ArrayList<User> result = null;
		result = DBManager.getFriendsFromUser(user);

		return result;
	}

	@Override
	public ArrayList<User> getParticipantsFromProject(Project project) throws CustomException {
		ArrayList<User> result = null;
		result = DBManager.getParticipantsFromProject(project);
		
		return result;
	}

	@Override
	public ProjectInfo getProjectInfo(Project project) throws CustomException {
		ProjectInfo result = null;
		result = DBManager.getProjectInfo(project);
		
		return result;
	}

	@Override
	public ArrayList<User> getNotMyFriends(int idUser) throws CustomException,RemoteException {
		
		ArrayList<User> result = null;
		result = DBManager.getNotMyFriends(idUser);
		
		return result;
	}

	@Override
	public void removeProject(Project project) throws RemoteException, CustomException {
		DBManager.removeProject(project);
		
	}

	
	
}
