package com.sweng.client;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JList;

public class GUIaddProject extends JFrame{
	private JTextField txtProjectName;
	public GUIaddProject() {
		getContentPane().setLayout(null);
		
		JLabel ProjectNameLabel = new JLabel("Project Name\r\n");
		ProjectNameLabel.setBounds(13, 21, 83, 14);
		getContentPane().add(ProjectNameLabel);
		
		txtProjectName = new JTextField();
		txtProjectName.setBounds(10, 59, 86, 20);
		getContentPane().add(txtProjectName);
		txtProjectName.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(178, 59, 226, 168);
		getContentPane().add(scrollPane);
		
		CheckBoxList list = new CheckBoxList();
		scrollPane.setRowHeaderView(list);
		
		JLabel ChooseFriendsLabel = new JLabel("Choose Friends ");
		ChooseFriendsLabel.setBounds(178, 21, 103, 14);
		getContentPane().add(ChooseFriendsLabel);
	}
}
