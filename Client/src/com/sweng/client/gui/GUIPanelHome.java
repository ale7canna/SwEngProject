package com.sweng.client.gui;

import java.awt.Dimension;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import com.sweng.common.beans.Activity;
import com.sweng.common.beans.Project;
import com.sweng.common.beans.User;
import com.sweng.common.notice.Notice;

public class GUIPanelHome extends JPanel {
	
	MyUserTableAdapter modelFriendship;
	MyActivityTableAdapter modelActivity;
	MyProjectTableAdapter modelProject;
	MyNoticeTableAdapter modelNotice;
	private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private JTextField Name;
	private JTextField Surname;
	private JTextField Id;
	private JTextField UserName;
	private JScrollPane scrollFriends;
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
		AddProjectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		AddProjectButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				listener.addProjectView();
			}
		});
		AddProjectButton.setBounds(311, 41, 89, 23);
		UserInfo.add(AddProjectButton);
		
		JButton addFriendButton = new JButton("Add Friends");
		addFriendButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				listener.addFriendsView();
			}
		});
		addFriendButton.setBounds(311, 120, 89, 23);
		UserInfo.add(addFriendButton);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				_listener.performLogout();
				
			}
		});
		btnLogout.setBounds(311, 173, 89, 23);
		UserInfo.add(btnLogout);
	
		modelFriendship = new MyUserTableAdapter();
		JTable tableFriends = new JTable(modelFriendship);
		
		tableFriends.setPreferredScrollableViewportSize(new Dimension(500, 70));
		tableFriends.setFillsViewportHeight(true);
		JScrollPane scrollPaneFriends = new JScrollPane(tableFriends);
	    scrollPaneFriends.setBounds(5, 218, 884, 194);
		
	    tabbedPane.add(scrollPaneFriends);
	    tabbedPane.setTitleAt(1, "My Friendship");
	    
	 
	    	tableFriends.addMouseListener(new MouseAdapter() {
		
			public void mousePressed(MouseEvent evt)
			{
				//TODO: E se la tabella degli amici è vuota? Da gestire
				//		Idem per progetti amicizie attività e notifiche
				//aggiunto controllo sul conteggio di riga!=-1 
				int row = tableFriends.rowAtPoint(evt.getPoint());
				if (evt.getClickCount() == 2&& row!=-1)
				{
					User u = ((MyUserTableAdapter)tableFriends.getModel()).getUserAt(row);
					int scelta = JOptionPane.showConfirmDialog(null, "Do you really want to remove from your friends "+ u.getName()+"?", "Remove friend", JOptionPane.YES_NO_OPTION);
					if (scelta == JOptionPane.YES_OPTION)
					{
						_listener.removeFriend(u);
						addFriendtoList(_listener.getFriends());
						JOptionPane.showMessageDialog(null, u.getUsername()+" removed from your friends");
						
					}
					else
						JOptionPane.showMessageDialog(null, "ciao");					
				}
			}
		});
	    
	    
		modelActivity = new MyActivityTableAdapter();
		JTable tableActivity = new JTable(modelActivity);
		tableActivity.setPreferredScrollableViewportSize(new Dimension(500, 70));
		tableActivity.setFillsViewportHeight(true);
		JScrollPane scrollPaneActivity = new JScrollPane(tableActivity);
	    scrollPaneActivity.setBounds(5, 218, 884, 194);
	    
	    tabbedPane.add(scrollPaneActivity);
	    tabbedPane.setTitleAt(2, "My Activities");
	    
	    
	    	tableActivity.addMouseListener(new MouseAdapter() {
			
	 			public void mousePressed(MouseEvent evt)
	 			{
	 				int row = tableActivity.rowAtPoint(evt.getPoint());
	 				if (evt.getClickCount() == 2 &&  row!=-1)
	 				{
	 					Activity a = ((MyActivityTableAdapter)tableActivity.getModel()).getActivityAt(row);
	 					_listener.showActivityInfo(a);
	 				}
	 			}
	 		});
	    
	    
		modelProject = new MyProjectTableAdapter();
		JTable tableProject = new JTable(modelProject);
		tableProject.setPreferredScrollableViewportSize(new Dimension(500, 70));
		tableProject.setFillsViewportHeight(true);
		JScrollPane scrollPaneProject = new JScrollPane(tableProject);
	    scrollPaneProject.setBounds(5, 218, 884, 194);
	    
	    tabbedPane.add(scrollPaneProject);
	    tabbedPane.setTitleAt(3, "My Projects");
	
	    
	    	tableProject.addMouseListener(new MouseAdapter() {
			
 			public void mousePressed(MouseEvent evt)
 			{
 				int row = tableProject.rowAtPoint(evt.getPoint());
 				if (evt.getClickCount() == 2 &&  row!=-1)
 				{
 					Project p = ((MyProjectTableAdapter)tableProject.getModel()).getProjectAt(row);
 					_listener.showProjectInfo(p);
 				}
 			}
 		});
	    
	    
	    
	    modelNotice = new MyNoticeTableAdapter();
		JTable tableNotice = new JTable(modelNotice);
		
		tableNotice.setPreferredScrollableViewportSize(new Dimension(500, 70));
		tableNotice.setFillsViewportHeight(true);
		JScrollPane scrollPaneNotice = new JScrollPane(tableNotice);
	    scrollPaneNotice.setBounds(5, 218, 884, 194);		
	    tabbedPane.add(scrollPaneNotice);
	    tabbedPane.setTitleAt(4, "My Notices");
	    
	   
	    	tableNotice.addMouseListener(new MouseAdapter() {
					
		 			public void mousePressed(MouseEvent evt)
		 			{
		 				int row = tableNotice.rowAtPoint(evt.getPoint());
		 				if (evt.getClickCount() == 2 &&  row!=-1)
		 				{
		 					Notice n= ((MyNoticeTableAdapter)tableNotice.getModel()).getNoticeAt(row);
		 					_listener.showNoticeInfo(n);
		 				}
		 			}
		 		});
	
	   
	}
	private void userInfo(User u){
		Name.setText(u.getName());
		Surname.setText(u.getSurname());
		Id.setText(String.valueOf(u.getIdUser()));
		UserName.setText(u.getUsername());
		
	}
	
	private void addFriendtoList(ArrayList<User> user){
		
		modelFriendship.removeAll();
		if(user==null){
			JOptionPane.showMessageDialog(null, "No user was selected, retry!");
			return;
			}
			
		for(User u : user){
			modelFriendship.addRow(u);			
		}
	}
	
	private void addActivitytoList(ArrayList<Activity> activity){
		
		modelActivity.removeAll();
		
		for(Activity a: activity){
			modelActivity.addRow(a);
		}
	}
	
	private void addProjecttoList(ArrayList<Project> project){
		
		modelProject.removeAll();
		
		for(Project p: project){
			modelProject.addRow(p);
		}
	}
	
	private void addNoticesToList(ArrayList<Notice> notices)
	{
		modelNotice.removeAll();
		
		for (Notice n : notices)
			modelNotice.addRow(n);
	}
	
	public void setUserInfo(User user, ArrayList<User> friendship, ArrayList<Activity> activity, ArrayList<Project> project, ArrayList<Notice> notices){
		
		if (user != null){
			userInfo(user);
		}
		
		if (friendship != null)
			addFriendtoList(friendship);
		if (activity != null)
			addActivitytoList(activity);
		if (project != null)
			addProjecttoList(project);
		if (notices != null)
			addNoticesToList(notices);
		
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
