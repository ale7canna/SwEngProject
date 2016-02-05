package com.sweng.common.gui;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.sweng.common.notice.Notice;

public class NoticeInfoGui extends JPanel{
	private JTextField textNoticeTitle;
	private JTextField textNoticeDate;
	private JTextField textNoticeMessage;
	private JTextField textField;

	public NoticeInfoGui(Notice notice, ICommonGui _listener) {
		setLayout(null);
		try{
		UIManager.setLookAndFeel(
	            UIManager.getCrossPlatformLookAndFeelClassName());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		JPanel panel = new JPanel();
		panel.setSize(new Dimension(300, 300));
		panel.setToolTipText("NoticeInfo\r\n");
		panel.setBounds(new Rectangle(400, 400, 700, 700));
		panel.setBounds(0, 0, 450, 295);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblNoticesTitle = new JLabel("Title");
		lblNoticesTitle.setBounds(33, 40, 69, 14);
		panel.add(lblNoticesTitle);
		
		textNoticeTitle = new JTextField(notice.getTitle());
		textNoticeTitle.setEditable(false);
		textNoticeTitle.setBounds(126, 37, 150, 20);
		panel.add(textNoticeTitle);
		textNoticeTitle.setColumns(10);
		
		JLabel labelDateNotice = new JLabel("Date\r\n");
		labelDateNotice.setBounds(33, 100, 69, 14);
		panel.add(labelDateNotice);
		
		String ora = "";
		if(notice!=null)
		{
			
			if(notice.getDate()!=null)
				{
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					ora = format.format(notice.getDate());
				}else
					ora = "Date and Time not setted";
	
			}
		textNoticeDate = new JTextField(ora);
		textNoticeDate.setEditable(false);
		textNoticeDate.setColumns(10);
		textNoticeDate.setBounds(126, 97, 130, 20);
		panel.add(textNoticeDate);
		
		
		JLabel lblMessage = new JLabel("Message\r\n");
		lblMessage.setBounds(33, 128, 69, 14);
		panel.add(lblMessage);
		
		
		textNoticeMessage = new JTextField(notice.getMessage());
		textNoticeMessage.setEditable(false);
		textNoticeMessage.setColumns(10);
		textNoticeMessage.setBounds(126, 128, 250, 106);
		panel.add(textNoticeMessage);
		
		JButton btnSetNoticeRed = new JButton("Set notice read");
		btnSetNoticeRed.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int scelta = JOptionPane.showConfirmDialog(null, "Do you really want to set the notice "+ notice.getTitle()+"already read?", "Set Read Notice", JOptionPane.YES_NO_OPTION);
				if (scelta == JOptionPane.YES_OPTION)
					_listener.refreshAll();
//					_listener.setNoticeRed(notice);
			}
		});
		btnSetNoticeRed.setBounds(327, 36, 99, 23);
		panel.add(btnSetNoticeRed);
		
		JLabel labelClass = new JLabel("Type");
		labelClass.setBounds(33, 72, 69, 14);
		panel.add(labelClass);
		
		textField = new JTextField(notice.getClass().getName());
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setBounds(126, 69, 200, 20);
		panel.add(textField);
		
		
		
	}
}
