package com.sweng.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.sweng.common.IClient;

public class Client extends UnicastRemoteObject implements IClient{

	protected Client() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	public void signUp(){
		
	}
	
	public void signIn(){
		
	}
	
	public void matchUser(){
		
	}
	
	

}
