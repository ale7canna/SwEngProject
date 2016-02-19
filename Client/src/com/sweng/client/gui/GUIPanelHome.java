package com.sweng.client.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;

import org.eclipse.wb.swing.FocusTraversalOnArray;

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
	private JTable tableNotice;
	
	public GUIPanelHome(EventListenerGUI _listener) {
		setFont(new Font("Yu Gothic", Font.BOLD, 13));
		setForeground(Color.LIGHT_GRAY);
		setBorder(UIManager.getBorder("FormattedTextField.border"));
		
		listener = _listener;
		
		setLayout(null);
		tabbedPane.setBounds(10, 0, 511, 269);
		add(tabbedPane);
		
		JPanel UserInfo = new JPanel();
		tabbedPane.addTab("User Info", null, UserInfo, null);
		
		Name = new JTextField();
		Name.setEditable(false);
		Name.setColumns(10);
		
		Surname = new JTextField();
		Surname.setEditable(false);
		Surname.setColumns(10);
		
		Id = new JTextField();
		Id.setEditable(false);
		Id.setColumns(10);
		
		UserName = new JTextField();
		UserName.setEditable(false);
		UserName.setColumns(10);
		
		JLabel NameLabel = new JLabel("Name");
		
		JLabel SurnameLabel = new JLabel("Surname\r\n");
		
		JLabel IdLabel = new JLabel("Id");
		
		JLabel UserNameLabel = new JLabel("UserName");
		
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
		
		JButton addFriendButton = new JButton("Add Friends");
		addFriendButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				listener.addFriendsView();
			}
		});
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				_listener.performLogout();
				
			}
		});
		GroupLayout gl_UserInfo = new GroupLayout(UserInfo);
		gl_UserInfo.setHorizontalGroup(
			gl_UserInfo.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_UserInfo.createSequentialGroup()
					.addGroup(gl_UserInfo.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_UserInfo.createSequentialGroup()
							.addGap(21)
							.addComponent(AddProjectButton, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
							.addGap(30)
							.addComponent(addFriendButton, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
							.addGap(109)
							.addComponent(btnLogout, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_UserInfo.createSequentialGroup()
							.addGap(10)
							.addGroup(gl_UserInfo.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(gl_UserInfo.createSequentialGroup()
									.addComponent(SurnameLabel, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
									.addGap(50)
									.addComponent(Surname))
								.addGroup(Alignment.LEADING, gl_UserInfo.createSequentialGroup()
									.addComponent(NameLabel, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
									.addGap(69)
									.addComponent(Name, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)))
							.addGap(44)
							.addGroup(gl_UserInfo.createParallelGroup(Alignment.LEADING)
								.addComponent(IdLabel, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
								.addComponent(UserNameLabel, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_UserInfo.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_UserInfo.createSequentialGroup()
									.addGap(21)
									.addComponent(Id, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE))
								.addGroup(Alignment.TRAILING, gl_UserInfo.createSequentialGroup()
									.addGap(21)
									.addComponent(UserName, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)))))
					.addContainerGap())
		);
		gl_UserInfo.setVerticalGroup(
			gl_UserInfo.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_UserInfo.createSequentialGroup()
					.addGap(26)
					.addGroup(gl_UserInfo.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_UserInfo.createSequentialGroup()
							.addGap(3)
							.addComponent(NameLabel))
						.addGroup(gl_UserInfo.createSequentialGroup()
							.addGap(3)
							.addComponent(IdLabel))
						.addComponent(Name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(Id, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(24)
					.addGroup(gl_UserInfo.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_UserInfo.createSequentialGroup()
							.addGap(3)
							.addComponent(SurnameLabel))
						.addGroup(gl_UserInfo.createSequentialGroup()
							.addGap(3)
							.addComponent(UserNameLabel))
						.addComponent(Surname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(UserName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(117)
					.addGroup(gl_UserInfo.createParallelGroup(Alignment.LEADING)
						.addComponent(AddProjectButton)
						.addComponent(addFriendButton)
						.addComponent(btnLogout))
					.addContainerGap())
		);
		UserInfo.setLayout(gl_UserInfo);
	
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
						JOptionPane.showMessageDialog(null, u.getUsername() + " was not removed from your friends");					
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
		tableNotice = new JTable(modelNotice);
		tableNotice.setForeground(Color.BLACK);
		
		tableNotice.setPreferredScrollableViewportSize(new Dimension(500, 70));
		tableNotice.setFillsViewportHeight(true);
		JScrollPane scrollPaneNotice = new JScrollPane(tableNotice);
	    scrollPaneNotice.setBounds(5, 218, 884, 194);		
	    tabbedPane.add(scrollPaneNotice);
	   
	   
	    tabbedPane.setTitleAt(4, "My Notices");
	    
	    
	    
	    JLabel lblDoubleClickingOn = new JLabel("Double clicking on a row of the tables to see more info");
	    lblDoubleClickingOn.setFont(new Font("Trebuchet MS", Font.ITALIC, 10));
	    lblDoubleClickingOn.setBounds(20, 275, 408, 14);
	    add(lblDoubleClickingOn);
	    setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{tabbedPane, UserInfo, Name, Surname, Id, UserName, NameLabel, SurnameLabel, IdLabel, UserNameLabel, AddProjectButton, addFriendButton, btnLogout, scrollPaneFriends, tableFriends, scrollPaneActivity, tableActivity, scrollPaneProject, tableProject, scrollPaneNotice, tableNotice, lblDoubleClickingOn}));
	    
	   
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
		if(notices.size()>0){
			setRedTab();
		}
		else
			setWhiteTab();
		
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

	    private ArrayList<String> columnNames = new ArrayList<String>();
	    private ArrayList<List> data = new ArrayList<List>();
	   
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



	
	public void setWhiteTab() {
		 tabbedPane.setBackgroundAt(4, new Color(240, 240, 240));
    	 tabbedPane.setToolTipTextAt(4, "New Notices Available\r\n");
	}


	public void setRedTab() {
		 tabbedPane.setBackgroundAt(4, Color.RED);
    	 tabbedPane.setToolTipTextAt(4, "New Notices Available\r\n");
		
	}
}
