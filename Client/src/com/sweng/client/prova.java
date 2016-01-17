package com.sweng.client;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.List;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;

import com.sweng.client.CheckBoxList.CellRenderer;
import com.sweng.common.beans.User;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class prova {
	

	public static class MyTableModel extends AbstractTableModel
	{
	    private ArrayList<String> columnNames = new ArrayList();
	    private ArrayList<List> data = new ArrayList();

	    {
	        columnNames.add("Id");
	        columnNames.add("UserName");
	        columnNames.add("Checked");
	      
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
	};
	
	static MyTableModel model;
	public static JCheckBox checkbox;
	
	
	
	
	public ArrayList<CheckBoxId> createCheckboxList(ArrayList<User> lista){
		ArrayList<CheckBoxId> listacheckbox = new ArrayList<CheckBoxId>();
		for (User u : lista){
			String userName = u.getUsername();
			int id = u.getIdUser();
			checkbox = new CheckBoxId(id, userName);
						
			listacheckbox.add((CheckBoxId) checkbox);
		}
		return listacheckbox;
	}
	
	
	
	
	
	public static void main(String[] args)
	{
	    model = new MyTableModel();
	 
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setSize(new Dimension(300, 200));
		scrollPane.setBounds(0, 0, 434, 261);
		
	
		
		//ArrayList<CheckBoxId> checkboxList = createCheckboxList(friends, friends1);
			
		CheckBoxList listFriends = new CheckBoxList();

//		for (JCheckBox c : checkboxList){
//				listFriends.addCheckbox((CheckBoxId) c);		
//				}
		
		

		scrollPane.setRowHeaderView(listFriends);
		//now adding this to the frame where I want to show 
	    JFrame frame = new JFrame();
	    frame.getContentPane().setLayout(null);
	    frame.getContentPane().add(scrollPane);
	    
	    JButton viewButton = new JButton("view\r\n");
	    viewButton.setSize(new Dimension(100, 100));
	    viewButton.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent arg0) {
	    		ArrayList<Integer> id = listFriends.getSelectedItems();
	    		
	    		for(Integer i : id){
	    			System.out.println(i);
	    		}
	    	}
	    });
	    scrollPane.setViewportView(viewButton);
//	    comboBox.setBounds(0, 0, 0, 0);
//	    frame.getContentPane().add(comboBox);
//	    comboBox.setModel(new DefaultComboBoxModel(new String[] {"ciao"}));
	    frame.setVisible(true);
	    
	    
	    {	/*	ArrayList<Integer> friends = new ArrayList<Integer>();
			friends.add(1);
			friends.add(2);
			friends.add(3);
			
			ArrayList<String> friends1 = new ArrayList<String>();
			friends1.add("Giorgio");
			friends1.add("Edo");
			friends1.add("ale");*/
			
			
		   /* List l = new List();
		    l.add( "yi",1);
		    l.add("chen", 2);
		    l.add("sleep", 3);
		    l.add("35", 4);
		    l.add("false", 5);
		    
		   
		    model.addRow(l);*/
		    
		   /* JTable table = new JTable(model);
		    table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		    table.setFillsViewportHeight(true);*/
			   
		   }
	}
	
	 

	
	
	

}
