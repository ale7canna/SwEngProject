package com.sweng.common.gui;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import com.sweng.common.beans.Activity;
import com.sweng.common.beans.ActivityInfo;
import com.sweng.common.beans.User;

import java.awt.Insets;
import java.awt.Dimension;

public class ActivityInfoGui extends JPanel {
	private JTextField txtActivityName;
	JList listResponsible;
	private JTextField textFieldPlace;
	private JTextField textFieldHour;

	public ActivityInfoGui(ActivityInfo activityInfo, ICommonGui _listener) {
		setSize(new Dimension(550, 300));
		
		setLayout(new GridLayout(0, 1, 0, 0));

		JSplitPane splitPane = new JSplitPane();
		add(splitPane);

		JPanel panelActivity = new JPanel();
		splitPane.setLeftComponent(panelActivity);
		panelActivity.setLayout(null);

		JLabel lblNewLabel = new JLabel("Activity");
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

		JLabel lblAttivo = new JLabel("\u00E8 Completed?");
		lblAttivo.setBounds(261, 47, 82, 20);
		panelActivity.add(lblAttivo);

		JCheckBox checkBox = new JCheckBox("");
		if (activityInfo != null)
			checkBox.setSelected(activityInfo.isDone());
		checkBox.setEnabled(false);
		checkBox.setBounds(357, 47, 29, 29);
		panelActivity.add(checkBox);

		JLabel lblPlace = new JLabel("Place:");
		lblPlace.setBounds(29, 78, 100, 20);
		panelActivity.add(lblPlace);

		JLabel lblHour = new JLabel("Hour");
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

		if (activityInfo.isFinishable()) {
			JButton btnCompleteActivity = new JButton("Complete Activity\r\n");
			btnCompleteActivity.setBounds(297, 119, 89, 23);
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

		JLabel lblAttivit = new JLabel("");
		panelResponsabili.add(lblAttivit);

		JLabel lblResponsible = new JLabel("Responsabili");
		panelResponsabili.add(lblResponsible);

		listResponsible = new JList(new MyUserListModel());
		panelResponsabili.add(listResponsible);

		if (activityInfo != null)
			addUsers(activityInfo.getResponsabili());

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
