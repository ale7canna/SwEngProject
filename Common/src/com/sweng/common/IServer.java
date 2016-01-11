package com.sweng.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.sweng.common.beans.Activity;
import com.sweng.common.beans.Project;
import com.sweng.common.beans.User;

public interface IServer extends Remote{

	//REMOTE METHODS
	public void addProject(Project _project) throws RemoteException;
	public void addUser(User _user) throws RemoteException;
	public void addActivity(Activity _activity) throws RemoteException;
	
	//OBSERVER PATTERN
	public void addObserver(IClient _client) throws RemoteException;
	public void removeObserver(IClient _client) throws RemoteException;
	public void notifyObservers() throws RemoteException;
}
