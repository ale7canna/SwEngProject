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
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class NoticeInfoGui extends JPanel{
	private JTextField textNoticeTitle;
	private JTextField textNoticeDate;
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
		panel.setBounds(0, 0, 599, 389);
		add(panel);
		
		JLabel lblNoticesTitle = new JLabel("Title");
		
		textNoticeTitle = new JTextField(notice.getTitle());
		textNoticeTitle.setEditable(false);
		textNoticeTitle.setColumns(10);
		
		JLabel labelDateNotice = new JLabel("Date\r\n");
		
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
		
		
		JLabel lblMessage = new JLabel("Message\r\n");
		
		JButton btnSetNoticeRed = new JButton("Set notice read");
		btnSetNoticeRed.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int scelta = JOptionPane.showConfirmDialog(null, "Do you really want to set the notice "+ notice.getTitle()+"already read?", "Set Read Notice", JOptionPane.YES_NO_OPTION);
				if (scelta == JOptionPane.YES_OPTION)
					_listener.refreshAll();
					_listener.setNoticeRead(notice);
			}
		});
		
		JLabel labelClass = new JLabel("Type");
		
		textField = new JTextField(notice.getClass().getName());
		textField.setEditable(false);
		textField.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(33)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblNoticesTitle, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
							.addGap(24)
							.addComponent(textNoticeTitle, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE)
							.addGap(124)
							.addComponent(btnSetNoticeRed, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblMessage, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
								.addComponent(labelClass, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
								.addComponent(labelDateNotice, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))
							.addGap(24)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(panel_1, 0, 0, Short.MAX_VALUE)
								.addComponent(textField, GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
								.addComponent(textNoticeDate, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(27, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(21)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(19)
									.addComponent(lblNoticesTitle))
								.addComponent(btnSetNoticeRed)))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(37)
							.addComponent(textNoticeTitle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(labelClass))
					.addGap(15)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textNoticeDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(labelDateNotice))
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(18)
							.addComponent(lblMessage))
						.addGroup(gl_panel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)))
					.addGap(153))
		);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 321, 89);
		panel_1.add(scrollPane);
		
		JTextPane txtpnSdasd = new JTextPane();
		txtpnSdasd.setEditable(false);
		txtpnSdasd.setText(notice.getMessage());
		scrollPane.setViewportView(txtpnSdasd);
		panel.setLayout(gl_panel);
		
		
		
	}
}
