package com.sweng.server.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.FontUIResource;

import com.sweng.common.beans.Activity;
import com.sweng.common.beans.ActivityInfo;
import com.sweng.common.beans.Project;
import com.sweng.common.beans.User;
import com.sweng.common.gui.ActivityInfoGui;
import com.sweng.common.gui.MyActivityListModel;
import com.sweng.common.gui.MyProjectListModel;
import com.sweng.common.gui.MyUserListModel;
import com.sweng.common.gui.ProjectInfoGui;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

public class ServerGUI extends JFrame {
	private JList userList, userProjectList, listFriends, activityList, projectList;
	private JPanel projectInfo, standardProjectPanelView;
	private JSplitPane splitPaneProjects, splitPaneActivity ;
	private JLabel projectSummaryLabel, activitySummaryLabel, usersSummaryLabel;
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
		setUIFont(new FontUIResource(f));

		getContentPane().setSize(new Dimension(600, 400));
		setSize(new Dimension(1031, 570));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setSize(new Dimension(600, 400));
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.anchor = GridBagConstraints.NORTHWEST;
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 0;
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.anchor = GridBagConstraints.NORTHWEST;
		gbc_panel_5.gridx = 0;
		gbc_panel_5.gridy = 0;
		;

		JPanel tabUtente = new JPanel();
		tabbedPane.addTab("Utenti", null, tabUtente, null);

