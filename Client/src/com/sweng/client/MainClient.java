package com.sweng.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JFrame;

import com.sweng.common.Consts;
import com.sweng.common.IServer;
import com.sweng.common.beans.User;

public class MainClient {

	private static IServer server = null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry("localhost", Consts.RMI_PORT);
			server = (IServer)registry.lookup(Consts.RMI_ID);
			
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		
		GuiManagerClient guiManagerClient = new GuiManagerClient(server);

		
	}
	
	public static IServer getServer()
	{
		return server;
	}
	
}
