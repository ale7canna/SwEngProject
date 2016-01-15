package com.sweng.client.gui;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.xml.bind.Marshaller.Listener;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JList;

import com.sweng.client.CheckBoxId;
import com.sweng.client.CheckBoxList;
import com.sweng.common.beans.Project;
import com.sweng.common.beans.User;

import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUIaddComponent extends JFrame{
	

	
	public ArrayList<CheckBoxId> createCheckboxList(ArrayList<User> lista){
		ArrayList<CheckBoxId> listacheckbox = new ArrayList<CheckBoxId>();
		for (User u : lista){
			String userName = u.getUsername();
			int id = u.getIdUser();
			checkbox = new CheckBoxId(id, userName);
						
			listacheckbox.add((CheckBoxId) checkbox);
		}
		return listacheckbox;
	}
	
	
	public static JCheckBox checkbox;
	private JTextField txtProjectName;
	private JTextField TimeActivityText;
	private JTextField PlaceActivityText;
	public GUIaddComponent(EventListenerGUI _listener, ArrayList<User> friendships, boolean isProject) {
		setSize(600, 600);
		
		getContentPane().setLayout(null);
	
	    
		JLabel NameLabel = new JLabel("Project Name\r\n");
		NameLabel.setBounds(10, 21, 83, 14);
		getContentPane().add(NameLabel);
		
		txtProjectName = new JTextField();
		txtProjectName.setBounds(13, 54, 86, 20);
		getContentPane().add(txtProjectName);
		txtProjectName.setColumns(10);
		
		
		ArrayList<CheckBoxId> checkboxList = createCheckboxList(friendships);		
		CheckBoxList listFriends = new CheckBoxList();
		for (CheckBoxId c : checkboxList){
				listFriends.addCheckbox(c);		
				}		
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setSize(new Dimension(300, 200));
		scrollPane.setBounds(178, 59, 226, 168);
	
				
		scrollPane.setRowHeaderView(listFriends);
		getContentPane().add(scrollPane);
		
		JLabel ChooseFriendsLabel = new JLabel("Choose partecipans among your friends ");
		ChooseFriendsLabel.setBounds(178, 21, 219, 14);
		getContentPane().add(ChooseFriendsLabel);
		
		JButton addActivitybtn = new JButton("Add Activity");
		addActivitybtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
			}
		});
		addActivitybtn.setBounds(119, 227, 106, 23);
		getContentPane().add(addActivitybtn);
		
		JButton addProjbtn = new JButton("OK");
		addProjbtn.setBounds(266, 227, 89, 23);
		addProjbtn.addMouseMotionListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event)
			{
				//TODO Sistemare e aggiornare interfaccia
				_listener.buttonclickedAddProject(txtProjectName.getText(), listFriends.getSelectedItems());
			}
		});
		getContentPane().add(addProjbtn);
		
		
		JLabel TimeActivityLabel = new JLabel("Activity Time\r\n\r\n");
		TimeActivityLabel.setBounds(13, 101, 83, 14);
		getContentPane().add(TimeActivityLabel);
		
		TimeActivityText = new JTextField();
		TimeActivityText.setBounds(10, 134, 86, 20);
		getContentPane().add(TimeActivityText);
		TimeActivityText.setColumns(10);
		
		JLabel PlaceActivityLabel = new JLabel("Activity Place \r\n");
		PlaceActivityLabel.setBounds(13, 173, 83, 14);
		getContentPane().add(PlaceActivityLabel);
		
		PlaceActivityText = new JTextField();
		PlaceActivityText.setColumns(10);
		PlaceActivityText.setBounds(10, 206, 86, 20);
		getContentPane().add(PlaceActivityText);
		
		
		if(isProject){
			PlaceActivityText.setVisible(false);
			TimeActivityText.setVisible(false);
			PlaceActivityLabel.setVisible(false);
			TimeActivityLabel.setVisible(false);
		}
		else if(!isProject){
			NameLabel = new JLabel("Activity Name\r\n");
			addActivitybtn.setVisible(false);
		}
	    
	}
}
