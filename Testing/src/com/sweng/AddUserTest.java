package com.sweng;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.rmi.RemoteException;

import org.junit.Test;

import com.sweng.common.IServer;
import com.sweng.common.beans.User;
import com.sweng.common.utils.CustomException;
import com.sweng.server.DBManager;
import com.sweng.server.Server;


public class AddUserTest {
	
	@Test
	public void test() throws RemoteException {
		
		IServer server = Server.getInstance(null); 
		User u = new User("Alessandro", "Canicatti", "ale7canna", "aleale");
		try {
			server.addUser(u);
			User user = server.performLogin("ale7canna", "aleale");
			
			assertEquals(u.getUsername(), user.getUsername());
		} catch (CustomException e) {
			assertFalse(true);
		}
		
		
	}
	
}
