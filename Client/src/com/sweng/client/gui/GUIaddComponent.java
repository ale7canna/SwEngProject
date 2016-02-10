package com.sweng.client.gui;

import java.awt.Dimension;
import java.awt.Window;
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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingUtilities;

import sun.swing.SwingAccessor;

import com.michaelbaranov.microba.calendar.DatePicker;
import com.sweng.client.CheckBoxId;
import com.sweng.client.CheckBoxList;
import com.sweng.common.beans.Project;
import com.sweng.common.beans.User;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextPane;

public class GUIaddComponent extends JFrame {

	public ArrayList<CheckBoxId> createCheckboxList(ArrayList<User> lista) {
		ArrayList<CheckBoxId> listacheckbox = new ArrayList<CheckBoxId>();
		if (lista != null) {

			for (User u : lista) {
				String userName = u.getUsername();
				int id = u.getIdUser();
				// Qua non ho capito perché usi quel JCheckBox per aggiungerlo
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

	public GUIaddComponent(EventListenerGUI _listener,
			ArrayList<User> friendships, boolean isProject, boolean isAddFriends) {

		getContentPane().setLayout(null);

		if (isProject && !isAddFriends)
			NameLabel = new JLabel("Project Name\r\n");
		if (isProject && isAddFriends)
			NameLabel = new JLabel("Add Friends\r\n");
		if (!isProject)
			NameLabel = new JLabel("Activity Name\r\n");

		NameLabel.setBounds(10, 21, 83, 14);
		getContentPane().add(NameLabel);

		txtProjectName = new JTextField();
		txtProjectName.setBounds(128, 22, 86, 20);
		getContentPane().add(txtProjectName);
		txtProjectName.setColumns(10);

		ArrayList<CheckBoxId> checkboxList = createCheckboxList(friendships);
		CheckBoxList listFriends = new CheckBoxList();
		for (CheckBoxId c : checkboxList) {
			listFriends.addCheckbox(c);
		}

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setSize(new Dimension(300, 200));
		scrollPane.setBounds(280, 51, 226, 168);
		scrollPane.setVisible(true);

		scrollPane.setColumnHeaderView(listFriends);

		if (!isProject) {
			timeSpinner = new JSpinner(new SpinnerDateModel());
			JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(
					timeSpinner, "HH:mm:ss");
			timeSpinner.setEditor(timeEditor);
			timeSpinner.setValue(Time.from(Instant.now()));
			timeSpinner.setBounds(10, 250, 80, 30);
			getContentPane().add(timeSpinner);
			datePicker = new DatePicker(Date.from(Instant.now()),
					new SimpleDateFormat("yyyy/MM/dd"));
			datePicker.setBounds(200, 400, 200, 50);
			getContentPane().add(datePicker);

			JLabel lblMessage = new JLabel("Message");
			lblMessage.setBounds(10, 234, 71, 23);
			getContentPane().add(lblMessage);

			JPanel panel = new JPanel();
			panel.setBounds(110, 234, 134, 113);
			getContentPane().add(panel);
			panel.setLayout(null);

			JScrollPane scrollPane_1 = new JScrollPane();
			scrollPane_1.setBounds(0, 0, 134, 113);
			panel.add(scrollPane_1);

			textPane = new JTextPane();
			scrollPane_1.setViewportView(textPane);
		}

		if (isProject && !isAddFriends)
			ChooseFriendsLabel = new JLabel(
					"Choose partecipans among your friends ");
		if (isProject && isAddFriends)
			ChooseFriendsLabel = new JLabel("Choose new friends");
		if (!isProject)
			ChooseFriendsLabel = new JLabel("Choose responsible for the activity");
		
		ChooseFriendsLabel.setBounds(178, 21, 219, 14);
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
								+ ". Please try again!");
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(dateTime);

					_listener.addActivityContinue(txtProjectName.getText(),
							PlaceActivityText.getText(), date,
							listFriends.getSelectedItems(), textPane.getText());
					_listener.refreshAll();
				}
			}
		});
		addActivitybtn.setBounds(353, 234, 106, 23);
		getContentPane().add(addActivitybtn);

		JCheckBox isActive = new JCheckBox("");
		isActive.setBounds(84, 51, 23, 20);
		isActive.setSelected(true);
		getContentPane().add(isActive);

		if (!isProject) {
			addProjbtn = new JButton("FINISH");
		} else
			addProjbtn = new JButton("OK");

		addProjbtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (isProject && !isAddFriends) {
					_listener.addProject(txtProjectName.getText(),
							listFriends.getSelectedItems(),
							isActive.isSelected());
					_listener.refreshAll();
				}
				if (isProject && isAddFriends) {
					// ArrayList<Integer> daaggiungere =
					// listFriends.getSelectedItems();
					_listener.addFriends(listFriends.getSelectedItems());
					_listener.refreshAll();

				}
				if (!isProject) {
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
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, e.getMessage()
								+ ". Please try again!");
						e.printStackTrace();
					}
					System.out.println(dateTime);

					_listener.addActivityFinish(txtProjectName.getText(),
							PlaceActivityText.getText(), date,
							listFriends.getSelectedItems(), textPane.getText());
					_listener.refreshAll();
				}

			}
		});
		addProjbtn.setBounds(266, 227, 89, 23);
		getContentPane().add(addProjbtn);

		JLabel PlaceActivityLabel = new JLabel("Activity Place \r\n");
		PlaceActivityLabel.setBounds(10, 181, 83, 14);
		getContentPane().add(PlaceActivityLabel);

		PlaceActivityText = new JTextField();
		PlaceActivityText.setColumns(10);
		PlaceActivityText.setBounds(110, 178, 86, 20);
		getContentPane().add(PlaceActivityText);

		JLabel lblNewLabelActivate = new JLabel("Activate");
		lblNewLabelActivate.setBounds(10, 57, 68, 14);
		getContentPane().add(lblNewLabelActivate);

		if (isProject) {
			// Nascondi solo le etichette ma rimangono i campi di testo
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
}
