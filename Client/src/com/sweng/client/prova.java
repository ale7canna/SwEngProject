package com.sweng.client;

import java.awt.Dimension;
import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class prova {
	public static class MyTableModel extends AbstractTableModel
	{
	    private ArrayList<String> columnNames = new ArrayList();
	    private ArrayList<List> data = new ArrayList();

	    {
	        columnNames.add("First Name");
	        columnNames.add("Last Name");
	        columnNames.add("Sport");
	        columnNames.add("# of Years");
	        columnNames.add("Vegetarian");
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

	public static void main(String[] args)
	{
	    MyTableModel model = new MyTableModel();
	 
	    List l = new List();
	    l.add( "yi",1);
	    l.add("chen", 2);
	    l.add("sleep", 3);
	    l.add("35", 4);
	    l.add("false", 5);
	    
	   
	    model.addRow(l);
	    
	    JTable table = new JTable(model);
	    table.setPreferredScrollableViewportSize(new Dimension(500, 70));
	    table.setFillsViewportHeight(true);

	    JScrollPane scrollPane = new JScrollPane(table);
	    scrollPane.setBounds(5, 218, 884, 194);
	    //now adding this to the frame where I want to show 
	    JFrame frame = new JFrame();
	    frame.add(scrollPane);
	    frame.setVisible(true);
	}
}
