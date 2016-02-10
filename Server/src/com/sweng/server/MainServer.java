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
import com.sweng.server.Server.IServerEvents;
import com.sweng.server.gui.ServerGUI;

public class MainServer {

	private static GuiManager guiMgr;
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, RemoteException {

		guiMgr = new GuiManager();
		
		guiMgr.LoadUser();
		guiMgr.LoadProjects();
		guiMgr.LoadActivities();
		
		ServerEvents list = new ServerEvents();
		Server server = new Server(list);
		try 
		{
			Registry registry = LocateRegistry.createRegistry(Consts.RMI_PORT);
			registry.bind(Consts.RMI_ID, server);
		} 
		catch (RemoteException | AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static class ServerEvents implements IServerEvents {

		@Override
		public void aggiornaProgetti() {
			

			Thread t = new Thread(new Runnable() {
				
				public void run() {
					guiMgr.LoadProjects();
				}
			});
			t.start();
		}

		@Override
		public void aggiornaUtenti() {
			

			Thread t = new Thread(new Runnable() {
				
				public void run() {
					guiMgr.LoadUser();
				}
			});
			t.start();
		}

		@Override
		public void aggiornaAttivita() {
			
			Thread t = new Thread(new Runnable() {
				
				public void run() {
					guiMgr.LoadActivities();
				}
			});
			t.start();
		}
		
	}
	
}
