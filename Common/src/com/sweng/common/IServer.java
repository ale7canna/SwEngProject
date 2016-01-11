package com.sweng.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.sweng.common.beans.Activity;
import com.sweng.common.beans.ActivityResponsible;
import com.sweng.common.beans.Friendship;
import com.sweng.common.beans.Participant;
import com.sweng.common.beans.Project;
import com.sweng.common.beans.User;
import com.sweng.common.notice.Notice;

public interface IServer extends Remote{

	//REMOTE METHODS
	public void addActivity(Activity _activity) throws RemoteException;
	public void addActivityResponsible(ActivityResponsible _activityResponsible) throws RemoteException;
	public void addFriendship(Friendship _friendship) throws RemoteException;
	public void addParticipant(Participant _participant) throws RemoteException; 
	public void addProject(Project _project) throws RemoteException;
	public void addUser(User _user) throws RemoteException;
	
	//OBSERVER PATTERN
	public void addObserver(IClient _client) throws RemoteException;
	public void removeObserver(IClient _client) throws RemoteException;
	public void notifyObservers(Notice _notice) throws RemoteException;
}
