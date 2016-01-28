package com.sweng.client.gui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
		
		textSignInUserName = new JTextField();
		textSignInUserName.setColumns(10);
		textSignInUserName.setBounds(32, 91, 86, 20);
		add(textSignInUserName);
		
		JButton button = new JButton("SignIn");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String username = textSignInUserName.getText();
				String password = String.copyValueOf(textSignInPassword.getPassword());
				if (!username.isEmpty() && !password.isEmpty())
					listener.SignInRequest(username, password);
			}
		});
		button.setBounds(72, 170, 89, 23);
		add(button);
		
		JLabel label = new JLabel("Password");
		label.setBounds(145, 132, 46, 14);
		add(label);
		
		JLabel label_1 = new JLabel("UserName");
		label_1.setBounds(145, 94, 46, 14);
		add(label_1);
		
		textSignUpName = new JTextField();
		textSignUpName.setColumns(10);
		textSignUpName.setBounds(255, 91, 86, 20);
		add(textSignUpName);
		
		JLabel label_2 = new JLabel("Name");
		label_2.setBounds(370, 94, 46, 14);
		add(label_2);
		
		JLabel label_3 = new JLabel("UserName");
		label_3.setBounds(370, 132, 46, 14);
		add(label_3);
		
		textSignUpUserName = new JTextField();
		textSignUpUserName.setColumns(10);
		textSignUpUserName.setBounds(255, 129, 86, 20);
		add(textSignUpUserName);
		
		JLabel label_4 = new JLabel("Password");
		label_4.setBounds(370, 174, 46, 14);
		add(label_4);
		
		JLabel label_5 = new JLabel("Surname");
		label_5.setBounds(370, 219, 46, 14);
		add(label_5);
		
		textSignUpSurname = new JTextField();
		textSignUpSurname.setColumns(10);
		textSignUpSurname.setBounds(255, 216, 86, 20);
		add(textSignUpSurname);
		
		JButton btnSignUp = new JButton("SignUp");
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
		btnSignUp.setBounds(296, 266, 89, 23);
		add(btnSignUp);
		
		textSignInPassword = new JPasswordField();
		textSignInPassword.setBounds(32, 129, 86, 20);
		add(textSignInPassword);
		
		textSignUpPassword = new JPasswordField();
		textSignUpPassword.setBounds(255, 171, 86, 20);
		add(textSignUpPassword);
	}
}
