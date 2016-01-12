package com.sweng.server;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.sweng.common.beans.Project;
import com.sweng.common.beans.User;

public class ServerGUI extends JFrame{
	private JList list, projectList;
	private JPanel projectInfo;
	private DefaultListModel model;
	private int counter = 0;
	private GUIListener listener;
	
	interface GUIListener
	{
		public void UserClicked();
	}
	
	public ServerGUI(GUIListener _listener) {
		listener = _listener;
		
		getContentPane().setSize(new Dimension(600, 400));
		setSize(new Dimension(600, 400));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setSize(new Dimension(600, 400));
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JSplitPane splitPane = new JSplitPane();
		tabbedPane.addTab("New tab", null, splitPane, null);
		
		JPanel panel = new JPanel();
		splitPane.setLeftComponent(panel);
		
		list = new JList(new DefaultListModel());
		
		list.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if (!e.getValueIsAdjusting())
					listener.UserClicked();
			}
		});
		panel.add(list);
		
		JPanel panel_1 = new JPanel();
		splitPane.setRightComponent(panel_1);
		
		splitPane.setDividerLocation(200);
		
		projectList = new JList(new DefaultListModel());
		panel_1.add(projectList);
		
		model = new DefaultListModel();
		
		
	}
	
	public void AddUsersToList(ArrayList<User> users)
	{
		DefaultListModel<String> listModel = (DefaultListModel<String>) list.getModel();
		
		update(getGraphics());
		for (User u :users)
			listModel.addElement(u.getName());
	}
	
	public void AddProjectsToList(ArrayList<Project> projects)
	{
		DefaultListModel<String> listModel = (DefaultListModel<String>) projectList.getModel();
		
		update(getGraphics());
		for (Project p: projects)
			listModel.addElement(p.getName());
	}
	
	
}
