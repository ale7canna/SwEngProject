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

	public NoticeInfoGui(Notice notice, ICommonGui _listener) {
		setSize(new Dimension(600, 400));
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
		
		JPanel panel_1 = new JPanel();
		
		JLabel labelDetail = new JLabel("Details");
		
		JTextPane textDetailField = new JTextPane();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(33)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNoticesTitle, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
						.addComponent(labelDateNotice, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMessage, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
						.addComponent(labelDetail))
					.addGap(24)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(textNoticeDate, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(textNoticeTitle, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE)
							.addGap(124)
							.addComponent(btnSetNoticeRed, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(textDetailField, Alignment.LEADING)
							.addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)))
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
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(labelDateNotice)
						.addComponent(textNoticeDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblMessage)
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(labelDetail)
						.addComponent(textDetailField, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))
					.addGap(57))
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
		
		String detailNotice = notice.getDetails();
		if (!detailNotice.isEmpty())
		{
			labelDetail.setVisible(true);
			textDetailField.setVisible(true);
			textDetailField.setText(detailNotice);
			textDetailField.updateUI();
		}
		else
		{
			labelDetail.setVisible(false);
			textDetailField.setVisible(false);
			
		}
		
	}
}
