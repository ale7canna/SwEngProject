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
import com.sweng.common.utils.CustomException;
import com.sweng.common.utils.Errors;

public class DBManager {

	static Connection connection;
	
	static 
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/swengproj", "root", "root");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	// METODI DI INTERROGAZIONE DB
	public static ArrayList<Activity> getActivityFromUser(User user) throws CustomException
	{
		ArrayList<Activity> result = null;
		try
		{
			String query = 	"SELECT * FROM attivita JOIN responsabile_attivita AS ar "
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
			
			if (result.isEmpty())
				throw new CustomException(Errors.ActivitiesNotFound);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new CustomException(Errors.ServerError);
		}
		
		
		return result;
	}
	
	public static ArrayList<Project> getAllProjects() throws CustomException
	{
		ArrayList<Project> result = null;
		try
		{
			String query = 	"SELECT * FROM progetto";
			PreparedStatement stat = (PreparedStatement) connection.prepareStatement(query);
			
			ResultSet rs = stat.executeQuery();
			
			result = new ArrayList<Project>();
			while (rs.next())
			{
				Project proj = new Project(rs.getInt("idProgetto"), rs.getInt("idAdmin"), rs.getString("Nome"), rs.getBoolean("Attivo"));
				result.add(proj);
			}
			
			if (result.isEmpty())
				throw new CustomException(Errors.ProjectsNotFound);
		}
		catch (SQLException e)
		{
			throw new CustomException(Errors.ServerError);
		}
		return result;
	}
	
	public static ArrayList<User> getAllUsers() throws CustomException
	{
		ArrayList<User> result = null;
		try
		{
			String query = 	"SELECT * FROM utente";
			PreparedStatement stat = (PreparedStatement) connection.prepareStatement(query);
			
			ResultSet rs = stat.executeQuery();
			
			result = new ArrayList<User>();
			while (rs.next())
			{
				User user = new User(rs.getInt("idUtente"), rs.getString("Nome"), rs.getString("Cognome"), rs.getString("Username"), rs.getString("Password"));
				result.add(user);
			}
			
			if (result.isEmpty())
				throw new CustomException(Errors.UserNotFound);
		}
		catch (SQLException e)
		{
			throw new CustomException(Errors.ServerError);
		}
		
		return result;
	}
	
	public static User getUser(String username, String password) throws CustomException
	{
		User result = null;
		String query = 	"SELECT * FROM utente WHERE UserName = ? AND Password = ?";
		
		try 
		{
			PreparedStatement stat = (PreparedStatement) connection.prepareStatement(query);
			
			stat.setString(1, username);
			stat.setString(2, password);
			
			ResultSet rs = stat.executeQuery();
			
			while (rs.next())
				result = new User(rs.getInt("idUtente"), rs.getString("Nome"), rs.getString("Cognome"), rs.getString("Username"), rs.getString("Password"));
			
		}
		catch (SQLException e)
		{
			throw new CustomException(Errors.ServerError);
		}
		
		if (result == null)
		{
			if (userExists(username))
				throw new CustomException(Errors.WrongPassword);
			else
				throw new CustomException(Errors.UserNotFound);
		}
		return result;
	}
	
	public static ArrayList<Project> getProjectsFromUser(User user) throws CustomException
	{
		ArrayList<Project> result = null;
		try
		{
			String query = 	"SELECT * FROM progetto JOIN partecipante "
					+ 		"ON progetto.idProgetto = partecipante.idProgetto WHERE partecipante.idUtente = ?";
			PreparedStatement stat = (PreparedStatement) connection.prepareStatement(query);
			
			stat.setInt(1, user.getIdUser());
			
			ResultSet rs = stat.executeQuery();
			result = new ArrayList<Project>();
			while (rs.next())
			{
				Project p = new Project(rs.getInt("idProgetto"), rs.getInt("idAdmin"),
						rs.getString("Nome"), rs.getBoolean("Attivo"));
				result.add(p);
			}
			
			if (result.isEmpty())
				throw new CustomException(Errors.ProjectsNotFound);
		}
		catch (SQLException e)
		{
			throw new CustomException(Errors.ServerError);
		}
		
		return result;
	}
	
	public static ArrayList<User> getFriendsFromUser(User user) throws CustomException
	{
		ArrayList<User> result = null;
		try
		{
			String query = 	"SELECT * FROM Utente JOIN amicizia "
					+ 		"ON Utente.idUtente = amicizia.idUtente2 WHERE amicizia.idUtente1 = ?";
			PreparedStatement stat = (PreparedStatement) connection.prepareStatement(query);
			
			stat.setInt(1, user.getIdUser());
			
			ResultSet rs = stat.executeQuery();
			
			result = new ArrayList<User>();
			while (rs.next())
			{
				User u = new User(rs.getInt("idUtente"), rs.getString("Nome"),
						rs.getString("Cognome"), rs.getString("UserName"), rs.getString("Password"));
				result.add(u);
			}
			
			if (result.isEmpty())
				throw new CustomException(Errors.ProjectsNotFound);
		}
		catch (SQLException e)
		{
			throw new CustomException(Errors.ServerError);
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
	
	private static boolean userExists(String username) throws CustomException
	{
		String query = 	"SELECT * FROM utente WHERE UserName = ?";
		
		try 
		{
			PreparedStatement stat = (PreparedStatement) connection.prepareStatement(query);
			stat.setString(1, username);		
			ResultSet rs = stat.executeQuery();
			
			if (!rs.next())
				return false;
			else
				return true;
		}
		catch (SQLException e)
		{
			throw new CustomException(Errors.ServerError);
		}
	}
	
}
