package com.sweng.client.gui;

import java.awt.List;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import com.sweng.common.beans.Activity;
import com.sweng.common.beans.User;

public class MyActivityTableAdapter extends AbstractTableModel {

	private ArrayList<String> columnNames = new ArrayList<String>();
	private ArrayList<ArrayList<Object>> data = new ArrayList<ArrayList<Object>>();

	private ArrayList<Activity> listActivity = null;

	public MyActivityTableAdapter() {
		columnNames.add("Name");
		columnNames.add("Place");
		columnNames.add("Date and Time");
		columnNames.add("Finishable");
	}

	public void addRow(Activity activity) {
		if (listActivity == null)
			listActivity = new ArrayList<Activity>();

		listActivity.add(activity);
		ArrayList<Object> rowData = new ArrayList<>();
		rowData.add(activity.getName());
		rowData.add(activity.getPlace());
		if (activity.getHour() != null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String ora = format.format(activity.getHour());
			rowData.add(ora);
		} else
			rowData.add("Date not set");
		rowData.add(activity.isFinishable());

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

		return (data.get(row)).get(col);
	}

	public void removeAll() {
		if (data != null)
			data.clear();
		if (listActivity != null)
			listActivity.clear();
		listActivity = null;
		super.fireTableDataChanged();
	}

	public Activity getActivityAt(int row) {
		if (listActivity != null && listActivity.size() >= row) {
			return listActivity.get(row);
		} else
			return null;
	}

	public boolean isCellEditable(int row, int col) {
		return false;
	}

	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();

	}

}
