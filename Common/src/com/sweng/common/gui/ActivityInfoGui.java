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

public class ActivityInfoGui extends JPanel{
	private JTextField txtActivityName;
	JList listResponsible;
	private JTextField textFieldPlace;
	private JTextField textFieldHour;

	public ActivityInfoGui(ActivityInfo activityInfo, ICommonGui _listener) {
		setLayout(new GridLayout(0, 1, 0, 0));

		JSplitPane splitPane = new JSplitPane();
		// getContentPane().add(splitPane);
		add(splitPane);

		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setLeftComponent(splitPane_1);

		JPanel panel = new JPanel();
		splitPane_1.setLeftComponent(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Activity");
		lblNewLabel.setBounds(145, 16, 132, 20);
		panel.add(lblNewLabel);

		JLabel lblNome = new JLabel("Name:");
		lblNome.setBounds(29, 47, 69, 20);
		panel.add(lblNome);

		if (activityInfo!= null)
			txtActivityName = new JTextField(activityInfo.getName());
		txtActivityName.setEditable(false);
		txtActivityName.setBounds(100, 44, 146, 26);
		panel.add(txtActivityName);
		txtActivityName.setColumns(10);

		JLabel lblAttivo = new JLabel("\u00E8 Completed?");
		lblAttivo.setBounds(261, 47, 82, 20);
		panel.add(lblAttivo);

		JCheckBox checkBox = new JCheckBox("");
		if (activityInfo!= null)
			checkBox.setSelected(activityInfo.isDone());
		checkBox.setEnabled(false);
		checkBox.setBounds(357, 47, 29, 29);
		panel.add(checkBox);
		
		JLabel lblPlace = new JLabel("Place:");
		lblPlace.setBounds(29, 78, 100, 20);
		panel.add(lblPlace);
		
		JLabel lblHour = new JLabel("Hour");
		lblHour.setBounds(29, 120, 100, 20);
		panel.add(lblHour);
		
		
		if(activityInfo!=null)
		{
			String ora = "";
			if(activityInfo.getHour()!=null)
				{
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					ora = format.format(activityInfo.getHour());
				}else
					ora = "Date and Time not setted";
				textFieldPlace = new JTextField(activityInfo.getPlace());
				textFieldHour = new JTextField(ora);
			}
		textFieldPlace.setBorder(null);
		textFieldPlace.setMargin(new Insets(0, 0, 0, 0));
		textFieldPlace.setEditable(false);
		textFieldPlace.setBounds(62, 75, 86, 20);
		panel.add(textFieldPlace);
		textFieldPlace.setColumns(10);
		
		
		textFieldHour.setEditable(false);
		textFieldHour.setBorder(null);
		textFieldHour.setBounds(62, 117, 86, 20);
		panel.add(textFieldHour);
		
		if(activityInfo.isFinishable()){
			JButton btnCompleteActivity = new JButton("Complete Activity\r\n");
			btnCompleteActivity.setBounds(297, 119, 89, 23);
			btnCompleteActivity.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent evt){
					int scelta = JOptionPane.showConfirmDialog(null, "Do you really want to complete this activity?", "Complete Activity", JOptionPane.YES_NO_OPTION);
					if (scelta == JOptionPane.YES_OPTION){
						_listener.completeActivity(activityInfo);
						JOptionPane.showMessageDialog(null, activityInfo.getName() + " was correctly completed!");
						_listener.refreshAll();
					}
				}
			});
			panel.add(btnCompleteActivity);
			textFieldHour.setColumns(10);
		}

//		JLabel lblUsernameAdmin = new JLabel("Username Admin:");
//		lblUsernameAdmin.setBounds(29, 134, 139, 20);
//		panel.add(lblUsernameAdmin);

//		if (activityInfo != null)
//			txtAdmin = new JTextField(projectInfo.getAdmin().getUsername());
//		txtAdmin.setEditable(false);
//		txtAdmin.setColumns(10);
//		txtAdmin.setBounds(240, 131, 146, 26);
//		panel.add(txtAdmin);

//		JButton btnRemove = new JButton("Remove");
//		btnRemove.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent arg0) {
//				_listener.RemoveProjectPressed(activityInfo);
//			}
//		});
//		btnRemove.setBounds(271, 12, 115, 29);
//		panel.add(btnRemove);

		JPanel panel_2 = new JPanel();
		splitPane_1.setRightComponent(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));

		JLabel lblAttivit = new JLabel("");
		panel_2.add(lblAttivit);
		
				JLabel lblResponsible = new JLabel("Responsabili");
				panel_2.add(lblResponsible);

		listResponsible = new JList(new MyUserListModel());
		panel_2.add(listResponsible);
		splitPane_1.setDividerLocation(175);
		splitPane.setDividerLocation(400);

		
		if (activityInfo != null)
			addUsers(activityInfo.getResponsabili());

	}

//	public void addActivitiesAndUsers(HashMap<Activity, User> data) {
//		MyActivityListModel model = (MyActivityListModel) listActivities.getModel();
//
//		model.removeAllElements();
//
//		if (data != null)
//			for (Activity a : data.keySet()) {
//				model.addElement(a, data.get(a));
//			}
//	}

	public void addUsers(ArrayList<User> users) {
		MyUserListModel model = (MyUserListModel) listResponsible.getModel();
		model.removeAllElements();

		if (users != null)
			for (User u : users) {
				model.addElement(u);
			}
	}
}
