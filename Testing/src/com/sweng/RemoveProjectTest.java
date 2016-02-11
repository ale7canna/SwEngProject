package com.sweng;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import org.junit.Test;

import com.sweng.common.IServer;
import com.sweng.common.beans.Project;
import com.sweng.common.beans.ProjectInfo;
import com.sweng.common.utils.CustomException;
import com.sweng.server.Server;


public class RemoveProjectTest {
	
	@Test
	public void test() throws CustomException, RemoteException {
		
		IServer server = new Server(null);
		
		Project p = new Project(1, "Nome_Progetto", true);
		server.removeProject(p);
		
		try {
			server.getParticipantsFromProject(p);
		}
		catch (CustomException e)
		{
			assertTrue(true);
		}
		
		try {
			ProjectInfo pi = server.getProjectInfo(p);
			assertTrue(pi.getActivitiesInResponsible().isEmpty());
			assertNull(pi.getParticipants());
		}
		catch (CustomException e)
		{
			assertTrue(true);
		}
	}
	
}
