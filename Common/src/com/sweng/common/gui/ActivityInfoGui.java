package com.sweng.common.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import com.sweng.common.beans.ActivityInfo;
import com.sweng.common.beans.ActivityResponsible;
import com.sweng.common.beans.User;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class ActivityInfoGui extends JPanel {
	private JTextField txtActivityName;
	JList listResponsible;
	private JTextField textFieldPlace;
	private JTextField textFieldHour;

	public ActivityInfoGui(ActivityInfo activityInfo, ICommonGui _listener) {
		setSize(new Dimension(800, 466));
		setLayout(null);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setBounds(0, 0, 790, 450);
		add(splitPane);

		JPanel panelActivity = new JPanel();
		splitPane.setLeftComponent(panelActivity);
		panelActivity.setLayout(null);

		JLabel lblNewLabel = new JLabel("Activity");
		lblNewLabel.setForeground(new Color(0, 128, 128));
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblNewLabel.setBounds(145, 16, 132, 20);
		panelActivity.add(lblNewLabel);

		JLabel lblNome = new JLabel("Name:");
		lblNome.setBounds(29, 47, 69, 20);
		panelActivity.add(lblNome);

		if (activityInfo != null)
			txtActivityName = new JTextField(activityInfo.getName());
		txtActivityName.setEditable(false);
		txtActivityName.setBounds(100, 44, 146, 26);
		panelActivity.add(txtActivityName);
		txtActivityName.setColumns(10);

		JLabel lblAttivo = new JLabel("Is Completed:\r\n");
		lblAttivo.setBounds(261, 47, 82, 20);
		panelActivity.add(lblAttivo);

		JCheckBox checkBox = new JCheckBox("");
		lblAttivo.setLabelFor(checkBox);
		if (activityInfo != null)
			checkBox.setSelected(activityInfo.isDone());
		checkBox.setEnabled(false);
		checkBox.setBounds(357, 47, 29, 29);
		panelActivity.add(checkBox);

		JLabel lblPlace = new JLabel("Place:");
		lblPlace.setBounds(29, 78, 100, 20);
		panelActivity.add(lblPlace);

		JLabel lblHour = new JLabel("Hour:");
		lblHour.setLabelFor(this);
		lblHour.setBounds(29, 120, 100, 20);
		panelActivity.add(lblHour);

		if (activityInfo != null) {
			String ora = "";
			if (activityInfo.getHour() != null) {
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				ora = format.format(activityInfo.getHour());
			} else
				ora = "Date and Time not setted";
			textFieldPlace = new JTextField(activityInfo.getPlace());
			textFieldHour = new JTextField(ora);
		}
		textFieldPlace.setBorder(null);
		textFieldPlace.setMargin(new Insets(0, 0, 0, 0));
		textFieldPlace.setEditable(false);
		textFieldPlace.setBounds(100, 75, 86, 20);
		panelActivity.add(textFieldPlace);
		textFieldPlace.setColumns(10);

		textFieldHour.setEditable(false);
		textFieldHour.setBorder(null);
		textFieldHour.setBounds(100, 117, 100, 20);
		panelActivity.add(textFieldHour);
		
		JLabel lblMessage = new JLabel("Message:");
		lblMessage.setBounds(29, 163, 69, 14);
		panelActivity.add(lblMessage);
		
		JPanel panel = new JPanel();
		panel.setBounds(98, 163, 301, 65);
		panelActivity.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 301, 65);
		panel.add(scrollPane);
		
		JTextPane textPane = new JTextPane();
		textPane.setText(activityInfo.getText());
		scrollPane.setViewportView(textPane);
		
		JButton btnNewButton = new JButton("Add Text\r\n");
		btnNewButton.setBounds(98, 253, 108, 23);
		panelActivity.add(btnNewButton);
		btnNewButton.addMouseListener(new MouseAdapter() {
	
		public void mousePressed(MouseEvent evt) {
			int scelta = JOptionPane.showConfirmDialog(null,
					"Do you really want to add the text this activity?",
					"Add Text", JOptionPane.YES_NO_OPTION);
			if (scelta == JOptionPane.YES_OPTION) {
				activityInfo.setText(textPane.getText());
				_listener.addText(activityInfo, textPane.getText());
				JOptionPane.showMessageDialog(null,"The text was correctly added!");
				_listener.refreshAll();
				}
			}
		});
		

		if (activityInfo.isFinishable()) {
			JButton btnCompleteActivity = new JButton("Complete Activity\r\n");
			btnCompleteActivity.setBounds(150, 353, 200, 23);
			btnCompleteActivity.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent evt) {
					int scelta = JOptionPane.showConfirmDialog(null,
							"Do you really want to complete this activity?",
							"Complete Activity", JOptionPane.YES_NO_OPTION);
					if (scelta == JOptionPane.YES_OPTION) {
						_listener.completeActivity(activityInfo);
						JOptionPane.showMessageDialog(null,
								activityInfo.getName()
										+ " was correctly completed!");
						_listener.refreshAll();
					}
				}
			});
			panelActivity.add(btnCompleteActivity);
			textFieldHour.setColumns(10);
		}

		JPanel panelResponsabili = new JPanel();
		splitPane.setRightComponent(panelResponsabili);
		splitPane.setDividerLocation(410);

		JLabel lblResponsible = new JLabel("Responsabili");
		lblResponsible.setForeground(new Color(0, 140, 128));
		lblResponsible.setFont(new Font("Trebuchet MS", Font.BOLD, 13));

		listResponsible = new JList(new MyUserListModel());
		lblResponsible.setLabelFor(listResponsible);
		
	
		
		JLabel lblNewLabel_1 = new JLabel("Double click to remove from responsible");
		lblNewLabel_1.setLabelFor(panelResponsabili);
		lblNewLabel_1.setFont(new Font("Trebuchet MS", Font.ITALIC, 10));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GroupLayout gl_panelResponsabili = new GroupLayout(panelResponsabili);
		gl_panelResponsabili.setHorizontalGroup(
			gl_panelResponsabili.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelResponsabili.createSequentialGroup()
					.addGap(132)
					.addComponent(lblResponsible)
					.addContainerGap(91, Short.MAX_VALUE))
				.addGroup(gl_panelResponsabili.createSequentialGroup()
					.addContainerGap(84, Short.MAX_VALUE)
					.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)
					.addGap(60))
				.addGroup(Alignment.LEADING, gl_panelResponsabili.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_1)
					.addContainerGap(180, Short.MAX_VALUE))
		);
		gl_panelResponsabili.setVerticalGroup(
			gl_panelResponsabili.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelResponsabili.createSequentialGroup()
					.addGap(13)
					.addComponent(lblResponsible)
					.addGap(26)
					.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(191)
					.addComponent(lblNewLabel_1)
					.addContainerGap())
		);
		
		scrollPane_1.setViewportView(listResponsible);
		panelResponsabili.setLayout(gl_panelResponsabili);

		listResponsible.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent ev) {
				if(ev.getClickCount()==2){
					User u = (User) ((MyUserListModel) listResponsible.getModel()).getUserAt(listResponsible.getSelectedIndex());
					int scelta = JOptionPane.showConfirmDialog(null, "Do you really want to remove "+ u.getUsername()+ " from "+ activityInfo.getName()+" activity?", "Remove participant", JOptionPane.YES_NO_OPTION );
					if(scelta == JOptionPane.YES_OPTION){
						ActivityResponsible resp= new ActivityResponsible(u.getIdUser(), activityInfo.getIdActivity()); 
						
						addUsers(_listener.removeResponsible(resp));
						
						} 
				}
				
			}
		});
		
		if (activityInfo != null)
			{
			addUsers(activityInfo.getResponsabili());
			}

	}

	public void addUsers(ArrayList<User> users) {
		MyUserListModel model = (MyUserListModel) listResponsible.getModel();
		model.removeAllElements();

		if (users != null)
			for (User u : users) {
				model.addElement(u);
			}
	}
}
