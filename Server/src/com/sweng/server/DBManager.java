package com.sweng.server;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.sweng.common.beans.Activity;
import com.sweng.common.beans.ActivityResponsible;
import com.sweng.common.beans.Friendship;
import com.sweng.common.beans.Participant;
import com.sweng.common.beans.Project;
import com.sweng.common.beans.User;

public class DBManager {

	Connection connection;
	
	
	public DBManager() throws ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.jdbc.Driver");
		
		connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/swengproj", "root", "root");		
	}
	
	
	// METODI DI INTERROGAZIONE DB
	public ArrayList<Activity> getActivityFromUser(User user) throws SQLException
	{
		ArrayList<Activity> result = null;
		String query = 	"SELECT * FROM attivita JOIN responsabile_attivita AS ar"
				+ 		"ON attivita.idAttivita = ar.idAttivita WHERE ar.idUtente = ?";
		PreparedStatement stat = (PreparedStatement) connection.prepareStatement(query);
		
		stat.setInt(1, user.getIdUser());
		
		ResultSet rs = stat.executeQuery();
		
		result = new ArrayList<Activity>();
		while (rs.next())
		{
			Activity act = new Activity(rs.getInt("idProgetto"), rs.getInt("idAttivita"),
					rs.getString("Nome"), rs.getString("Luogo"), rs.getDate("Ora"));
			result.add(act);
		}
		
		return result;
	}
	
	public ArrayList<Project> getAllProjects() throws SQLException
	{
		ArrayList<Project> result = null;
		String query = 	"SELECT * FROM progetto";
		PreparedStatement stat = (PreparedStatement) connection.prepareStatement(query);
		
		ResultSet rs = stat.executeQuery();
		
		result = new ArrayList<Project>();
		while (rs.next())
		{
			Project proj = new Project(rs.getInt("idProgetto"), rs.getInt("idAdmin"), rs.getString("Nome"), rs.getBoolean("Attivo"));
			result.add(proj);
		}
		
		return result;
	}
	
	public ArrayList<User> getAllUsers() throws SQLException
	{
		ArrayList<User> result = null;
		String query = 	"SELECT * FROM utente";
		PreparedStatement stat = (PreparedStatement) connection.prepareStatement(query);
		
		ResultSet rs = stat.executeQuery();
		
		result = new ArrayList<User>();
		while (rs.next())
		{
			User user = new User(rs.getInt("idUtente"), rs.getString("Nome"), rs.getString("Cognome"), rs.getString("Username"), rs.getString("Password"));
			result.add(user);
		}
		
		return result;
	}
	
	
	// METODI DI AGGIUNTA ENTRY AL DB
	public void addActivity(Activity activity) throws SQLException
	{
		PreparedStatement stat = (PreparedStatement) connection.prepareStatement(
				"INSERT INTO attivita (Nome, Luogo, Ora, idProgetto)"
			+ 	"VALUES (?, ?, ?, ?)");
		
		stat.setString(1, activity.getName());
		stat.setString(2, activity.getPlace());
		stat.setDate(3, activity.getHour());
		stat.setInt(4, activity.getIdProject());
				
		stat.executeUpdate();
	}

	public void addActivityResponsible(ActivityResponsible ar) throws SQLException
	{
		PreparedStatement stat = (PreparedStatement) connection.prepareStatement(
				"INSERT INTO responsabile_attivita (idUtente, idAttivita)"
			+ 	"VALUES (?, ?)");
		
		stat.setInt(1, ar.getIdUser());
		stat.setInt(2, ar.getIdActivity());
		stat.executeUpdate();
	}
	
	public void addFriendship(Friendship friendship) throws SQLException
	{
		PreparedStatement stat = (PreparedStatement) connection.prepareStatement(
				"INSERT INTO amicizia (idUtente1, idUtente2)"
						+ 	"VALUES (?, ?)");
		
		stat.setInt(1, friendship.getIdUtente1());
		stat.setInt(2, friendship.getIdUtente2());
		stat.executeUpdate();
	}
	
	public void addParticipant(Participant participant) throws SQLException
	{
		PreparedStatement stat = (PreparedStatement) connection.prepareStatement(
				"INSERT INTO partecipante (idUtente, idProgetto)"
						+ 	"VALUES (?, ?)");
		
		stat.setInt(1, participant.getIdUser());
		stat.setInt(2, participant.getIdProject());
		stat.executeUpdate();
	}
	
	public void addProject(Project project) throws SQLException
	{
		PreparedStatement stat = (PreparedStatement) connection.prepareStatement(
				"INSERT INTO progetto (Nome, idAdmin, Attivo)"
						+ 	"VALUES (?, ?, ?)");
		
		stat.setString(1, project.getName());
		stat.setInt(2, project.getIdAdmin());
		stat.setBoolean(3, project.isActive());
		stat.executeUpdate();
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
		
		stat.executeUpdate();
		
	}
	
}
