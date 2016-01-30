package com.sweng.server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;

import com.sweng.common.Consts;
import com.sweng.common.notice.SimpleNotice;
import com.sweng.common.utils.CustomException;
import com.sweng.server.gui.ServerGUI;

public class MainServer {

	private static ServerGUI GUI;
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, RemoteException {

		DBManager db = new DBManager();
		GuiManager guiMgr = new GuiManager();
		
//		db.addProject(new Project(0, 1, "Cena", true));
		try
		{
			guiMgr.LoadUser(DBManager.getAllUsers());
			guiMgr.LoadProjects(DBManager.getAllProjects(), DBManager.getActiveProjectsCount(), DBManager.getTotalProjectsCount());
			guiMgr.LoadActivities(DBManager.getAllActivities(), DBManager.getDoneActivitiesCount(), DBManager.getActivitiesCount());
		}
		catch (CustomException e)
		{
			
		}
		
		Server server = new Server();
		try 
		{
			Registry registry = LocateRegistry.createRegistry(Consts.RMI_PORT);
			registry.bind(Consts.RMI_ID, server);
		} 
		catch (RemoteException | AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
//		SimpleNotice n = new SimpleNotice(Date.from(Instant.now()), "Not", "Prima notifica");
//		
//		DBManager.storeNotices(n, 2);
//
//		DBManager.storeNotices(n, 1);
	}
	
}
