package com.sweng.common.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
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
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Window;

public class ProjectInfoGui extends JPanel {
	private JTextField txtProjectName;
	private JTextField txtAdmin;
	JList listActivities;
	JList listParticipants;

	public ProjectInfoGui(ProjectInfo projectInfo, ICommonGui _listener) {
		setLayout(new GridLayout(0, 1, 0, 0));

		JSplitPane splitPane = new JSplitPane();
		// getContentPane().add(splitPane);
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

		if (projectInfo != null)
			txtProjectName = new JTextField(projectInfo.getName());
		txtProjectName.setEditable(false);
		txtProjectName.setBounds(100, 44, 146, 26);
		panel.add(txtProjectName);
		txtProjectName.setColumns(10);

		JLabel lblAttivo = new JLabel("\u00E8 attivo?");
		lblAttivo.setBounds(261, 47, 82, 20);
		panel.add(lblAttivo);

		JCheckBox checkBox = new JCheckBox("");
		if (projectInfo != null)
			checkBox.setSelected(projectInfo.isActive());
		checkBox.setEnabled(false);
		checkBox.setBounds(357, 47, 29, 29);
		panel.add(checkBox);

		JProgressBar progressBar = new JProgressBar();
		if (projectInfo != null) {
			int comp = (int) (projectInfo.getCompletetionPercentage() * 100);
			progressBar.setValue(comp);
		}
		progressBar.setBounds(166, 86, 220, 26);
		panel.add(progressBar);

		JLabel lblPercentage = new JLabel("Percentage");
		lblPercentage.setBounds(29, 92, 125, 20);
		panel.add(lblPercentage);

		JLabel lblUsernameAdmin = new JLabel("Username Admin:");
		lblUsernameAdmin.setBounds(29, 134, 139, 20);
		panel.add(lblUsernameAdmin);

		if (projectInfo != null)
			txtAdmin = new JTextField(projectInfo.getAdmin().getUsername());
		txtAdmin.setEditable(false);
		txtAdmin.setColumns(10);
		txtAdmin.setBounds(240, 131, 146, 26);
		panel.add(txtAdmin);

		JButton btnRemove = new JButton("Remove");
		btnRemove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int scelta = JOptionPane.showConfirmDialog(null, "Do you really want to remove from your friends "+ projectInfo.getName()+"?", "Remove Project", JOptionPane.YES_NO_OPTION);
				if (scelta == JOptionPane.YES_OPTION)
					{
					_listener.RemoveProjectPressed(projectInfo);
					JOptionPane.showMessageDialog(null, "The Project was correctly removed.");
					_listener.refreshAll();
					}
							
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

		if (projectInfo != null)
			addActivitiesAndUsers(projectInfo.getActivitiesInResponsible());
		if (projectInfo != null)
			addUsers(projectInfo.getParticipants());

	}

	public void addActivitiesAndUsers(HashMap<Activity, User> data) {
		MyActivityListModel model = (MyActivityListModel) listActivities.getModel();

		model.removeAllElements();

		if (data != null)
			for (Activity a : data.keySet()) {
				model.addElement(a, data.get(a));
			}
	}

	public void addUsers(ArrayList<User> users) {
		MyUserListModel model = (MyUserListModel) listParticipants.getModel();
		model.removeAllElements();

		if (users != null)
			for (User u : users) {
				model.addElement(u);
			}
	}
}
