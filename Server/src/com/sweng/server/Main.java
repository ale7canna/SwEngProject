package com.sweng.server;

import java.sql.SQLException;

import javax.swing.JFrame;

import com.sweng.common.beans.Project;
import com.sweng.common.beans.User;
import com.sweng.server.ServerGUI.GUIListener;

public class Main {

	private static ServerGUI GUI;
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		DBManager db = new DBManager();
		GuiManager guiMgr = new GuiManager();
		
//		db.addProject(new Project(0, 1, "Cena", true));
		guiMgr.LoadUser(db.getAllUsers());
		guiMgr.LoadProjects(db.getAllProjects());
		
	}
	
}
