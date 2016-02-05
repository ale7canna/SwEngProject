package com.sweng.common.notice;

import java.util.Date;

import com.sweng.common.utils.DefaultMessages;

public class SimpleNotice extends Notice{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SimpleNotice(Integer _id, String _title, String _message) {
		super(_id, _title, _message);
	}

}
