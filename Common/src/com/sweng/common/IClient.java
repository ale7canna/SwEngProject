package com.sweng.common;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import com.sweng.common.notice.Notice;

public interface IClient extends Remote, Serializable {
	public void update(Notice notice) throws RemoteException;
	
	public void sendMessage(String message) throws RemoteException;
	
	public int getId()throws RemoteException;
	public String getUsername()throws RemoteException;
}
