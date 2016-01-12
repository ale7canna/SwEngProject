package com.sweng.client;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JList;

public class GUIaddProject extends JFrame{
	private JTextField textField;
	public GUIaddProject() {
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Project Name\r\n");
		lblNewLabel.setBounds(10, 21, 83, 14);
		getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(84, 18, 86, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(178, 21, 226, 168);
		getContentPane().add(scrollPane);
		
		CheckBoxList list = new CheckBoxList();
		list.setBounds(180, 20, 224, 166);
		getContentPane().add(list);
	}
}
