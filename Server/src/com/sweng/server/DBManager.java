package com.sweng.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.sweng.common.Consts;
import com.sweng.common.beans.Activity;
import com.sweng.common.beans.ActivityResponsible;
import com.sweng.common.beans.Friendship;
import com.sweng.common.beans.Participant;
import com.sweng.common.beans.Project;
import com.sweng.common.beans.ProjectInfo;
import com.sweng.common.beans.User;
import com.sweng.common.utils.CustomException;
import com.sweng.common.utils.Errors;

public class DBManager {

	static Connection connection;

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = (Connection) DriverManager.getConnection(Consts.DB_URL, Consts.DB_USERNAME,
					Consts.DB_PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// METODI DI INTERROGAZIONE DB
	public static ArrayList<Activity> getActivityFromUser(User user) throws CustomException {
		ArrayList<Activity> result = null;
		try {
			String query = "SELECT * FROM attivita JOIN responsabile_attivita AS ar "
					+ "ON attivita.idAttivita = ar.idAttivita WHERE ar.idUtente = ?";
			PreparedStatement stat = (PreparedStatement) connection.prepareStatement(query);

			stat.setInt(1, user.getIdUser());

			ResultSet rs = stat.executeQuery();

			result = new ArrayList<Activity>();
			while (rs.next()) {
				Activity act = new Activity(rs.getInt("idProgetto"), rs.getInt("idAttivita"), rs.getString("Nome"),
						rs.getString("Luogo"), rs.getDate("Ora"), rs.getBoolean("Completata"));
				result.add(act);
			}

			if (result.isEmpty())
				throw new CustomException(Errors.ActivitiesNotFound);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CustomException(Errors.ServerError);
		}

		return result;
	}

	public static ArrayList<Project> getAllProjects() throws CustomException {
		ArrayList<Project> result = null;
		try {
			String query = "SELECT * FROM progetto";
			PreparedStatement stat = (PreparedStatement) connection.prepareStatement(query);

			ResultSet rs = stat.executeQuery();

			result = new ArrayList<Project>();
			while (rs.next()) {
				Project proj = new Project(rs.getInt("idProgetto"), rs.getInt("idAdmin"), rs.getString("Nome"),
						rs.getBoolean("Attivo"));
				result.add(proj);
			}

			if (result.isEmpty())
				throw new CustomException(Errors.ProjectsNotFound);
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
		return result;
	}

	public static ArrayList<User> getAllUsers() throws CustomException {
		ArrayList<User> result = null;
		try {
			String query = "SELECT * FROM utente";
			PreparedStatement stat = (PreparedStatement) connection.prepareStatement(query);

			ResultSet rs = stat.executeQuery();

			result = new ArrayList<User>();
			while (rs.next()) {
				User user = new User(rs.getInt("idUtente"), rs.getString("Nome"), rs.getString("Cognome"),
						rs.getString("Username"), rs.getString("Password"));
				result.add(user);
			}

			if (result.isEmpty())
				throw new CustomException(Errors.UserNotFound);
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}

		return result;
	}

	public static User getUser(String username, String password) throws CustomException {
		User result = null;
		String query = "SELECT * FROM utente WHERE UserName = ? AND Password = ?";

		try {
			PreparedStatement stat = (PreparedStatement) connection.prepareStatement(query);

			stat.setString(1, username);
			stat.setString(2, password);

			ResultSet rs = stat.executeQuery();

			while (rs.next())
				result = new User(rs.getInt("idUtente"), rs.getString("Nome"), rs.getString("Cognome"),
						rs.getString("Username"), rs.getString("Password"));

		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}

		if (result == null) {
			if (userExists(username))
				throw new CustomException(Errors.WrongPassword);
			else
				throw new CustomException(Errors.UserNotFound);
		}
		return result;
	}

	public static User getUser(int id) throws CustomException {
		User result = null;
		String query = "SELECT * FROM utente WHERE idUtente = ?";

		try {
			PreparedStatement stat = (PreparedStatement) connection.prepareStatement(query);

			stat.setInt(1, id);

			ResultSet rs = stat.executeQuery();

			while (rs.next())
				result = new User(rs.getInt("idUtente"), rs.getString("Nome"), rs.getString("Cognome"),
						rs.getString("Username"), rs.getString("Password"));

		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}

		return result;
	}

	public static ArrayList<Project> getProjectsFromUser(User user) throws CustomException {
		ArrayList<Project> result = null;
		try {
			String query = "SELECT * FROM progetto JOIN partecipante "
					+ "ON progetto.idProgetto = partecipante.idProgetto WHERE partecipante.idUtente = ?";
			PreparedStatement stat = (PreparedStatement) connection.prepareStatement(query);

			stat.setInt(1, user.getIdUser());

			ResultSet rs = stat.executeQuery();
			result = new ArrayList<Project>();
			while (rs.next()) {
				Project p = new Project(rs.getInt("idProgetto"), rs.getInt("idAdmin"), rs.getString("Nome"),
						rs.getBoolean("Attivo"));
				result.add(p);
			}

			if (result.isEmpty())
				throw new CustomException(Errors.ProjectsNotFound);
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}

		return result;
	}

	public static ArrayList<User> getFriendsFromUser(User user) throws CustomException {
		ArrayList<User> result = null;
		try {
			String query = "SELECT * FROM Utente JOIN amicizia "
					+ "ON Utente.idUtente = amicizia.idUtente2 WHERE amicizia.idUtente1 = ?";
			PreparedStatement stat = (PreparedStatement) connection.prepareStatement(query);

			stat.setInt(1, user.getIdUser());

			ResultSet rs = stat.executeQuery();

			result = new ArrayList<User>();
			while (rs.next()) {
				User u = new User(rs.getInt("idUtente"), rs.getString("Nome"), rs.getString("Cognome"),
						rs.getString("UserName"), rs.getString("Password"));
				result.add(u);
			}

			if (result.isEmpty())
				throw new CustomException(Errors.ProjectsNotFound);
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}

		return result;
	}

	public static ProjectInfo getProjectInfo(Project project) throws CustomException {
		ProjectInfo resultInfo = null;

		try {
			String query = "SELECT * FROM attivita JOIN responsabile_attivita AS ar "
					+ "ON attivita.idAttivita = ar.idAttivita JOIN utente ON ar.idUtente = utente.idUtente "
					+ "WHERE idProgetto = ?";
			PreparedStatement stat = connection.prepareStatement(query);

			stat.setInt(1, project.getIdProject());
			ResultSet rs = stat.executeQuery();

			int attivitaComplete = 0;
			HashMap<Activity, User> actInResp = new HashMap<Activity, User>();
			while (rs.next()) {
				boolean completa = rs.getBoolean("Completata");
				if (completa)
					attivitaComplete++;
				Activity act = new Activity(project.getIdProject(), rs.getInt("ar.idAttivita"),
						rs.getString("attivita.Nome"), rs.getString("Luogo"), rs.getDate("Ora"), completa);

				User u = new User(rs.getInt("ar.idUtente"), rs.getString("utente.Nome"), rs.getString("Cognome"),
						rs.getString("UserName"), null);

				actInResp.put(act, u);
			}

			ArrayList<User> parts = getParticipantsFromProject(project);
			User admin = getUser(project.getIdAdmin());
			float compPerc = (float) attivitaComplete / (float) actInResp.size();

			resultInfo = new ProjectInfo(project.getName(), project.getIdProject(), project.isActive(), admin,
					actInResp, parts, compPerc);
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		} catch (CustomException e) {
			throw new CustomException(Errors.ActivitiesNotFound);
		}

		if (resultInfo == null)
			throw new CustomException(Errors.ProjectsNotFound);

		return resultInfo;

	}

	public static ArrayList<User> getParticipantsFromProject(Project project) throws CustomException {
		ArrayList<User> result = null;
		try {
			String query = "SELECT * FROM utente JOIN partecipante AS p "
					+ "ON utente.idUtente = p.idUtente WHERE p.idProgetto = ?";
			PreparedStatement stat = (PreparedStatement) connection.prepareStatement(query);

			stat.setInt(1, project.getIdProject());

			ResultSet rs = stat.executeQuery();

			result = new ArrayList<User>();
			while (rs.next()) {
				User u = new User(rs.getInt("p.idUtente"), rs.getString("Nome"), rs.getString("Cognome"),
						rs.getString("UserName"), null);
				result.add(u);
			}

			if (result.isEmpty())
				throw new CustomException(Errors.UserNotFound);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CustomException(Errors.ServerError);
		}

		return result;
	}

	public static ArrayList<User> getNotMyFriends(int idUser) throws CustomException{
		ArrayList<User> result = null;
		try {
			String query =  "SELECT * FROM utente WHERE idUtente NOT IN(SELECT idUtente2 "+ 
							"FROM amicizia WHERE amicizia.idUtente1=?)";
			PreparedStatement stat = (PreparedStatement) connection.prepareStatement(query);

			stat.setInt(1, idUser);

			ResultSet rs = stat.executeQuery();

			result = new ArrayList<User>();
			while (rs.next()) {
				if(rs.getInt("idUtente")!=idUser){
					User u = new User(rs.getInt("idUtente"), rs.getString("Nome"), rs.getString("Cognome"),
							rs.getString("UserName"), null);
					result.add(u);
				}
			}

			if (result.isEmpty())
				throw new CustomException(Errors.ActivitiesNotFound);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CustomException(Errors.ServerError);
		}

		return result;
	}
	
	
	// METODI DI AGGIUNTA ENTRY AL DB
	public static void addActivity(Activity activity) throws CustomException {
		try {
			PreparedStatement stat = (PreparedStatement) connection
					.prepareStatement("INSERT INTO attivita (Nome, Luogo, Ora, idProgetto)" + "VALUES (?, ?, ?, ?)");

			stat.setString(1, activity.getName());
			stat.setString(2, activity.getPlace());
			stat.setDate(3, activity.getHour());
			stat.setInt(4, activity.getIdProject());

			stat.executeUpdate();
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
	}

	public static void addActivityResponsible(ActivityResponsible ar) throws CustomException {
		try {
			PreparedStatement stat = (PreparedStatement) connection
					.prepareStatement("INSERT INTO responsabile_attivita (idUtente, idAttivita)" + "VALUES (?, ?)");

			stat.setInt(1, ar.getIdUser());
			stat.setInt(2, ar.getIdActivity());
			stat.executeUpdate();
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
	}

	public static void addFriendship(Friendship friendship) throws CustomException {

		try {
			PreparedStatement stat = (PreparedStatement) connection
					.prepareStatement("INSERT INTO amicizia (idUtente1, idUtente2)" + "VALUES (?, ?)");

			stat.setInt(1, friendship.getIdUtente1());
			stat.setInt(2, friendship.getIdUtente2());
			stat.executeUpdate();
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
	}

	public static void addParticipant(Participant participant) throws CustomException {

		try {
			PreparedStatement stat = (PreparedStatement) connection
					.prepareStatement("INSERT INTO partecipante (idUtente, idProgetto)" + "VALUES (?, ?)");

			stat.setInt(1, participant.getIdUser());
			stat.setInt(2, participant.getIdProject());
			stat.executeUpdate();
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
	}

	public static void addProject(Project project) throws CustomException {

		try {
			PreparedStatement stat = (PreparedStatement) connection
					.prepareStatement("INSERT INTO progetto (Nome, idAdmin, Attivo)" + "VALUES (?, ?, ?)");

			stat.setString(1, project.getName());
			stat.setInt(2, project.getIdAdmin());
			stat.setBoolean(3, project.isActive());
			stat.executeUpdate();
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
	}

	public static void addUser(User user) throws CustomException {

		try {
			PreparedStatement stat = (PreparedStatement) connection

					.prepareStatement("INSERT INTO utente (Nome, Cognome, Username, Password)" + "VALUES (?, ?, ?, ?)");

			stat.setString(1, user.getName());
			stat.setString(2, user.getSurname());
			stat.setString(3, user.getUsername());
			stat.setString(4, user.getPassword());

			stat.executeUpdate();
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
	}

	private static boolean userExists(String username) throws CustomException {
		String query = "SELECT * FROM utente WHERE UserName = ?";

		try {
			PreparedStatement stat = (PreparedStatement) connection.prepareStatement(query);
			stat.setString(1, username);
			ResultSet rs = stat.executeQuery();

			if (!rs.next())
				return false;
			else
				return true;
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
	}

	//METODI DI ELIMINAZIONE DAL DB
	public static void removeProject(Project project) throws CustomException{
		removeActivitiesFromProject(project);
		removeParticipantsFromProject(project);

		
		String query = "DELETE FROM progetto WHERE idProgetto = ?";
		
		try {
			PreparedStatement stat = connection.prepareStatement(query);
		
			stat.setInt(1, project.getIdProject());
			stat.executeUpdate();
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
		
		
	}
	
	public static void removeActivitiesFromProject(Project project) throws CustomException
	{
		String query = "DELETE FROM responsabile_attivita WHERE idAttivita IN " +
						"(SELECT idAttivita FROM attivita WHERE idProgetto = ?)";
		try {
			PreparedStatement stat = connection.prepareStatement(query);
		
			stat.setInt(1, project.getIdProject());
			stat.executeUpdate();
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
		
		query = "DELETE FROM attivita WHERE idProgetto = ?";
				
		try {
			PreparedStatement stat = connection.prepareStatement(query);
		
			stat.setInt(1, project.getIdProject());
			stat.executeUpdate();
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
	}
	
	public static void removeParticipantsFromProject(Project project) throws CustomException
	{
		String query = "DELETE FROM partecipante WHERE idProgetto = ?";
		
		try {
			PreparedStatement stat = connection.prepareStatement(query);
		
			stat.setInt(1, project.getIdProject());
			stat.executeUpdate();
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
	}
	
	
	
	
	

	public static Project getProjectFromNameAndAdmin(String projectName, int idAdmin) throws CustomException {
		
		Project result = null;
		String query = "SELECT * FROM progetto WHERE Nome = ? AND idAdmin = ? ";

		try {
			PreparedStatement stat = (PreparedStatement) connection.prepareStatement(query);
			stat.setString(1, projectName);
			stat.setInt(2, idAdmin);
			ResultSet rs = stat.executeQuery();

			if (rs.next())
			{
				result = new Project(rs.getInt("idProgetto"), rs.getInt("idAdmin"), rs.getString("Nome"), rs.getBoolean("Attivo"));
			}
			else
				throw new CustomException(Errors.ProjectsNotFound);
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
		return result;
	}
	
	public static Activity getActivityFromNamePlaceAndProject(String activityName, int idProject) throws CustomException {
		
		Activity result = null;
		String query = "SELECT * FROM attivita WHERE Nome = ? AND idProgetto = ";

		try {
			PreparedStatement stat = (PreparedStatement) connection.prepareStatement(query);
			stat.setString(1, activityName);
			stat.setInt(2, idProject);
			ResultSet rs = stat.executeQuery();

			if (rs.next())
			{
				result = new Activity(rs.getInt("idProgetto"), rs.getInt("idAttivita"), rs.getString("Nome"), rs.getString("Luogo"), rs.getDate("Ora"), rs.getBoolean("Completata"));
			}
			else
				throw new CustomException(Errors.ProjectsNotFound);
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
		return result;
	}

	
}
