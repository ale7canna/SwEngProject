package com.sweng.client.gui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.TitledBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import javax.swing.LayoutStyle.ComponentPlacement;

public class GUIPanelSignIn extends JPanel{
		
	private JTextField textSignInUserName;
	private JPasswordField textSignInPassword;
	private JTextField textSignUpName;
	private JTextField textSignUpUserName;
	private JTextField textSignUpSurname;
	private JPasswordField textSignUpPassword;
	
	private EventListenerGUI listener;
	
	public GUIPanelSignIn(EventListenerGUI _listener ) {
		setLayout(null);
		listener = _listener;
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "SignIn", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 128, 128)));
		panel.setBounds(5, 5, 285, 340);
		add(panel);
		
		textSignInUserName = new JTextField("");
		textSignInUserName.setColumns(10);
		
		JButton button = new JButton("SignIn");
		
		JLabel label = new JLabel("Password");
		
		JLabel label_1 = new JLabel("UserName");
		
		textSignInPassword = new JPasswordField("");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(20)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(textSignInPassword, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
						.addComponent(textSignInUserName, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
							.addGap(38))
						.addComponent(label, GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(72)
					.addComponent(button, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(73, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(52)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textSignInUserName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_1))
					.addGap(40)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textSignInPassword, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
						.addComponent(label))
					.addGap(42)
					.addComponent(button)
					.addContainerGap(123, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String username = textSignInUserName.getText();
				String password = String.copyValueOf(textSignInPassword.getPassword());
				if (!username.isEmpty() && !password.isEmpty())
					listener.SignInRequest(username, password);
				else 
					JOptionPane.showMessageDialog(null, "Please complete in the correct way the Signin form");
			}
		});
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "SignUp", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 128, 128)), "SignUp", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 128, 128)));
		panel_1.setBounds(290, 5, 285, 340);
		add(panel_1);
		
		textSignUpName = new JTextField("");
		textSignUpName.setColumns(10);
		
		JLabel label_2 = new JLabel("Name");
		
		JLabel label_3 = new JLabel("UserName");
		
		textSignUpUserName = new JTextField("");
		textSignUpUserName.setColumns(10);
		
		JLabel label_4 = new JLabel("Password");
		
		JLabel label_5 = new JLabel("Surname");
		
		textSignUpSurname = new JTextField("");
		textSignUpSurname.setColumns(10);
		
		JButton btnSignUp = new JButton("SignUp");
		
		textSignUpPassword = new JPasswordField("");
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
						.addComponent(textSignUpSurname, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
						.addComponent(textSignUpPassword, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
						.addComponent(textSignUpUserName, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
						.addComponent(textSignUpName, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE))
					.addGap(34)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(label_5, GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
						.addComponent(label_4, GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
						.addComponent(label_3, GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
						.addComponent(label_2, GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE))
					.addContainerGap())
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(63)
					.addComponent(btnSignUp, GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
					.addGap(79))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(45)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(textSignUpName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_2))
					.addGap(18)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(textSignUpUserName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_3))
					.addGap(22)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(textSignUpPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_4))
					.addGap(25)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(textSignUpSurname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_5))
					.addGap(45)
					.addComponent(btnSignUp)
					.addContainerGap(55, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		btnSignUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String username = textSignUpUserName.getText();
				String password = String.copyValueOf(textSignUpPassword.getPassword());
				String surname = textSignUpSurname.getText();
				String name = textSignUpName.getText();
				_listener.performSignUp(username, password, name, surname);
			}
		});
	}
}
