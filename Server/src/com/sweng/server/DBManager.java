package com.sweng.server;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.sweng.common.beans.User;

public class DBManager {

	Connection connection;
	
	public DBManager() throws ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.jdbc.Driver");
		
		connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/swengproj", "root", "root");		
	}
	
	public void addUser(User user) throws SQLException
	{
	
		PreparedStatement stat = (PreparedStatement) connection.prepareStatement(
				"INSERT INTO utente (Nome, Cognome, Username, Password)"
			+ 	"VALUES (?, ?, ?, ?)");
		
		stat.setString(1, user.getName());
		stat.setString(2, user.getSurname());
		stat.setString(3, user.getUsername());
		stat.setString(4, user.getPassword());
		
		System.out.println(stat.executeUpdate());
	}
	
}
