package com.sweng.client.gui;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import com.michaelbaranov.microba.calendar.DatePicker;
import com.sweng.client.CheckBoxId;
import com.sweng.client.CheckBoxList;
import com.sweng.common.beans.Project;
import com.sweng.common.beans.User;

public class GUIaddComponent extends JFrame{
	

	
	public ArrayList<CheckBoxId> createCheckboxList(ArrayList<User> lista){
		ArrayList<CheckBoxId> listacheckbox = new ArrayList<CheckBoxId>();
		if(lista!=null){
			
			for (User u : lista){
				String userName = u.getUsername();
				int id = u.getIdUser();
				//Qua non ho capito perché usi quel JCheckBox per aggiungerlo alla lista ???
				//checkbox = new CheckBoxId(id, userName);
							
				listacheckbox.add(new CheckBoxId(id, userName));
			}
		}
		return listacheckbox;
	}
	
	
	public static JCheckBox checkbox;
	private JTextField txtProjectName;
	private JTextField TimeActivityText;
	private JTextField PlaceActivityText;
	public static Project project;
	private JLabel NameLabel;
	private JButton addProjbtn;
	private JLabel ChooseFriendsLabel;
	private JSpinner timeSpinner;
	private DatePicker datePicker;
	
	public GUIaddComponent(EventListenerGUI _listener, ArrayList<User> friendships, boolean isProject, boolean isAddFriends) {
		
		
		getContentPane().setLayout(null);
	
	    if(isProject&&!isAddFriends)
	    	NameLabel = new JLabel("Project Name\r\n");
	    if(isProject&&isAddFriends)
	    	NameLabel = new JLabel("Add Friends\r\n");
	    if(!isProject)
	    	NameLabel = new JLabel("Activity Name\r\n");
	    
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
		
				if(isProject&&!isAddFriends)
					ChooseFriendsLabel = new JLabel("Choose partecipans among your friends ");
			    if(isProject&&isAddFriends)
			    	ChooseFriendsLabel = new JLabel("Choose new friends");
				if(!isProject)
					ChooseFriendsLabel = new JLabel("Choose responsible for the activity");
				ChooseFriendsLabel.setBounds(178, 21, 219, 14);
				getContentPane().add(ChooseFriendsLabel);
		
		JButton addActivitybtn = new JButton("Add New Activity");
		addActivitybtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(!isProject){
					_listener.addActivityContinue(txtProjectName.getText(), PlaceActivityText.getText(), Date.valueOf(TimeActivityText.toString()) , listFriends.getSelectedItems());
				}
		}});
		addActivitybtn.setBounds(119, 227, 106, 23);
		getContentPane().add(addActivitybtn);
		
		JCheckBox isActive = new JCheckBox("New check box");
		isActive.setBounds(56, 85, 23, 20);
		isActive.setSelected(true);
		getContentPane().add(isActive);
		
		if(!isProject){
			addProjbtn = new JButton("FINISH");
		}
		else
			 addProjbtn = new JButton("OK");
		
		addProjbtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(isProject&&!isAddFriends){
					_listener.addProject(txtProjectName.getText(), listFriends.getSelectedItems(), isActive.isSelected());
					
				}
				if(isProject&&isAddFriends){
					ArrayList<Integer> daaggiungere = listFriends.getSelectedItems();
					_listener.addFriends(daaggiungere);
				
				}
				if(!isProject){
					String ts = null;
				
					SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
					
					ts = format.format((java.util.Date)timeSpinner.getValue()); 
					String dateTime= null;
					dateTime= datePicker.getDateFormat().format(datePicker.getDate())+" "+ts;
					java.util.Date date= null;
					SimpleDateFormat defin= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					try {
						date = defin.parse(dateTime);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(dateTime);
				
					_listener.addActivityFinish(txtProjectName.getText(), PlaceActivityText.getText(), date , listFriends.getSelectedItems());
				}
				
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
		
		if(!isProject){
			timeSpinner = new JSpinner( new SpinnerDateModel() );
			JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
			timeSpinner.setEditor(timeEditor);
			timeSpinner.setValue(Time.from(Instant.now()));
			timeSpinner.setBounds(10, 250, 80, 30);
			getContentPane().add(timeSpinner);
			datePicker = new DatePicker(Date.from(Instant.now()), new SimpleDateFormat("yyyy/MM/dd"));
			datePicker.setBounds(200, 400, 200, 50);
			getContentPane().add(datePicker);
								
		}
		
		
		if(isProject){
			// Nascondi solo le etichette ma rimangono i campi di testo
			PlaceActivityText.setVisible(false);
			PlaceActivityLabel.setVisible(false);
			TimeActivityText.setVisible(false);
			TimeActivityLabel.setVisible(false);
			addActivitybtn.setVisible(false);
			if(isAddFriends){
				txtProjectName.setVisible(false);
				addActivitybtn.setVisible(false);
				isActive.setVisible(false);
				
				lblNewLabelActivate.setVisible(false);
				
			}
		}
		
		
		
	    getContentPane().setLayout(null);
	    getContentPane().add(scrollPane);
	    
	}
}
