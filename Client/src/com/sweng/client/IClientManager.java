package com.sweng.client;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.ArrayList;

import com.sweng.common.IServer;
import com.sweng.common.beans.Activity;
import com.sweng.common.beans.ActivityInfo;
import com.sweng.common.beans.Project;
import com.sweng.common.beans.ProjectInfo;
import com.sweng.common.beans.User;
import com.sweng.common.notice.Notice;

public interface IClientManager {
	
	public void SignInRequest(String username, String password);
	public User getUser(); 
	public ArrayList<User> getFriendships();
	public ArrayList<Activity> getActivity();
	public ArrayList<Project> getProject();
	public ArrayList<User> getParticipant(Project project);
	public ArrayList<Notice> getNotices();
	public Project addProject(String nameProject, int idAdmin, boolean isActive);
	public Activity addActivity(String nameActivity, int idProject, String place, Date hour, boolean isLast);
	public void addFriends(ArrayList<Integer> friends);
	public void addParticipants(ArrayList<Integer> participants, int idProject);
	public void addRespActivity(int idActivity, ArrayList<Integer> responsibles);
	public ArrayList<User> getNotmyFriends();
	public ProjectInfo getProjectInfo(Project project);
	public ActivityInfo getActivityInfo(Activity activity);
	public void removeFriend(User u);
	public void removeProject(ProjectInfo p);
	public void setActivityInfoDone(ActivityInfo activityInfo);
	public void logout();
	public void performRegistration(String username, String password, String name, String surname);
	void removeNotice(Notice notice);
	public int getId();
}
