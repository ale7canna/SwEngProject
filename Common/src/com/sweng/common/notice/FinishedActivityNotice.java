package com.sweng.common.notice;

import java.util.Date;

import com.sweng.common.beans.ActivityInfo;
import com.sweng.common.utils.DefaultMessages;

public class FinishedActivityNotice extends Notice {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ActivityInfo activityInfo;

	public FinishedActivityNotice(ActivityInfo activityInfo) {
		super(DefaultMessages.ActivityDoneByOtherTitle.toString(), DefaultMessages.ActivityDoneByOther.toString());
		this.activityInfo = activityInfo;
		
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
		String s = "Someone completed the activity " +
				 	activityInfo.getName() + 
					" in project " +
				 	activityInfo.getProject().getName() + 
					".";
		return s;
	}
	
}
