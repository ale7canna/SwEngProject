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

	public StartedActicityNotice(Integer _id, ActivityInfo _activityInfo) {
		super(_id, DefaultMessages.StartedActivityTitle.toString(), DefaultMessages.StartedActivity.toString());
		activityInfo = _activityInfo;
	}
	
	
	public ActivityInfo getActivityInfo() {
		return activityInfo;
	}
	

}
