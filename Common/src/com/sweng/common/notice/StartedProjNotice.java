package com.sweng.common.notice;

import java.util.Date;

public class StartedProjNotice extends Notice {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public StartedProjNotice(Date _date, String _title, String _message) {
		
		super(_date, _title, _message);
	}
	
}
