package com.sweng.client;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.sweng.client.prova.MyTableModel;
import com.sweng.common.beans.Activity;
import com.sweng.common.beans.Friendship;
import com.sweng.common.beans.Project;
import com.sweng.common.beans.User;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

import java.awt.Dimension;
import java.awt.List;

import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUIPanelHome extends JPanel {
	
	MyTableModel modelFriendship;
	MyTableModel modelActivity;
	MyTableModel modelProject;
	private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private JTextField Name;
	private JTextField Surname;
	private JTextField Id;
	private JTextField UserName;
	private JScrollPane scrollFriends;
	private JTable table;
	private EventListenerGUI listener;
	
	public GUIPanelHome(EventListenerGUI _listener) {
		
		listener = _listener;
		
		setLayout(null);
		tabbedPane.setBounds(10, 0, 511, 269);
		add(tabbedPane);
		
		JPanel UserInfo = new JPanel();
		tabbedPane.addTab("User Info", null, UserInfo, null);
		UserInfo.setLayout(null);
		
		Name = new JTextField();
		Name.setEditable(false);
		Name.setBounds(114, 26, 86, 20);
		UserInfo.add(Name);
		Name.setColumns(10);
		
		Surname = new JTextField();
		Surname.setEditable(false);
		Surname.setBounds(114, 70, 86, 20);
		UserInfo.add(Surname);
		Surname.setColumns(10);
		
		Id = new JTextField();
		Id.setEditable(false);
		Id.setBounds(114, 123, 86, 20);
		UserInfo.add(Id);
		Id.setColumns(10);
		
		UserName = new JTextField();
		UserName.setEditable(false);
		UserName.setBounds(114, 174, 86, 20);
		UserInfo.add(UserName);
		UserName.setColumns(10);
		
		JLabel NameLabel = new JLabel("Name");
		NameLabel.setBounds(10, 29, 46, 14);
		UserInfo.add(NameLabel);
		
		JLabel SurnameLabel = new JLabel("Surname\r\n");
		SurnameLabel.setBounds(10, 73, 46, 14);
		UserInfo.add(SurnameLabel);
		
		JLabel IdLabel = new JLabel("Id");
		IdLabel.setBounds(10, 126, 46, 14);
		UserInfo.add(IdLabel);
		
		JLabel UserNameLabel = new JLabel("UserName");
		UserNameLabel.setBounds(10, 177, 65, 14);
		UserInfo.add(UserNameLabel);
		
		JButton AddProjectButton = new JButton("Add Project\r\n");
		AddProjectButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				listener.addProject();
			}
		});
		AddProjectButton.setBounds(311, 25, 89, 23);
		UserInfo.add(AddProjectButton);
		
		JButton addFriendButton = new JButton("Add Friends");
		addFriendButton.setBounds(311, 120, 89, 23);
		UserInfo.add(addFriendButton);
		
	
		ArrayList<String> titleFriends = new ArrayList<String>()
				{{
			add("Nome");
			add("Cognome");
			add("Id");
			add("UserName");
		}};

		modelFriendship = new MyTableModel(titleFriends);
		JTable tableFriends = new JTable(modelFriendship);
		tableFriends.setPreferredScrollableViewportSize(new Dimension(500, 70));
		tableFriends.setFillsViewportHeight(true);
		JScrollPane scrollPaneFriends = new JScrollPane(tableFriends);
	    scrollPaneFriends.setBounds(5, 218, 884, 194);
		
	    tabbedPane.add(scrollPaneFriends);
	    tabbedPane.setTitleAt(1, "My Friendship");
	    
	    
	    ArrayList<String> titleActivity = new ArrayList<String>()
				{{
			add("Nome");
			add("Luogo");
			add("Ora");
			add("IdAttività");
			add("IdProgetto");
		}};
		
		modelActivity = new MyTableModel(titleActivity);
		JTable tableActivity = new JTable(modelActivity);
		tableActivity.setPreferredScrollableViewportSize(new Dimension(500, 70));
		tableActivity.setFillsViewportHeight(true);
		JScrollPane scrollPaneActivity = new JScrollPane(tableActivity);
	    scrollPaneActivity.setBounds(5, 218, 884, 194);
	    
	    tabbedPane.add(scrollPaneActivity);
	    tabbedPane.setTitleAt(2, "My Activities");
	    
	    ArrayList<String> titleProject = new ArrayList<String>()
				{{
			add("Nome");
			add("Admin");
			add("Id");
			add("Attivo");
		}};
		
		modelProject = new MyTableModel(titleProject);
		JTable tableProject = new JTable(modelProject);
		tableProject.setPreferredScrollableViewportSize(new Dimension(500, 70));
		tableProject.setFillsViewportHeight(true);
		JScrollPane scrollPaneProject = new JScrollPane(tableProject);
	    scrollPaneProject.setBounds(5, 218, 884, 194);
	    
	    tabbedPane.add(scrollPaneProject);
	    tabbedPane.setTitleAt(3, "My Projects");
	
	}
	
	private void userInfo(User u){
		Name.setText(u.getName());
		Surname.setText(u.getSurname());
		Id.setText(String.valueOf(u.getIdUser()));
		UserName.setText(u.getUsername());
		
	}
	
	private void addFriendtoList(ArrayList<User> user){
		
	    int k = 0;
		for(User u : user){
			List l = new List();
			l.add(u.getName());
			l.add(u.getSurname());
			l.add(String.valueOf(u.getIdUser()));
			l.add(u.getUsername());
			
			modelFriendship.addRow(l);			
		}
	}
	
	private void addActivitytoList(ArrayList<Activity> activity){
		for(Activity a: activity){
			List lActivity = new List();
			lActivity.add(a.getName(), 1);
			lActivity.add(a.getPlace(), 2);
			lActivity.add(String.valueOf(a.getHour()), 3);
			lActivity.add(String.valueOf(a.getIdActivity()), 4);
			lActivity.add(String.valueOf(a.getIdProject()), 5);
			
			modelActivity.addRow(lActivity);
		}
	}
	
	private void addProjecttoList(ArrayList<Project> project){
		for(Project p: project){
			List lProject = new List();
			lProject.add(p.getName(), 1);
			lProject.add(String.valueOf(p.getIdAdmin()), 2);
			lProject.add(String.valueOf(p.getIdProject()), 3);
			lProject.add(String.valueOf(p.isActive()), 4);
			
			modelProject.addRow(lProject);
		}
	}
	
	public void setUserInfo(User user, ArrayList<User> friendship, ArrayList<Activity> activity, ArrayList<Project> project){
		
		if (user != null)
			userInfo(user);
		if (friendship != null)
			addFriendtoList(friendship);
		if (activity != null)
			addActivitytoList(activity);
		if (project != null)
			addProjecttoList(project);
		
	}
	
	
	class MyTableModel extends AbstractTableModel{

	    private ArrayList<String> columnNames = new ArrayList();
	    private ArrayList<List> data = new ArrayList();

	   
	    public MyTableModel(ArrayList<String> titoli) {
	    	for (String t : titoli){
	    		columnNames.add(t);   	       
	    	}
		}

	    public void addRow(List rowData)
	    {
	        data.add(rowData);
	        fireTableRowsInserted(data.size() - 1, data.size() - 1);
	    }

	    public int getColumnCount()
	    {
	        return columnNames.size();
	    }

	    public int getRowCount()
	    {
	        return data.size();
	    }

	    public String getColumnName(int col)
	    {
	        try
	        {
	            return columnNames.get(col);
	        }
	        catch(Exception e)
	        {
	            return null;
	        }
	    }

	   public Object getValueAt(int row, int col)
	    {
			
	        return (data.get(row)).getItem(col);
	        
	    }

	    public boolean isCellEditable(int row, int col)
	    {
	        return false;
	    }

	    public Class getColumnClass(int c)
	    {
	        return getValueAt(0, c).getClass();
	    
	    	}
	}
}
