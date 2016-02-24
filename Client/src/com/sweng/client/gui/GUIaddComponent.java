package com.sweng.client.gui;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.WindowConstants;

import com.michaelbaranov.microba.calendar.DatePicker;
import com.sweng.client.CheckBoxId;
import com.sweng.client.CheckBoxList;
import com.sweng.common.beans.Project;
import com.sweng.common.beans.User;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUIaddComponent extends JFrame {

	public ArrayList<CheckBoxId> createCheckboxList(ArrayList<User> lista) {
		ArrayList<CheckBoxId> listacheckbox = new ArrayList<CheckBoxId>();
		if (lista != null) {

			for (User u : lista) {
				String userName = u.getUsername();
				int id = u.getIdUser();
				// Qua non ho capito perch� usi quel JCheckBox per aggiungerlo
				// alla lista ???
				// checkbox = new CheckBoxId(id, userName);

				listacheckbox.add(new CheckBoxId(id, userName));
			}
		}
		return listacheckbox;
	}

	public static JCheckBox checkbox;
	private JTextField txtProjectName;
	private JTextField PlaceActivityText;
	public static Project project;
	private JLabel NameLabel;
	private JButton addProjbtn;
	private JLabel ChooseFriendsLabel;
	private JSpinner timeSpinner;
	private DatePicker datePicker;
	private JTextPane textPane;
	private JCheckBox isActive;
	private CheckBoxList listFriends;

	public GUIaddComponent(EventListenerGUI _listener,
			ArrayList<User> friendships, boolean isProject, boolean isAddFriends, int i) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {

				if (!isProject)
				{
					if (_listener.WindowWasClosed()) {
	                    setVisible(false);
	                    dispose();
	                }
					else
						JOptionPane.showMessageDialog(null, "You must add at least one activity.");
				}
			}
		});
		getContentPane().setLayout(null);
		
		if (!isProject)
			setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		if (isProject && !isAddFriends)
			NameLabel = new JLabel("Project Name\r\n");
		else if (isProject && isAddFriends && i==0)
			NameLabel = new JLabel("Add Friends\r\n");
		else if (!isProject)
			NameLabel = new JLabel("Activity Name\r\n");
		else if(isProject && isAddFriends && i!=0)
			NameLabel = new JLabel("Add Participant to Project\r\n");

		NameLabel.setBounds(10, 21, 83, 14);
		getContentPane().add(NameLabel);

		txtProjectName = new JTextField();
		txtProjectName.setBounds(99, 18, 86, 20);
		getContentPane().add(txtProjectName);
		txtProjectName.setColumns(10);

		ArrayList<CheckBoxId> checkboxList = createCheckboxList(friendships);
		listFriends = new CheckBoxList();
		for (CheckBoxId c : checkboxList) {
			listFriends.addCheckbox(c);
		}

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setSize(new Dimension(300, 200));
		scrollPane.setBounds(279, 46, 258, 167);
		scrollPane.setVisible(true);

		scrollPane.setColumnHeaderView(listFriends);

		if (!isProject) {
			JLabel lblNewLabel = new JLabel("Activity Time\r\n\r\n");
			lblNewLabel.setBounds(10, 296, 94, 20);
			getContentPane().add(lblNewLabel);
			timeSpinner = new JSpinner(new SpinnerDateModel());
			JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
			timeSpinner.setEditor(timeEditor);
			timeSpinner.setValue(Time.from(Instant.now()));
			timeSpinner.setBounds(99, 271, 80, 30);
			getContentPane().add(timeSpinner);
			datePicker = new DatePicker(Date.from(Instant.now()),
					new SimpleDateFormat("yyyy/MM/dd"));
			datePicker.setBounds(99, 312, 200, 50);
			getContentPane().add(datePicker);

			JLabel lblMessage = new JLabel("Message");
			lblMessage.setBounds(10, 126, 71, 23);
			getContentPane().add(lblMessage);

			JPanel panel = new JPanel();
			panel.setBounds(99, 126, 134, 113);
			getContentPane().add(panel);
			panel.setLayout(null);

			JScrollPane scrollPane_1 = new JScrollPane();
			scrollPane_1.setBounds(0, 0, 134, 113);
			panel.add(scrollPane_1);

			textPane = new JTextPane();
			scrollPane_1.setViewportView(textPane);
		}

		if (isProject && !isAddFriends)
			ChooseFriendsLabel = new JLabel("Choose partecipans among your friends ");
		else if (isProject && isAddFriends && i==0)
			ChooseFriendsLabel = new JLabel("Choose new friends");
		else if (isProject && isAddFriends && i!=0)
			ChooseFriendsLabel = new JLabel("Choose new friends to add to the Project");
		else if (!isProject)
			ChooseFriendsLabel = new JLabel("Choose responsible for the activity");
		
		ChooseFriendsLabel.setBounds(279, 21, 239, 14);
		getContentPane().add(ChooseFriendsLabel);

		JButton addActivitybtn = new JButton("Add New Activity");
		addActivitybtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (!isProject) {
					String ts;
					SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

					ts = format.format((java.util.Date) timeSpinner.getValue());
					String dateTime = null;
					dateTime = datePicker.getDateFormat().format(
							datePicker.getDate())
							+ " " + ts;
					java.util.Date date = null;
					SimpleDateFormat defin = new SimpleDateFormat(
							"yyyy/MM/dd HH:mm:ss");
					try {
						date = defin.parse(dateTime);
					} catch (ParseException e) {
						JOptionPane.showMessageDialog(null, e.getMessage()
								+ ". \n Error in date casting.");
					}
					System.out.println(dateTime);

					_listener.addActivityContinue(txtProjectName.getText(),	PlaceActivityText.getText(), date,listFriends.getSelectedItems(), textPane.getText());
					_listener.refreshAll();
				}
			}
		});
		addActivitybtn.setBounds(398, 237, 139, 23);
		getContentPane().add(addActivitybtn);

	
		if (!isProject) {
			addProjbtn = new JButton("FINISH");
		} else{
			addProjbtn = new JButton("OK");
			isActive = new JCheckBox("");
			isActive.setBounds(99, 51, 23, 20);
			isActive.setSelected(true);
			getContentPane().add(isActive);
		}
		

		addProjbtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (isProject && !isAddFriends) {
					_listener.addProject(txtProjectName.getText(),
							listFriends.getSelectedItems(),
							isActive.isSelected());
					_listener.refreshAll();
				}
				else if (isProject && isAddFriends && i==0) {
					// ArrayList<Integer> daaggiungere =
					// listFriends.getSelectedItems();
					_listener.addFriends(listFriends.getSelectedItems());
					_listener.refreshAll();

				}
				else if (isProject && isAddFriends && i!=0) {
					// ArrayList<Integer> daaggiungere =
					// listFriends.getSelectedItems();
					_listener.addParticipantstoExistingProject(listFriends.getSelectedItems(), i);
					_listener.refreshAll();
					setVisible(false);
					dispose();

				}
				else if (!isProject) {
					String ts = null;

					SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

					ts = format.format((java.util.Date) timeSpinner.getValue());
					String dateTime = null;
					dateTime = datePicker.getDateFormat().format(
							datePicker.getDate())
							+ " " + ts;
					java.util.Date date = null;
					SimpleDateFormat defin = new SimpleDateFormat(
							"yyyy/MM/dd HH:mm:ss");
					try {
						date = defin.parse(dateTime);
					} catch (ParseException e) {
						JOptionPane.showMessageDialog(null, e.getMessage()
								+ ". Error in date casting.");
					}
					System.out.println(dateTime);

					_listener.addActivityFinish(txtProjectName.getText(),
							PlaceActivityText.getText(), date,
							listFriends.getSelectedItems(), textPane.getText());
					_listener.refreshAll();
				}

			}
			
		});
		addProjbtn.setBounds(279, 239, 89, 23);
		getContentPane().add(addProjbtn);

		JLabel PlaceActivityLabel = new JLabel("Activity Place \r\n");
		PlaceActivityLabel.setBounds(10, 93, 83, 14);
		getContentPane().add(PlaceActivityLabel);

		PlaceActivityText = new JTextField();
		PlaceActivityText.setColumns(10);
		PlaceActivityText.setBounds(99, 90, 86, 20);
		getContentPane().add(PlaceActivityText);

	

		if (isProject) {
			// Nascondi solo le etichette ma rimangono i campi di testo
			JLabel lblNewLabelActivate = new JLabel("Activate");
			lblNewLabelActivate.setBounds(10, 57, 68, 14);
			getContentPane().add(lblNewLabelActivate);
			PlaceActivityText.setVisible(false);
			PlaceActivityLabel.setVisible(false);

			addActivitybtn.setVisible(false);
			if (isAddFriends) {
				txtProjectName.setVisible(false);
				addActivitybtn.setVisible(false);
				isActive.setVisible(false);

				lblNewLabelActivate.setVisible(false);

			}
		}

		getContentPane().setLayout(null);
		getContentPane().add(scrollPane);
		
	

	}

	public void clearall() {
		txtProjectName.setText("");
		textPane.setText("");
		PlaceActivityText.setText("");
		
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
		timeSpinner.setEditor(timeEditor);
		timeSpinner.setValue(Time.from(Instant.now()));
		
		try {
			datePicker.setDate(Date.from(Instant.now()));
		} catch (PropertyVetoException e) {
			JOptionPane.showMessageDialog(null, e.getMessage()
					+ ". Error in date clearing.");
		}
		
		listFriends.clearSelected();
		listFriends.updateUI();
		
	}
}
