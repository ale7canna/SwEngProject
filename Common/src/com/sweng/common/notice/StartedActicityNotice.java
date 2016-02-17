package com.sweng.common.notice;

import java.util.Date;

import com.sweng.common.beans.ActivityInfo;
import com.sweng.common.utils.DefaultMessages;

public class StartedActicityNotice extends Notice {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ActivityInfo activityInfo;

	public StartedActicityNotice(ActivityInfo _activityInfo) {
		super(DefaultMessages.StartedActivityTitle.toString(), DefaultMessages.StartedActivity.toString());
		activityInfo = _activityInfo;
	}
	
	
	public ActivityInfo getActivityInfo() {
		return activityInfo;
	}
	
	@Override
	public String getDetails()
	{
		String s = "The activity " +
				 	activityInfo.getName() + 
					" in project " +
				 	activityInfo.getProject().getName() + 
					" has started.";
		return s;
	}
	

}