		JPanel panelUser = new JPanel();
		panelUser.setAlignmentY(Component.TOP_ALIGNMENT);
		panelUser.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelUser.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));

		userList = new JList(new MyUserListModel());
		userList.setAlignmentY(Component.TOP_ALIGNMENT);
		userList.setAlignmentX(Component.LEFT_ALIGNMENT);

		userList.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				User u = (User) ((MyUserListModel) userList.getModel()).getUserAt(userList.getSelectedIndex());
				listener.UserClicked(u);
			}
		});

		JLabel lblNewLabel_2 = new JLabel("Utenti");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblNewLabel_2.setAlignmentY(Component.TOP_ALIGNMENT);

		JPanel panelUserProject = new JPanel();
		panelUserProject.setAlignmentY(Component.TOP_ALIGNMENT);
		panelUserProject.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelUserProject.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));

		userProjectList = new JList(new MyProjectListModel());
		userProjectList.setAlignmentY(Component.TOP_ALIGNMENT);
		userProjectList.setAlignmentX(Component.LEFT_ALIGNMENT);
		userProjectList.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 2) {
					Project p = (Project) ((MyProjectListModel) userProjectList.getModel())
							.getProjectAt(userProjectList.getSelectedIndex());
					listener.UserProjectClicked(p);
				}
			}
		});

		JLabel lblNewLabel_3 = new JLabel("Progetti");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblNewLabel_3.setAlignmentY(Component.TOP_ALIGNMENT);

		JPanel panelUserFriends = new JPanel();
		panelUserFriends.setAlignmentY(Component.TOP_ALIGNMENT);
		panelUserFriends.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelUserFriends.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));

		JLabel lblNewLabel_4 = new JLabel("Amici");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblNewLabel_4.setAlignmentY(Component.TOP_ALIGNMENT);

		listFriends = new JList(new MyUserListModel());
		listFriends.setAlignmentY(Component.TOP_ALIGNMENT);
		listFriends.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		usersSummaryLabel = new JLabel("");
		GroupLayout gl_tabUtente = new GroupLayout(tabUtente);
		gl_tabUtente.setHorizontalGroup(
			gl_tabUtente.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_tabUtente.createSequentialGroup()
					.addComponent(panelUser, GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_tabUtente.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_tabUtente.createSequentialGroup()
							.addComponent(usersSummaryLabel, GroupLayout.PREFERRED_SIZE, 566, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_tabUtente.createSequentialGroup()
							.addComponent(panelUserProject, GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panelUserFriends, GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE))))
		);
		gl_tabUtente.setVerticalGroup(
			gl_tabUtente.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tabUtente.createSequentialGroup()
					.addGap(14)
					.addComponent(usersSummaryLabel, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_tabUtente.createParallelGroup(Alignment.LEADING)
						.addComponent(panelUserProject, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panelUserFriends, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panelUser, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		GroupLayout gl_panelUserFriends = new GroupLayout(panelUserFriends);
		gl_panelUserFriends.setHorizontalGroup(
			gl_panelUserFriends.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panelUserFriends.createSequentialGroup()
					.addGap(136)
					.addComponent(lblNewLabel_4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(130))
				.addComponent(listFriends, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
		);
		gl_panelUserFriends.setVerticalGroup(
			gl_panelUserFriends.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelUserFriends.createSequentialGroup()
					.addComponent(lblNewLabel_4)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(listFriends, GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE))
		);
		panelUserFriends.setLayout(gl_panelUserFriends);
		
		GroupLayout gl_panelUserProject = new GroupLayout(panelUserProject);
		gl_panelUserProject.setHorizontalGroup(
			gl_panelUserProject.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelUserProject.createSequentialGroup()
					.addGap(127)
					.addComponent(lblNewLabel_3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(121))
				.addComponent(userProjectList, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
		);
		gl_panelUserProject.setVerticalGroup(
			gl_panelUserProject.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelUserProject.createSequentialGroup()
					.addComponent(lblNewLabel_3)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(userProjectList, GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE))
		);
		panelUserProject.setLayout(gl_panelUserProject);
		
		GroupLayout gl_panelUser = new GroupLayout(panelUser);
		gl_panelUser.setHorizontalGroup(
			gl_panelUser.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelUser.createSequentialGroup()
					.addGap(142)
					.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(138))
				.addComponent(userList, GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
		);
		gl_panelUser.setVerticalGroup(
			gl_panelUser.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelUser.createSequentialGroup()
					.addComponent(lblNewLabel_2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(userList, GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE))
		);
		panelUser.setLayout(gl_panelUser);
		tabUtente.setLayout(gl_tabUtente);

		JPanel tabProgetti = new JPanel();
		tabbedPane.addTab("Progetti", null, tabProgetti, null);
		GridBagLayout gbl_tabProgetti = new GridBagLayout();
		gbl_tabProgetti.columnWidths = new int[]{1004, 0};
		gbl_tabProgetti.rowHeights = new int[]{36, 439, 0};
		gbl_tabProgetti.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_tabProgetti.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		tabProgetti.setLayout(gbl_tabProgetti);
		
				splitPaneProjects = new JSplitPane();
				
						standardProjectPanelView = new JPanel();
						standardProjectPanelView.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
						splitPaneProjects.setRightComponent(standardProjectPanelView);
						
								JLabel label = new JLabel("Clicca un progetto a lato per caricare i dettagli");
								label.setHorizontalAlignment(SwingConstants.CENTER);
								label.setAlignmentX(0.5f);
								standardProjectPanelView.add(label);
								splitPaneProjects.setDividerLocation(250);
								
								JScrollPane scrollPane = new JScrollPane();
								splitPaneProjects.setLeftComponent(scrollPane);
								
								projectList = new JList(new MyProjectListModel());
								projectList.addMouseListener(new MouseAdapter() {

									public void mouseClicked(MouseEvent evt) {
										if (evt.getClickCount() == 1) {
											Project project = (Project) ((MyProjectListModel) projectList.getModel())
													.getProjectAt(projectList.getSelectedIndex());
											listener.ProjectClicked(project);
										}
									}
								});
								
										JPanel panel_2 = new JPanel();
										panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
										
												projectSummaryLabel = new JLabel("");
												panel_2.add(projectSummaryLabel);
												GridBagConstraints gbc_panel_2 = new GridBagConstraints();
												gbc_panel_2.fill = GridBagConstraints.BOTH;
												gbc_panel_2.insets = new Insets(0, 0, 5, 0);
												gbc_panel_2.gridx = 0;
												gbc_panel_2.gridy = 0;
												tabProgetti.add(panel_2, gbc_panel_2);
								scrollPane.setViewportView(projectList);
								GridBagConstraints gbc_splitPaneProjects = new GridBagConstraints();
								gbc_splitPaneProjects.fill = GridBagConstraints.BOTH;
								gbc_splitPaneProjects.gridx = 0;
								gbc_splitPaneProjects.gridy = 1;
								tabProgetti.add(splitPaneProjects, gbc_splitPaneProjects);
		
		JPanel tabAttivita = new JPanel();
		tabbedPane.addTab("Attivit\u00E0", null, tabAttivita, null);
		GridBagLayout gbl_tabAttivita = new GridBagLayout();
		gbl_tabAttivita.columnWidths = new int[]{833, 0};
		gbl_tabAttivita.rowHeights = new int[]{41, 324, 0};
		gbl_tabAttivita.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_tabAttivita.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		tabAttivita.setLayout(gbl_tabAttivita);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		tabAttivita.add(panel_1, gbc_panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		activitySummaryLabel = new JLabel("");
		panel_1.add(activitySummaryLabel);
		
		splitPaneActivity = new JSplitPane();
		GridBagConstraints gbc_splitPane = new GridBagConstraints();
		gbc_splitPane.fill = GridBagConstraints.BOTH;
		gbc_splitPane.gridx = 0;
		gbc_splitPane.gridy = 1;
		tabAttivita.add(splitPaneActivity, gbc_splitPane);
		
		activityList = new JList(new MyActivityListModel());
		activityList.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 1) {
					Activity a = (Activity) ((MyActivityListModel) activityList.getModel())
							.getActivityAt(activityList.getSelectedIndex());
					listener.ActivityClicked(a);
				}
			}
		});
		splitPaneActivity.setLeftComponent(activityList);
		
		JPanel panel_7 = new JPanel();
		splitPaneActivity.setRightComponent(panel_7);
		panel_7.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblCliccaUnattivitA = new JLabel("Clicca un'attivit\u00E0 a lato per caricare i dettagli");
		lblCliccaUnattivitA.setHorizontalAlignment(SwingConstants.CENTER);
		lblCliccaUnattivitA.setAlignmentX(0.5f);
		panel_7.add(lblCliccaUnattivitA);
		splitPaneActivity.setDividerLocation(200);

	}

	public void AddUsersToList(ArrayList<User> users) {
		MyUserListModel listModel = (MyUserListModel) userList.getModel();

		listModel.removeAllElements();
		if (users != null)
			for (User u : users)
				listModel.addElement(u);
	}

	public void AddFriendsToList(ArrayList<User> users) {
		MyUserListModel listModel = (MyUserListModel) listFriends.getModel();

		listModel.removeAllElements();

		if (users != null)
			for (User u : users)
				listModel.addElement(u);
	}

	public void AddUserProjectsToList(ArrayList<Project> projects) {
		MyProjectListModel listModel = (MyProjectListModel) userProjectList.getModel();
		listModel.removeAllElements();

		// update(getGraphics());
		if (projects != null)
			for (Project p : projects)
				listModel.addElement(p);
	}

	public void AddProjectsToList(ArrayList<Project> projects) {
		MyProjectListModel listModel = (MyProjectListModel) projectList.getModel();
		listModel.removeAllElements();

		// update(getGraphics());
		if (projects != null)
			for (Project p : projects)
				listModel.addElement(p);
	}

	public void ChangeProjectInfo(ProjectInfoGui projectInfo) {
		if (projectInfo == null)
			splitPaneProjects.setRightComponent(standardProjectPanelView);
		else
			splitPaneProjects.setRightComponent(projectInfo);
	}

	public void ChangeProjectsSummary(int progettiAttivi, int progettiTotali) {
		String s = String.format("Ci sono %d progetti attivi su un totale di %d progetti.", progettiAttivi,
				progettiTotali);
		projectSummaryLabel.setText(s);
	}
	

	public void AddActivitiesToList(ArrayList<Activity> activities) {
		MyActivityListModel listModel = (MyActivityListModel) activityList.getModel();
		listModel.removeAllElements();

		// update(getGraphics());
		if (activities != null)
			for (Activity a : activities)
				listModel.addElement(a);
	}

	
	
	public void ChangeActivityInfo(ActivityInfoGui act) {
		splitPaneActivity.setRightComponent(act);
	}

	public void ChangeActivitiesSummary(int attivitaCompletate, int attivitaTotali) {
		String s = String.format("Ci sono %d attività completate su un totale di %d attività.", attivitaCompletate,
				attivitaTotali);
		activitySummaryLabel.setText(s);
	}

	public void ChangeUserSummary(int activeUsers, int totalUsers)
	{
		String newText = String.format("Ci sono %d utenti attivi su un totale di %d", activeUsers, totalUsers);
		usersSummaryLabel.setText(newText); 
	}

}
