package com.sweng;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sweng.common.beans.User;
import com.sweng.common.utils.CustomException;
import com.sweng.server.DBManager;


public class AddUserTest {
	
	@Test
	public void test() {
		
		User u = new User("Alessandro", "Canicatti", "ale7canna", "aleale");
		try {
			DBManager.addUser(u);
			User user = DBManager.getUser("ale7canna", "aleale");
			
			assertEquals(u.getUsername(), user.getUsername());
		} catch (CustomException e) {
			assertFalse(true);
		}
		
		
	}
	
}
