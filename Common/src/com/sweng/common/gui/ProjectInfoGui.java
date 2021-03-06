package com.sweng.common.gui;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import com.sweng.common.beans.Activity;
import com.sweng.common.beans.Participant;
import com.sweng.common.beans.Project;
import com.sweng.common.beans.ProjectInfo;
import com.sweng.common.beans.User;
import com.sweng.common.utils.CustomException;
import java.awt.Component;
import java.awt.Font;
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ProjectInfoGui extends JPanel {

	private JTextField txtProjectName;
	private JTextField txtAdmin;
	private JList listActivities;
	private JList listParticipants;
	private ProjectInfo projectInfo;
	private JCheckBox checkBox;
	private JProgressBar progressBar;

	public ProjectInfoGui(ProjectInfo _projectInfo, ICommonGui _listener,
			boolean isAdmin, int userID) {
		setLayout(new GridLayout(0, 1, 0, 0));
		projectInfo = _projectInfo;
		JSplitPane splitPane = new JSplitPane();
		// getContentPane().add(splitPane);
		add(splitPane);

		JPanel panel_1 = new JPanel();
		splitPane.setRightComponent(panel_1);

		JLabel lblPartecipanti = new JLabel("Participants");
		lblPartecipanti.setForeground(new Color(0, 128, 128));
		lblPartecipanti.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblPartecipanti.setAlignmentX(Component.CENTER_ALIGNMENT);

		listParticipants = new JList(new MyUserListModel());

		JLabel lblDoubleClickTo_1 = new JLabel(
				"Double click to remove a participant");
		lblDoubleClickTo_1.setFont(new Font("Trebuchet MS", Font.ITALIC, 10));

		JButton btnAddNewParticipants = new JButton("Add New Participants");
		if (!isAdmin || projectInfo.getCompletetionPercentage() >= 1) {
			btnAddNewParticipants.setVisible(false);
		} else
			btnAddNewParticipants.setVisible(true);

		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1
				.setHorizontalGroup(gl_panel_1
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panel_1
										.createSequentialGroup()
										.addGap(71)
										.addGroup(
												gl_panel_1
														.createParallelGroup(
																Alignment.TRAILING)
														.addGroup(
																gl_panel_1
																		.createSequentialGroup()
																		.addComponent(
																				lblPartecipanti,
																				GroupLayout.PREFERRED_SIZE,
																				85,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(61))
														.addComponent(
																lblDoubleClickTo_1,
																GroupLayout.PREFERRED_SIZE,
																207,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE))
						.addComponent(listParticipants, Alignment.TRAILING,
								GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
						.addGroup(
								gl_panel_1.createSequentialGroup().addGap(81)
										.addComponent(btnAddNewParticipants)
										.addContainerGap(316, Short.MAX_VALUE)));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel_1
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblPartecipanti)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(listParticipants,
								GroupLayout.PREFERRED_SIZE, 309,
								GroupLayout.PREFERRED_SIZE)
						.addGap(54)
						.addComponent(btnAddNewParticipants)
						.addPreferredGap(ComponentPlacement.RELATED, 71,
								Short.MAX_VALUE)
						.addComponent(lblDoubleClickTo_1).addContainerGap()));
		panel_1.setLayout(gl_panel_1);

		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setLeftComponent(splitPane_1);

		JPanel panel = new JPanel();
		splitPane_1.setLeftComponent(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Project\r\n");
		lblNewLabel.setForeground(new Color(0, 128, 128));
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblNewLabel.setBounds(162, 11, 91, 20);
		panel.add(lblNewLabel);

		JLabel lblNome = new JLabel("Name\r\n");
		lblNome.setBounds(29, 47, 69, 20);
		panel.add(lblNome);

		if (projectInfo != null)
			txtProjectName = new JTextField(projectInfo.getName());
		txtProjectName.setEditable(false);
		txtProjectName.setBounds(100, 44, 146, 26);
		panel.add(txtProjectName);
		txtProjectName.setColumns(10);

		JLabel lblAttivo = new JLabel("is Active\r\n");
		lblAttivo.setBounds(261, 47, 82, 20);
		panel.add(lblAttivo);

		checkBox = new JCheckBox("");
		checkBox.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == 2) {
					if (isAdmin) {
						int scelta = JOptionPane.showConfirmDialog(
								null,
								"Do you really want to start "
										+ projectInfo.getName() + " project?",
								"Start Project", JOptionPane.YES_NO_OPTION);
						if (scelta == JOptionPane.YES_OPTION) {
							_listener.startProjectClicked(new Project(
									projectInfo.getIdProject(), projectInfo
											.getAdmin().getIdUser(),
									projectInfo.getName(), projectInfo
											.isActive(), projectInfo
											.isComplete()));
							JOptionPane.showMessageDialog(null,
									"The Project was correctly started.");
							_listener.refreshAll();
							checkBox.setSelected(true);
						}
					} else
						JOptionPane.showMessageDialog(null,
								"Only the project admin can start the project");
				}
			}
		});
		if (projectInfo != null)
			checkBox.setSelected(projectInfo.isActive());
		checkBox.setEnabled(false);
		checkBox.setBounds(357, 47, 29, 29);
		panel.add(checkBox);

		progressBar = new JProgressBar();
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

		if (isAdmin) {
			JButton btnRemove = new JButton("Remove");
			btnRemove.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent arg0) {

					int scelta = JOptionPane.showConfirmDialog(
							null,
							"Do you really want to remove "
									+ projectInfo.getName() + " project?",
							"Remove Project", JOptionPane.YES_NO_OPTION);
					if (scelta == JOptionPane.YES_OPTION) {
						_listener.RemoveProjectPressed(projectInfo);
					}

				}
			});
			btnRemove.setBounds(271, 12, 115, 29);
			panel.add(btnRemove);

		}

		JPanel panel_2 = new JPanel();
		splitPane_1.setRightComponent(panel_2);

		JLabel lblAttivit = new JLabel("Activity");
		lblAttivit.setForeground(new Color(0, 128, 128));
		lblAttivit.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblAttivit.setFont(new Font("Trebuchet MS", Font.BOLD, 13));

		listActivities = new JList(new MyActivityListModel());

		JLabel lblDoubleClickTo = new JLabel(
				"Double click to modify an activity");
		lblDoubleClickTo.setFont(new Font("Trebuchet MS", Font.ITALIC, 10));

		JButton btnAddNewActivity = new JButton("Add New Activity");

		if (!isAdmin || projectInfo.getCompletetionPercentage() >= 1) {
			btnAddNewActivity.setVisible(false);
		} else
			btnAddNewActivity.setVisible(true);

		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2
				.setHorizontalGroup(gl_panel_2
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panel_2
										.createSequentialGroup()
										.addGroup(
												gl_panel_2
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_panel_2
																		.createSequentialGroup()
																		.addGap(171)
																		.addComponent(
																				lblAttivit))
														.addGroup(
																gl_panel_2
																		.createSequentialGroup()
																		.addGap(46)
																		.addComponent(
																				lblDoubleClickTo,
																				GroupLayout.PREFERRED_SIZE,
																				263,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap(88, Short.MAX_VALUE))
						.addComponent(listActivities, GroupLayout.DEFAULT_SIZE,
								397, Short.MAX_VALUE)
						.addGroup(
								gl_panel_2.createSequentialGroup().addGap(27)
										.addComponent(btnAddNewActivity)
										.addContainerGap(281, Short.MAX_VALUE)));
		gl_panel_2.setVerticalGroup(gl_panel_2.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel_2
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblAttivit)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(listActivities,
								GroupLayout.PREFERRED_SIZE, 213,
								GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(btnAddNewActivity)
						.addPreferredGap(ComponentPlacement.RELATED, 22,
								Short.MAX_VALUE).addComponent(lblDoubleClickTo)
						.addContainerGap()));
		panel_2.setLayout(gl_panel_2);

		splitPane_1.setDividerLocation(175);
		splitPane.setDividerLocation(400);

		if (projectInfo != null)
			addActivitiesAndUsers(projectInfo.getActivitiesInResponsible());
		if (projectInfo != null)
			addUsers(projectInfo.getParticipants());

		if (isAdmin) {
			JLabel lblDoubleClickOn = new JLabel(
					"Double click on a row to modify");
			panel_2.add(lblDoubleClickOn);

			JLabel lblNewLabel_1 = new JLabel(
					"Double click on a row to remove a participant from your project\r\n");
			panel_1.add(lblNewLabel_1);

			btnAddNewActivity.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					if (projectInfo.isComplete())
						JOptionPane
								.showMessageDialog(null,
										"You cannot add activities cause the project is complete.");
					else
						_listener.addActivityView(new Project(projectInfo
								.getIdProject(), projectInfo.getAdmin()
								.getIdUser(), projectInfo.getName(),
								projectInfo.isActive(), projectInfo
										.isComplete()));
				}
			});

			btnAddNewParticipants.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					if (projectInfo.isComplete())
						JOptionPane
								.showMessageDialog(null,
										"You cannot add participant cause the project is complete.");
					else
						_listener.addFriendsView(projectInfo);
				}

			});

			listParticipants.addMouseListener(new MouseAdapter() {

				public void mouseClicked(MouseEvent e) {

					if (e.getClickCount() == 2) {
						if (projectInfo.isComplete())
							JOptionPane
									.showMessageDialog(null,
											"You cannot remove participant cause the project is complete.");
						else {
							User u = (User) ((MyUserListModel) listParticipants
									.getModel()).getUserAt(listParticipants
									.getSelectedIndex());
							int scelta = JOptionPane.showConfirmDialog(
									null,
									"Do you really want to remove "
											+ u.getUsername() + " from "
											+ projectInfo.getName()
											+ " project?",
									"Remove participant",
									JOptionPane.YES_NO_OPTION);
							if (scelta == JOptionPane.YES_OPTION) {
								Participant part = new Participant(u
										.getIdUser(), projectInfo
										.getIdProject());
								addUsers(_listener.removeParticipant(part));
							}
						}
					}

				}
			});
		}

		listActivities.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent ev) {

				if (ev.getClickCount() == 2) {
					Activity a = ((MyActivityListModel) listActivities
							.getModel()).getActivityAt(listActivities
							.getSelectedIndex());

					boolean isResponsible = false;
					Activity MyActivity = null;
					for (Activity act : projectInfo
							.getActivitiesInResponsible().keySet()) {
						if (act.equals(a)) {
							MyActivity = act;
							break;
						}
					}

					ArrayList<User> responsibles = projectInfo
							.getActivitiesInResponsible().get(MyActivity);

					for (User resp : responsibles) {
						if (resp.getIdUser() == userID) {
							isResponsible = true;
							break;
						}
					}
					if (isAdmin || isResponsible)
						_listener.showActivityInfo(a);
				}

			}
		});

	}

	public void addActivitiesAndUsers(HashMap<Activity, ArrayList<User>> data) {

		MyActivityListModel model = (MyActivityListModel) listActivities
				.getModel();

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

	public void refreshContent(ProjectInfo info) {

		if (info != null)
			txtProjectName = new JTextField(info.getName());

		if (info != null)
			checkBox.setSelected(info.isActive());

		if (info != null) {
			int comp = (int) (info.getCompletetionPercentage() * 100);
			progressBar.setValue(comp);
		}
		if (info != null)
			txtAdmin = new JTextField(info.getAdmin().getUsername());

		if (info != null)
			addActivitiesAndUsers(info.getActivitiesInResponsible());
		if (info != null)
			addUsers(info.getParticipants());
		this.projectInfo = info;
	}
}
