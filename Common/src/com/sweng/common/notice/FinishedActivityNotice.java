package com.sweng.common.notice;

import java.util.Date;

public class FinishedActivityNotice extends Notice {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FinishedActivityNotice(Date _date, String _title, String _message) {
		super(_date, _title, _message);
	}
}
