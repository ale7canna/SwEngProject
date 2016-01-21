package com.sweng.server.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import com.sweng.common.beans.Project;
import com.sweng.common.beans.ProjectInfo;
import com.sweng.common.beans.User;
import com.sweng.common.gui.MyProjectListModel;
import com.sweng.common.gui.MyUserListModel;
import com.sweng.common.gui.ProjectInfoGui;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.border.BevelBorder;
import javax.swing.ListModel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;

public class ServerGUI extends JFrame {
	private JList userList, userProjectList, projectList, listFriends;
	private JPanel projectInfo;
	private JSplitPane splitPaneProjects;
	private JLabel projectSummaryLabel;
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
		setSize(new Dimension(860, 490));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setSize(new Dimension(600, 400));
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel tabStatistics = new JPanel();
		tabStatistics.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tabbedPane.addTab("Statistiche", null, tabStatistics, null);
		tabStatistics.setLayout(new BoxLayout(tabStatistics, BoxLayout.X_AXIS));

		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.anchor = GridBagConstraints.NORTHWEST;
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 0;
		tabStatistics.add(panel_4);

		JLabel lblNewLabel_1 = new JLabel("New label");
		panel_4.add(lblNewLabel_1);

		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.anchor = GridBagConstraints.NORTHWEST;
		gbc_panel_5.gridx = 0;
		gbc_panel_5.gridy = 0;
		tabStatistics.add(panel_5);

		JButton btnNewButton = new JButton("New button");
		panel_5.add(btnNewButton);

		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		tabStatistics.add(panel_6);

		JLabel lblNewLabel = new JLabel("New label");
		panel_6.add(lblNewLabel);
		;

		JPanel tabUtente = new JPanel();
		tabbedPane.addTab("Utenti", null, tabUtente, null);
		tabUtente.setLayout(new GridLayout(0, 3, 0, 0));

		JPanel panelUser = new JPanel();
		panelUser.setAlignmentY(Component.TOP_ALIGNMENT);
		panelUser.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelUser.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		tabUtente.add(panelUser);

		userList = new JList(new MyUserListModel());
		userList.setAlignmentY(Component.TOP_ALIGNMENT);
		userList.setAlignmentX(Component.LEFT_ALIGNMENT);

		userList.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				User u = (User) ((MyUserListModel) userList.getModel()).getUserAt(userList.getSelectedIndex());
				listener.UserClicked(u);
			}
		});
		panelUser.setLayout(new BoxLayout(panelUser, BoxLayout.Y_AXIS));

		JLabel lblNewLabel_2 = new JLabel("Utenti");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblNewLabel_2.setAlignmentY(Component.TOP_ALIGNMENT);
		panelUser.add(lblNewLabel_2);
		panelUser.add(userList);

		JPanel panelUserProject = new JPanel();
		panelUserProject.setAlignmentY(Component.TOP_ALIGNMENT);
		panelUserProject.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelUserProject.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		tabUtente.add(panelUserProject);

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
		panelUserProject.setLayout(new BoxLayout(panelUserProject, BoxLayout.Y_AXIS));

		JLabel lblNewLabel_3 = new JLabel("Progetti");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblNewLabel_3.setAlignmentY(Component.TOP_ALIGNMENT);
		panelUserProject.add(lblNewLabel_3);

		panelUserProject.add(userProjectList);

		JPanel panelUserFriends = new JPanel();
		panelUserFriends.setAlignmentY(Component.TOP_ALIGNMENT);
		panelUserFriends.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelUserFriends.setBorder(new LineBorder(new Color(0, 0, 0)));
		tabUtente.add(panelUserFriends);
		panelUserFriends.setLayout(new BoxLayout(panelUserFriends, BoxLayout.Y_AXIS));

		JLabel lblNewLabel_4 = new JLabel("Amici");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblNewLabel_4.setAlignmentY(Component.TOP_ALIGNMENT);
		panelUserFriends.add(lblNewLabel_4);

		listFriends = new JList(new MyUserListModel());
		listFriends.setAlignmentY(Component.TOP_ALIGNMENT);
		listFriends.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelUserFriends.add(listFriends);

		JPanel tabProgetti = new JPanel();
		tabbedPane.addTab("Progetti", null, tabProgetti, null);
		GridBagLayout gbl_tabProgetti = new GridBagLayout();
		gbl_tabProgetti.columnWidths = new int[] { 833, 0 };
		gbl_tabProgetti.rowHeights = new int[] { 41, 324, 0 };
		gbl_tabProgetti.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_tabProgetti.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		tabProgetti.setLayout(gbl_tabProgetti);

		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 0;
		tabProgetti.add(panel_2, gbc_panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		projectSummaryLabel = new JLabel("");
		panel_2.add(projectSummaryLabel);

		splitPaneProjects = new JSplitPane();
		GridBagConstraints gbc_splitPaneProjects = new GridBagConstraints();
		gbc_splitPaneProjects.fill = GridBagConstraints.BOTH;
		gbc_splitPaneProjects.gridx = 0;
		gbc_splitPaneProjects.gridy = 1;
		tabProgetti.add(splitPaneProjects, gbc_splitPaneProjects);

		projectList = new JList(new MyProjectListModel());
		projectList.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 1) {
					Project p = (Project) ((MyProjectListModel) projectList.getModel())
							.getProjectAt(projectList.getSelectedIndex());
					listener.ProjectClicked(p);
				}
			}
		});
		splitPaneProjects.setLeftComponent(projectList);

		JPanel panel_3 = new JPanel();
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		splitPaneProjects.setRightComponent(panel_3);

		JLabel label = new JLabel("Clicca un progetto a lato per caricare i dettagli");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setAlignmentX(0.5f);
		panel_3.add(label);
		splitPaneProjects.setDividerLocation(200);

	}

	public void AddUsersToList(ArrayList<User> users) {
		MyUserListModel listModel = (MyUserListModel) userList.getModel();

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
		splitPaneProjects.setRightComponent(projectInfo);
	}

	public void ChangeProjectsSummary(int progettiAttivi, int progettiTotali) {
		String s = String.format("Ci sono %d progetti attivi su un totale di %d progetti.", progettiAttivi,
				progettiTotali);
		projectSummaryLabel.setText(s);
	}
}
