package com.sweng.common.notice;

import com.sweng.common.beans.Activity;
import com.sweng.common.beans.Project;
import com.sweng.common.utils.DefaultMessages;

public class ActivityWithoutResponsibleNotice extends Notice{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Project project;
	private Activity activity;
	public ActivityWithoutResponsibleNotice(Project _project, Activity _activity) {
		super(DefaultMessages.ActivityWithoutResponsibleTitle.toString(), DefaultMessages.ActivityWithoutResponsible.toString());
		project = _project;
		activity = _activity;
	}

	
	public Project getProject()
	{
		return project;
	}
	
	public Activity getActivity()
	{
		return activity;
	}
}
