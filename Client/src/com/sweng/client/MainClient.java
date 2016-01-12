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
	private static GUI gui = null;
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
		
		
		gui = new GUI();
		gui.switchGui(new GUIPanelSignIn(new GuiListener()));
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setVisible(true);
		gui.setSize(600, 400);
		
		
		
	}
	
	public static IServer getServer()
	{
		return server;
	}
	
	static class GuiListener implements EventListenerGUI
	{

		@Override
		public void SignInRequest(String username, String password) {
			// TODO Auto-generated method stub
			try {
				User user = server.performLogin(username, password);
				GUIPanelHome home = new GUIPanelHome();
				gui.switchGui(home);
				gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				gui.setVisible(true);
				gui.setSize(600, 400);
				home.userInfo(user);
				
			}
			catch (RemoteException e)
			{
				e.printStackTrace();
			}
		}
		
	}

}
