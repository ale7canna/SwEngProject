package com.sweng.common.notice;

import com.sweng.common.beans.ActivityInfo;
import com.sweng.common.beans.User;
import com.sweng.common.utils.DefaultMessages;

public class FinishedActivityNotice extends Notice {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ActivityInfo activityInfo;
	private User who;

	public FinishedActivityNotice(ActivityInfo activityInfo, User whoComplete) {
		super(DefaultMessages.ActivityDoneByOtherTitle.toString(), DefaultMessages.ActivityDoneByOther.toString());
		this.activityInfo = activityInfo;
		this.who = whoComplete;
	}

	
	public ActivityInfo getActivityInfo() {
		
		return activityInfo;
	}

	
	public void setActivityInfo(ActivityInfo activityInfo) {
		
		this.activityInfo = activityInfo;
	}
	
	@Override
	public String getDetails()
	{
		String s = 	who.getName() + " " + who.getSurname() + 
					" completed the activity " +
				 	activityInfo.getName() + 
					" in project " +
				 	activityInfo.getProject().getName() + 
					".";
		return s;
	}
	
}
