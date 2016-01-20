package com.sweng.server.gui;

import com.sweng.common.beans.Project;
import com.sweng.common.beans.ProjectInfo;
import com.sweng.common.beans.User;

public interface GUIListener
{
	public void UserClicked(User user);
	public void ProjectClicked(Project project);
	public void UserProjectClicked(Project project);
	
	public void RemoveProjectPressed(ProjectInfo projectInfo);
}