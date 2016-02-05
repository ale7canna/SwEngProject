package com.sweng.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

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

public interface IServer extends Remote{

	//REMOTE METHODS
	public Activity addActivity(Activity _activity) throws RemoteException, CustomException;
	public void addActivityResponsible(ActivityResponsible _activityResponsible) throws RemoteException;
	public void addFriendship(Friendship _friendship) throws RemoteException;
	public void addParticipant(Participant _participant) throws RemoteException; 
	public Project addProject(Project _project) throws RemoteException, CustomException;
	public void addUser(User _user) throws RemoteException;
	
	public void startProject(Project project) throws RemoteException, CustomException;
	
	public User performLogin(String username, String password) throws RemoteException, CustomException;
	public ArrayList<Activity> getActivityFromUser(User user) throws RemoteException, CustomException;
	public ArrayList<Project> getProjectsFromUsers(User user) throws RemoteException, CustomException;
	public ArrayList<User> getFriendsFromUser(User user) throws RemoteException, CustomException;
	public ArrayList<User> getParticipantsFromProject(Project project) throws RemoteException, CustomException;
	public ProjectInfo getProjectInfo(Project project) throws RemoteException, CustomException;
	public ActivityInfo getActivityInfo(Activity activity) throws RemoteException, CustomException;
	public ArrayList<User> getNotMyFriends(int idUser) throws CustomException, RemoteException;
	public ArrayList<Notice> getNoticeFromUser(User user) throws CustomException, RemoteException;
	
	
	public void removeProject(Project project) throws RemoteException, CustomException;
	public void removeFriendship(Friendship friendship) throws RemoteException, CustomException;
	public ArrayList<User> removeParticipant(Participant partecipant) throws RemoteException, CustomException;
	
	public void setNoticeRead(Notice notice, User user) throws RemoteException, CustomException;
	public void setActivityDone(ActivityInfo activityInfo) throws RemoteException, CustomException;
	//OBSERVER PATTERN
	public void addObserver(IClient _client) throws RemoteException;
	public void removeObserver(IClient _client) throws RemoteException;
	public void notifyObservers(Notice _notice) throws RemoteException;
	public ArrayList<User> removeActivityResponsible(ActivityResponsible resp) throws RemoteException, CustomException;
	
}
