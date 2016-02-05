package com.sweng.common.gui;

import java.util.ArrayList;

import com.sweng.common.beans.Activity;
import com.sweng.common.beans.ActivityInfo;
import com.sweng.common.beans.Participant;
import com.sweng.common.beans.ProjectInfo;
import com.sweng.common.beans.User;
import com.sweng.common.notice.Notice;

public interface ICommonGui {

	public void RemoveProjectPressed(ProjectInfo projectInfo);
	public void completeActivity(ActivityInfo activityInfo);
	public void refreshAll();
	public void setNoticeRead(Notice notice);
	public ArrayList<User> removeParticipant(Participant part);
	public void showActivityInfo(Activity a);
	
}
