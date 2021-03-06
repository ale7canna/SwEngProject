package com.sweng.client.gui;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.ArrayList;

import com.sweng.common.beans.Activity;
import com.sweng.common.beans.ActivityInfo;
import com.sweng.common.beans.Project;
import com.sweng.common.beans.User;
import com.sweng.common.notice.Notice;

public interface EventListenerGUI {

	public User SignInRequest(String username, String password); 
	
	public void addProject(String name, ArrayList<Integer> participants, boolean isActive);
	
	public void addProjectView();
	
	public void addActivityView(Project project);
	
	public void addActivityFinish(String nameActivity, String place, java.util.Date date, ArrayList<Integer> respActivity, String text);
	
	public void addActivityContinue(String nameActivity, String place, Date hour, ArrayList<Integer> respActivity, String text);
	
	public void addFriendsView();
	
	public ArrayList<User> addFriends( ArrayList<Integer> friends);
	
	public void showProjectInfo(Project p);
	
	public void showActivityInfo(Activity a);
	
	public void buttonclickedAddProject(Project proj);
	
	public void removeFriend(User u);
	
	public ArrayList<User> getFriends();
	
	public ArrayList<Activity> getActivities();
	
	public ArrayList<Project> getProjects();
	
	public void refreshAll();
	
	public void performLogout();
	
	public void closeLogout();

	public void performSignUp(String username, String password, String name, String surname);

	public void showNoticeInfo(Notice n);

	public boolean WindowWasClosed();

	public void addParticipantstoExistingProject(ArrayList<Integer> selectedItems, int i);	

}
