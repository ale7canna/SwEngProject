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

public class ServerGUI extends JFrame {
	private JList userList, userProjectList, projectList;
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
		setSize(new Dimension(860, 455));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setSize(new Dimension(600, 400));
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panelStatistics = new JPanel();
		panelStatistics.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tabbedPane.addTab("Statistiche", null, panelStatistics, null);
		panelStatistics.setLayout(new BoxLayout(panelStatistics, BoxLayout.X_AXIS));
		
		JPanel panel_4 = new JPanel();
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.anchor = GridBagConstraints.NORTHWEST;
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 0;
		panelStatistics.add(panel_4);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		panel_4.add(lblNewLabel_1);
		
		JPanel panel_5 = new JPanel();
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.anchor = GridBagConstraints.NORTHWEST;
		gbc_panel_5.gridx = 0;
		gbc_panel_5.gridy = 0;
		panelStatistics.add(panel_5);
		
		JButton btnNewButton = new JButton("New button");
		panel_5.add(btnNewButton);

		JSplitPane UserSplitPane = new JSplitPane();
		tabbedPane.addTab("Utenti", null, UserSplitPane, null);

		JPanel panel = new JPanel();
		UserSplitPane.setLeftComponent(panel);

		userList = new JList(new MyUserListModel());

		userList.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				User u = (User) ((MyUserListModel) userList.getModel()).getUserAt(userList.getSelectedIndex());
				listener.UserClicked(u);
			}
		});
		panel.add(userList);

		JPanel panel_1 = new JPanel();
		UserSplitPane.setRightComponent(panel_1);

		UserSplitPane.setDividerLocation(200);

		userProjectList = new JList(new MyProjectListModel());
		userProjectList.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent evt) {
				Project p = (Project) ((MyProjectListModel) userProjectList.getModel())
						.getProjectAt(userProjectList.getSelectedIndex());
				listener.UserProjectClicked(p);
			}
		});
		;

		panel_1.add(userProjectList);
		
		JPanel progettiPane = new JPanel();
		tabbedPane.addTab("Progetti", null, progettiPane, null);
		progettiPane.setLayout(null);
		
		splitPaneProjects = new JSplitPane();
		splitPaneProjects.setBounds(0, 41, 833, 324);
		progettiPane.add(splitPaneProjects);
		
		projectList = new JList(new MyProjectListModel());
		projectList.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent evt) {
				Project p = (Project) ((MyProjectListModel) projectList.getModel())
						.getProjectAt(projectList.getSelectedIndex());
				listener.ProjectClicked(p);
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
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 0, 833, 41);
		progettiPane.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		projectSummaryLabel = new JLabel("");
		panel_2.add(projectSummaryLabel);
		;

	}

	public void AddUsersToList(ArrayList<User> users) {
		MyUserListModel listModel = (MyUserListModel) userList.getModel();

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
	
	public void ChangeProjectsSummary(int progettiAttivi, int progettiTotali)
	{
		String s = String.format("Ci sono %d progetti attivi su un totale di %d progetti.", progettiAttivi, progettiTotali);
		projectSummaryLabel.setText(s);
	}
}
