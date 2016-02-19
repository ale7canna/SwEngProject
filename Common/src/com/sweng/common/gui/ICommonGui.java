package com.sweng.common.gui;

import java.rmi.RemoteException;
import java.util.ArrayList;

import com.sweng.common.beans.Activity;
import com.sweng.common.beans.ActivityInfo;
import com.sweng.common.beans.ActivityResponsible;
import com.sweng.common.beans.Participant;
import com.sweng.common.beans.Project;
import com.sweng.common.beans.ProjectInfo;
import com.sweng.common.beans.User;
import com.sweng.common.notice.Notice;
import com.sweng.common.utils.CustomException;

public interface ICommonGui {

	public void RemoveProjectPressed(ProjectInfo projectInfo);
	public void completeActivity(ActivityInfo activityInfo);
	public void refreshAll();
	public void setNoticeRead(Notice notice);
	public ArrayList<User> removeParticipant(Participant part);
	public void showActivityInfo(Activity a);
	public ArrayList<User> removeResponsible(ActivityResponsible resp);
	public void startProject(Project project) throws RemoteException, CustomException;
	public void addText(ActivityInfo activityInfo, String text);
	public void startProjectClicked(Project project);
}
