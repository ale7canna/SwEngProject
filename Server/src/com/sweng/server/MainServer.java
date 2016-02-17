package com.sweng.server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;

import javax.swing.JOptionPane;

import com.sweng.common.Consts;
import com.sweng.common.notice.SimpleNotice;
import com.sweng.common.utils.CustomException;
import com.sweng.server.Server.IServerEvents;
import com.sweng.server.gui.ServerGUI;

public class MainServer {

	private static GuiManager guiMgr;
	
	public static void main(String[] args) {
		
		
		try 
		{
			Registry registry = LocateRegistry.createRegistry(Consts.RMI_PORT);
			
			ServerEvents list = new ServerEvents();
			Server server = Server.getInstance(list);
			
			registry.bind(Consts.RMI_ID, server);
			
			guiMgr = new GuiManager(server);
			
			guiMgr.LoadUser();
			guiMgr.LoadProjects();
			guiMgr.LoadActivities();
		} 
		catch (RemoteException | AlreadyBoundException e) {
			if (e instanceof AlreadyBoundException)
				JOptionPane.showMessageDialog(null, "Another server instance is already running on this PC.");
			else if (e instanceof ExportException )
				JOptionPane.showMessageDialog(null, "Another server instance is already running on this PC.");
			else	
				JOptionPane.showMessageDialog(null, "Remote Error Connection.");
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
