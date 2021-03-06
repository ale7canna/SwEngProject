package com.sweng.client;

import java.net.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JOptionPane;

import com.sweng.common.Consts;
import com.sweng.common.IServer;

public class MainClient {

	private static IServer server = null;
	
	public static void main(String[] args) throws RemoteException {
		
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry("localhost", Consts.RMI_PORT);
//			registry = LocateRegistry.getRegistry("192.168.17.1", Consts.RMI_PORT);
			server = (IServer)registry.lookup(Consts.RMI_ID);
			
		} catch (RemoteException | NotBoundException e) {
			System.out.println(e.getMessage());
			
			if (e.getCause() instanceof ConnectException)
				JOptionPane.showMessageDialog(null, "The server is not available. Please try again and be patient!");
			else
				JOptionPane.showMessageDialog(null, "Error from the server.");
			return;
		}
		
		
		Client clientManager = new Client(server);

		
	}
	
	public static IServer getServer()
	{
		return server;
	}
	
}
