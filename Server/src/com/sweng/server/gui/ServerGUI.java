package com.sweng.server.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import com.sweng.common.beans.Project;
import com.sweng.common.beans.User;

public class ServerGUI extends JFrame{
	private JList list, projectList;
	private JPanel projectInfo;
	private DefaultListModel model;
	private int counter = 0;
	private GUIListener listener;
	
	public static void setUIFont(FontUIResource f) {
        Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                FontUIResource orig = (FontUIResource) value;
                Font font = new Font(f.getFontName(), orig.getStyle(), f.getSize());
                UIManager.put(key, new FontUIResource(font));
            }
        }
    }
	
	public ServerGUI(GUIListener _listener) {
		listener = _listener;
		
		Font f = new Font("Arial", Font.PLAIN, 20);
//		setFont(f);
		setUIFont(new FontUIResource(f));
		
		getContentPane().setSize(new Dimension(600, 400));
		setSize(new Dimension(600, 400));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setSize(new Dimension(600, 400));
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JSplitPane splitPane = new JSplitPane();
		tabbedPane.addTab("New tab", null, splitPane, null);
		
		JPanel panel = new JPanel();
		splitPane.setLeftComponent(panel);
		
		list = new JList(new MyUserListModel());
		
		list.addMouseListener(new MouseAdapter() {
					
			public void mouseClicked(MouseEvent e) {
					User u = (User)((MyUserListModel) list.getModel()).getUserAt(list.getSelectedIndex());
					listener.UserClicked(u);
			}
		});
		panel.add(list);
		
		JPanel panel_1 = new JPanel();
		splitPane.setRightComponent(panel_1);
		
		splitPane.setDividerLocation(200);
		
		projectList = new JList(new MyProjectListModel());
		projectList.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent evt)
			{
				Project p = (Project)((MyProjectListModel) projectList.getModel()).getProjectAt(projectList.getSelectedIndex());
				listener.ProjectClicked(p);
			}
		});;
		
		panel_1.add(projectList);
		
		JList list_1 = new JList();
		tabbedPane.addTab("New tab", null, list_1, null);

		
	
		
	}
	
	public void AddUsersToList(ArrayList<User> users)
	{
		MyUserListModel listModel = (MyUserListModel) list.getModel();
		
		for (User u :users)
			listModel.addElement(u);
	}
	
	public void AddProjectsToList(ArrayList<Project> projects)
	{
		MyProjectListModel listModel = (MyProjectListModel)projectList.getModel();
		listModel.removeAllElements();
		
		//update(getGraphics());
		if (projects != null)
			for (Project p: projects)
				listModel.addElement(p);
	}
	
	
}
