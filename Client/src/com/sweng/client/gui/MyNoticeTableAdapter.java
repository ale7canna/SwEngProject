package com.sweng.client.gui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.sweng.common.beans.Activity;
import com.sweng.common.notice.Notice;

public class MyNoticeTableAdapter extends AbstractTableModel{
	
	private ArrayList<String> columnNames = new ArrayList<String>();
	private ArrayList<ArrayList<Object>> data = new ArrayList<ArrayList<Object>>();

	private ArrayList<Notice> listNotice = null;

	public MyNoticeTableAdapter() {
		columnNames.add("Title");
		columnNames.add("Date");
		columnNames.add("Message");
	}

	public void addRow(Notice notice) {
		if (listNotice== null)
			listNotice= new ArrayList<Notice>();

		listNotice.add(notice);
		ArrayList<Object> rowData = new ArrayList<>();
		rowData.add(notice.getTitle());
		
		if (notice.getDate() != null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String ora = format.format(notice.getDate());
			rowData.add(ora);
		} else
			rowData.add("Date not set");
		rowData.add(notice.getMessage());

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
		if (listNotice != null)
			listNotice.clear();
		listNotice = null;
		super.fireTableDataChanged();
	}

	public Notice getNoticeAt(int row) {
		if (listNotice != null && listNotice.size() >= row) {
			return listNotice.get(row);
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
