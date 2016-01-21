package com.sweng.client;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.ArrayList;

import com.sweng.common.IServer;
import com.sweng.common.beans.Activity;
import com.sweng.common.beans.Project;
import com.sweng.common.beans.User;

public interface IClientManager {
	
	public void SignInRequest(String username, String password);
	public User getUser(); 
	public ArrayList<User> getFriendships();
	public ArrayList<Activity> getActivity();
	public ArrayList<Project> getProject();
	public ArrayList<User> getParticipant(Project project);
	public Project addProject(String nameProject, int idAdmin, boolean isActive);
	public Activity addActivity(String nameActivity, int idProject, String place, Date hour);
	public void addFriends(ArrayList<Integer> friends);
	public void addParticipants(ArrayList<Integer> participants, int idProject);
	public void addRespActivity(int idActivity, ArrayList<Integer> responsibles);
	public ArrayList<User> getNotmyFriends();
}
