package com.sweng.common.notice;

import java.util.Date;

import com.sweng.common.beans.Activity;

public class UnlockedActivityNotice extends Notice {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Activity activity;
	
	public UnlockedActivityNotice(Date _date, String _title, String _message, Activity _activity) {
		super(_date, _title, _message);
		activity = _activity;
	}
	
	public Activity getActivity()
	{
		return activity;
	}

}
