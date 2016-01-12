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
import com.sweng.common.beans.User;
import com.sweng.common.notice.Notice;
import com.sweng.common.utils.CustomException;

public class Server extends UnicastRemoteObject implements IServer{

	
	
	protected Server() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addActivity(Activity _activity) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addActivityResponsible(ActivityResponsible _activityResponsible) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addFriendship(Friendship _friendship) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addParticipant(Participant _participant) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addProject(Project _project) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addUser(User _user) throws RemoteException {
		// TODO Auto-generated method stub
		
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

}
