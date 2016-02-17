package com.sweng.common.notice;

import com.sweng.common.beans.ProjectInfo;
import com.sweng.common.beans.User;
import com.sweng.common.utils.DefaultMessages;

public class FriendshipAdded extends Notice {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private User friend; 
	public FriendshipAdded(User _friend) {
		super(DefaultMessages.FriendShipAddedTitle.toString(), _friend.getUsername() + " added you to his friends");
		friend = _friend;
	}
	
	User getFriend()
	{
		return friend;
	}
	
	@Override
	public String getDetails()
	{
		String s = friend.getName() + " " + friend.getSurname() + " added you to his friends.";
		return s;
	}

}
