package com.sweng.client;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTabbedPane;
import java.awt.Dimension;

public class GUI extends JFrame {
	public GUI() {
	}
	
	public void switchGui(JPanel panel)
	{
		getContentPane().removeAll();
		getContentPane().add(panel);
	}
}
