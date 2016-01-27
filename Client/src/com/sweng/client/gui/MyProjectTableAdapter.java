package com.sweng.client.gui;

import java.awt.List;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.sweng.common.beans.Project;
import com.sweng.common.beans.User;

public class MyProjectTableAdapter extends AbstractTableModel {

	private ArrayList<String> columnNames = new ArrayList();
	private ArrayList<List> data = new ArrayList();

	private ArrayList<Project> listProject = null;

	public MyProjectTableAdapter() {
		columnNames.add("Nome");
		columnNames.add("IdProgetto");
		columnNames.add("IdAdmin");
	}

	public void addRow(Project project) {
		if (listProject == null)
			listProject = new ArrayList<Project>();

		listProject.add(project);
		List rowData = new List();
		rowData.add(project.getName());
		rowData.add(String.valueOf(project.getIdProject()));
		rowData.add(String.valueOf(project.getIdAdmin()));

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
	
	public void removeAll(){
		if(data!=null)
			data.clear();
		if(listProject!=null)
			listProject.clear();
		listProject= null;
		super.fireTableDataChanged();
	}

	public Object getValueAt(int row, int col) {

		return (data.get(row)).getItem(col);
	}
	
	public Project getProjectAt(int row)
	{
		if (listProject != null && listProject.size() >= row)
		{
			return listProject.get(row);
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
