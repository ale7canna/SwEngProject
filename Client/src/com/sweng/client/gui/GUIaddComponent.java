package com.sweng.client.gui;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.sweng.client.CheckBoxId;
import com.sweng.client.CheckBoxList;
import com.sweng.common.beans.User;

public class GUIaddComponent extends JFrame{
	

	
	public ArrayList<CheckBoxId> createCheckboxList(ArrayList<User> lista){
		ArrayList<CheckBoxId> listacheckbox = new ArrayList<CheckBoxId>();
		for (User u : lista){
			String userName = u.getUsername();
			int id = u.getIdUser();
			//Qua non ho capito perché usi quel JCheckBox per aggiungerlo alla lista ???
			//checkbox = new CheckBoxId(id, userName);
						
			listacheckbox.add(new CheckBoxId(id, userName));
		}
		return listacheckbox;
	}
	
	
	public static JCheckBox checkbox;
	private JTextField txtProjectName;
	private JTextField TimeActivityText;
	private JTextField PlaceActivityText;
	
	public GUIaddComponent(EventListenerGUI _listener, ArrayList<User> friendships, boolean isProject) {
		
		
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
		scrollPane.setVisible(true);
				
		scrollPane.setRowHeaderView(listFriends);
		
		JLabel ChooseFriendsLabel = new JLabel("Choose partecipans among your friends ");
		ChooseFriendsLabel.setBounds(178, 21, 219, 14);
		getContentPane().add(ChooseFriendsLabel);
		
		JButton addActivitybtn = new JButton("Add Activity");
		addActivitybtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				_listener.addActivity(txtProjectName.toString(), PlaceActivityText.toString(), Date.valueOf(TimeActivityText.toString()) , listFriends.getSelectedItems());
			}
		});
		addActivitybtn.setBounds(119, 227, 106, 23);
		getContentPane().add(addActivitybtn);
		
		JCheckBox isActive = new JCheckBox("New check box");
		isActive.setBounds(56, 85, 23, 20);
		getContentPane().add(isActive);
		
		JButton addProjbtn = new JButton("OK");
		addProjbtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//Qua Marzo mi usi i metodi sbagliati per recuperare nome e il boolean attivo
				//_listener.addProject(txtProjectName.toString(), listFriends.getSelectedItems(), isActive.isEnabled());
				_listener.addProject(txtProjectName.getText(), listFriends.getSelectedItems(), isActive.isSelected());
				JOptionPane.showMessageDialog(null, "Project was added correctly, please close the window or choose add Activity to proceed");
			}
		});
		addProjbtn.setBounds(266, 227, 89, 23);
		getContentPane().add(addProjbtn);
		
		JLabel TimeActivityLabel = new JLabel("Activity Time\r\n\r\n");
		TimeActivityLabel.setBounds(10, 125, 83, 14);
		getContentPane().add(TimeActivityLabel);
		
		TimeActivityText = new JTextField();
		TimeActivityText.setBounds(10, 150, 86, 20);
		getContentPane().add(TimeActivityText);
		TimeActivityText.setColumns(10);
		
		JLabel PlaceActivityLabel = new JLabel("Activity Place \r\n");
		PlaceActivityLabel.setBounds(10, 181, 83, 14);
		getContentPane().add(PlaceActivityLabel);
		
		PlaceActivityText = new JTextField();
		PlaceActivityText.setColumns(10);
		PlaceActivityText.setBounds(10, 206, 86, 20);
		getContentPane().add(PlaceActivityText);
		
		JLabel lblNewLabelActivate = new JLabel("Activate");
		lblNewLabelActivate.setBounds(10, 88, 40, 14);
		getContentPane().add(lblNewLabelActivate);
		
		
		
		
		if(isProject){
			// Nascondi solo le etichette ma rimangono i campi di testo
			PlaceActivityText.setVisible(false);
			PlaceActivityLabel.setVisible(false);
			TimeActivityText.setVisible(false);
			TimeActivityLabel.setVisible(false);
		}
		else if(!isProject){
			NameLabel = new JLabel("Activity Name\r\n");
			addActivitybtn.setVisible(false);
		}
		
		
	    getContentPane().setLayout(null);
	    getContentPane().add(scrollPane);
	    
	}
}
