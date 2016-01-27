package com.sweng.client.gui;

import java.awt.List;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.sweng.common.beans.Project;
import com.sweng.common.beans.User;

public class MyUserTableAdapter extends AbstractTableModel {

	private ArrayList<String> columnNames = new ArrayList();
	private ArrayList<List> data = new ArrayList();

	private ArrayList<User> listUser = null;

	public MyUserTableAdapter() {
		columnNames.add("Nome");
		columnNames.add("Cognome");
		columnNames.add("Username");
	}
	
	public void removeAll(){
		if(data!=null)
			data.clear();
		if(listUser!=null)
			listUser.clear();
		listUser = null;
		super.fireTableDataChanged();
	}

	public void addRow(User user) {
		if (listUser == null)
			listUser = new ArrayList<User>();

		listUser.add(user);
		List rowData = new List();
		rowData.add(user.getName());
		rowData.add(user.getSurname());
		rowData.add(user.getUsername());

		data.add(rowData);
		fireTableRowsInserted(data.size() - 1, data.size() - 1);
	}

	public int getColumnCount() {
		return columnNames.size();
	}

	public int getRowCount() {
		return data.size();
	}

	public String getColumnName(int col) {
		try {
			return columnNames.get(col);
		} catch (Exception e) {
			return null;
		}
	}

	public Object getValueAt(int row, int col) {

		return (data.get(row)).getItem(col);
	}
	
	public User getUserAt(int row)
	{
		if (listUser != null && listUser.size() >= row)
		{
			return listUser.get(row);
		}
		else
			return null;
	}

	public boolean isCellEditable(int row, int col) {
		return false;
	}

	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();

	}

}
