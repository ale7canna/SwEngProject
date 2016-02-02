package com.sweng.common.gui;

import com.sweng.common.beans.ActivityInfo;
import com.sweng.common.beans.ProjectInfo;

public interface ICommonGui {

	public void RemoveProjectPressed(ProjectInfo projectInfo);
	public void completeActivity(ActivityInfo activityInfo);
	public void refreshAll();
	
}
