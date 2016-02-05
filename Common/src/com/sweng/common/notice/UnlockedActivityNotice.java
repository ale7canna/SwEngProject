package com.sweng.common.notice;

import java.util.Date;

import com.sweng.common.beans.Activity;
import com.sweng.common.utils.DefaultMessages;

public class UnlockedActivityNotice extends Notice {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Activity activity;
	
	public UnlockedActivityNotice(Activity _activity) {
		super(DefaultMessages.UnlockedActivityTitle.toString(), DefaultMessages.UnlockedActivity.toString());
		activity = _activity;
	}
	
	public Activity getActivity()
	{
		return activity;
	}

}
