package com.sweng.client;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.sweng.client.prova.MyTableModel;
import com.sweng.common.beans.Activity;
import com.sweng.common.beans.Project;
import com.sweng.common.beans.User;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

import java.awt.Dimension;
import java.awt.List;

public class GUIPanelHome extends JPanel {
	
	MyTableModel model;
	private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private JTextField Name;
	private JTextField Surname;
	private JTextField Id;
	private JTextField UserName;
	private JScrollPane scrollFriends;
	private JTable table;
	
	public GUIPanelHome() {
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
		
	
		ArrayList<String> titleFriends = new ArrayList<String>()
				{{
			add("Nome");
			add("Cognome");
			add("Id");
			add("UserName");
		}};

		model = new MyTableModel(titleFriends);
		JTable tableFriends = new JTable(model);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		JScrollPane scrollPaneFriends = new JScrollPane(table);
	    scrollPaneFriends.setBounds(5, 218, 884, 194);
		
	    
	    
	    ArrayList<String> titleActivity = new ArrayList<String>()
				{{
			add("Nome");
			add("Luogo");
			add("Ora");
			add("IdAttività");
			add("IdProgetto");
		}};
		
		model = new MyTableModel(titleActivity);
		JTable tableActivity = new JTable(model);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		JScrollPane scrollPaneActivity = new JScrollPane(table);
	    scrollPaneActivity.setBounds(5, 218, 884, 194);
	    
	    
	    ArrayList<String> titleEvents = new ArrayList<String>()
				{{
			add("Nome");
			add("Admin");
			add("Id");
			add("Attivo");
		}};
		
		model = new MyTableModel(titleEvents);
		JTable tableProject = new JTable(model);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		JScrollPane scrollPaneProject = new JScrollPane(table);
	    scrollPaneProject.setBounds(5, 218, 884, 194);
	
	}
	
	public void userInfo(User u){
		Name.setText(u.getName());
		Surname.setText(u.getSurname());
		Id.setText(String.valueOf(u.getIdUser()));
		UserName.setText(u.getUsername());
		
	}
	
	public void addFriendtoList(ArrayList<User> user){
		
	    List l = new List();
		for(User u : user){
			l.add(u.getName(), 1);
			l.add(u.getSurname(), 2);
			l.add(String.valueOf(u.getIdUser()), 3);
			l.add(u.getUsername(), 4);
			
			model.addRow(l);
			
		}
	}
	
	public void addActivitytoList(ArrayList<Activity> activity){
		List lActivity = new List();
		for(Activity a: activity){
			lActivity.add(a.getName(), 1);
			lActivity.add(a.getPlace(), 2);
			lActivity.add(String.valueOf(a.getHour()), 3);
			lActivity.add(String.valueOf(a.getIdActivity()), 4);
			lActivity.add(String.valueOf(a.getIdProject()), 5);
			
			model.addRow(lActivity);
		}
	}
	
	public void addProjecttoList(ArrayList<Project> project){
		List lProject = new List();
		for(Project p: project){
			lProject.add(p.getName(), 1);
			lProject.add(String.valueOf(p.getIdAdmin()), 2);
			lProject.add(String.valueOf(p.getIdProject()), 3);
			lProject.add(String.valueOf(p.isActive()), 4);
			
		}
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
