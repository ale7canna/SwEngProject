package com.sweng.server.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import com.sweng.common.beans.Activity;
import com.sweng.common.beans.ProjectInfo;
import com.sweng.common.beans.User;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProjectInfoGui extends JPanel{
	private JTextField txtProjectName;
	private JTextField txtAdmin;
	JList listActivities;
	JList listParticipants;

	public ProjectInfoGui(ProjectInfo projectInfo, GUIListener _listener)
	{
//		getContentPane().setLayout(new BorderLayout(0, 0));
		setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
//		getContentPane().add(splitPane);
		add(splitPane);
		
		JPanel panel_1 = new JPanel();
		splitPane.setRightComponent(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		JLabel lblPartecipanti = new JLabel("Partecipanti");
		panel_1.add(lblPartecipanti);
		
		listParticipants = new JList(new MyUserListModel());
		panel_1.add(listParticipants);
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setLeftComponent(splitPane_1);
		
		JPanel panel = new JPanel();
		splitPane_1.setLeftComponent(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Progetto");
		lblNewLabel.setBounds(145, 16, 132, 20);
		panel.add(lblNewLabel);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(29, 47, 69, 20);
		panel.add(lblNome);
		
		txtProjectName = new JTextField(projectInfo.getName());
		txtProjectName.setEditable(false);
		txtProjectName.setBounds(100, 44, 146, 26);
		panel.add(txtProjectName);
		txtProjectName.setColumns(10);
		
		JLabel lblAttivo = new JLabel("\u00E8 attivo?");
		lblAttivo.setBounds(261, 47, 82, 20);
		panel.add(lblAttivo);
		
		JCheckBox checkBox = new JCheckBox("");
		checkBox.setSelected(projectInfo.isActive());
		checkBox.setEnabled(false);
		checkBox.setBounds(357, 47, 29, 29);
		panel.add(checkBox);
		
		JProgressBar progressBar = new JProgressBar();
		int comp = (int)(projectInfo.getCompletetionPercentage() * 100);
		progressBar.setValue(comp);
		progressBar.setBounds(166, 86, 220, 26);
		panel.add(progressBar);
		
		JLabel lblPercentage = new JLabel("Percentage");
		lblPercentage.setBounds(29, 92, 125, 20);
		panel.add(lblPercentage);
		
		JLabel lblUsernameAdmin = new JLabel("Username Admin:");
		lblUsernameAdmin.setBounds(29, 134, 139, 20);
		panel.add(lblUsernameAdmin);
		
		txtAdmin = new JTextField(projectInfo.getAdmin().getUsername());
		txtAdmin.setEditable(false);
		txtAdmin.setColumns(10);
		txtAdmin.setBounds(240, 131, 146, 26);
		panel.add(txtAdmin);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				_listener.RemoveProjectPressed(projectInfo);
			}
		});
		btnRemove.setBounds(271, 12, 115, 29);
		panel.add(btnRemove);
		
		JPanel panel_2 = new JPanel();
		splitPane_1.setRightComponent(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		
		JLabel lblAttivit = new JLabel("Attivit\u00E0");
		panel_2.add(lblAttivit);
		
		listActivities = new JList(new MyActivityListModel());
		panel_2.add(listActivities);
		splitPane_1.setDividerLocation(175);
		splitPane.setDividerLocation(400);
		
		addActivitiesAndUsers(projectInfo.getActivitiesInResponsible());
		addUsers(projectInfo.getParticipants());
		
	}
	
	public void addActivitiesAndUsers(HashMap<Activity, User> data)
	{
		MyActivityListModel model = (MyActivityListModel)listActivities.getModel();
		
		for (Activity a : data.keySet())
		{
			model.addElement(a, data.get(a));
		}
	}
	
	public void addUsers(ArrayList<User> users)
	{
		MyUserListModel model = (MyUserListModel)listParticipants.getModel();
		
		for (User u: users)
		{
			model.addElement(u);
		}
	}
}
