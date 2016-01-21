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
import javax.swing.BoxLayout;

public class ServerGUI extends JFrame {
	private JList userList, userProjectList, projectList;
	private JSplitPane projectSplittable;
	private JPanel projectInfo;
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
		
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("Statistiche", null, panel_3, null);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));

		JSplitPane splitPane = new JSplitPane();
		tabbedPane.addTab("Utenti", null, splitPane, null);

		JPanel panel = new JPanel();
		splitPane.setLeftComponent(panel);

		userList = new JList(new MyUserListModel());

		userList.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				User u = (User) ((MyUserListModel) userList.getModel()).getUserAt(userList.getSelectedIndex());
				listener.UserClicked(u);
			}
		});
		panel.add(userList);

		JPanel panel_1 = new JPanel();
		splitPane.setRightComponent(panel_1);

		splitPane.setDividerLocation(200);

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

		projectSplittable = new JSplitPane();
		tabbedPane.addTab("Progetti", null, projectSplittable, null);

		projectList = new JList(new MyProjectListModel());
		projectList.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent evt) {
				Project p = (Project) ((MyProjectListModel) projectList.getModel())
						.getProjectAt(projectList.getSelectedIndex());
				listener.ProjectClicked(p);
			}
		});
		;

		projectSplittable.setLeftComponent(projectList);

		JPanel panel_2 = new JPanel();
		projectSplittable.setRightComponent(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel = new JLabel("Clicca un progetto a lato per caricare i dettagli");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_2.add(lblNewLabel);
		projectSplittable.setDividerLocation(200);

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
		projectSplittable.setRightComponent(projectInfo);
	}
}
