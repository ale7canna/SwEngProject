package com.sweng.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.sweng.common.Consts;
import com.sweng.common.IClient;
import com.sweng.common.beans.Activity;
import com.sweng.common.beans.ActivityInfo;
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
				Date d = null;
				Timestamp t = rs.getTimestamp("Ora");
				if (t != null)
					d = new Date(t.getTime());
					
				Activity act = new Activity(rs.getInt("idProgetto"), rs.getInt("idAttivita"), rs.getString("Nome"),
						rs.getString("Luogo"), d, rs.getBoolean("Completata"));
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
				throw new CustomException(Errors.FriendsNotFound);
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}

		return result;
	}

	public static ProjectInfo getProjectInfo(Project project) throws CustomException {
		ProjectInfo resultInfo = null;
		int attivitaComplete = 0;
		HashMap<Activity, User> actInResp = new HashMap<Activity, User>();
		ArrayList<User> parts = null;
		User admin = null;
		float compPerc = 0;

		try {
			String query = "SELECT * FROM attivita JOIN responsabile_attivita AS ar "
					+ "ON attivita.idAttivita = ar.idAttivita JOIN utente ON ar.idUtente = utente.idUtente "
					+ "WHERE idProgetto = ?";
			PreparedStatement stat = connection.prepareStatement(query);

			stat.setInt(1, project.getIdProject());
			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				boolean completa = rs.getBoolean("Completata");
				if (completa)
					attivitaComplete++;
				
				Date d = null;
				Timestamp t = rs.getTimestamp("Ora");
				if (t != null)
					d = new Date(t.getTime());
				
				Activity act = new Activity(project.getIdProject(), rs.getInt("ar.idAttivita"),
						rs.getString("attivita.Nome"), rs.getString("Luogo"), d, completa);

				User u = new User(rs.getInt("ar.idUtente"), rs.getString("utente.Nome"), rs.getString("Cognome"),
						rs.getString("UserName"), null);

				actInResp.put(act, u);
			}

			compPerc = (float) attivitaComplete / (float) actInResp.size();

		} catch (SQLException exc) {
		}

		try {
			parts = getParticipantsFromProject(project);
		} catch (CustomException e) {
		}
		try {
			admin = getUser(project.getIdAdmin());
		} catch (CustomException e) {
		}

		if (admin == null && actInResp == null && parts == null)
			throw new CustomException(Errors.ProjectsNotFound);
		else
			resultInfo = new ProjectInfo(project.getName(), project.getIdProject(), project.isActive(), admin, actInResp, parts, compPerc);
		
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

	public static ArrayList<User> getNotMyFriends(int idUser) throws CustomException {
		ArrayList<User> result = null;
		try {
			String query = "SELECT * FROM utente WHERE idUtente NOT IN(SELECT idUtente2 "
					+ "FROM amicizia WHERE amicizia.idUtente1=?)";
			PreparedStatement stat = (PreparedStatement) connection.prepareStatement(query);

			stat.setInt(1, idUser);

			ResultSet rs = stat.executeQuery();

			result = new ArrayList<User>();
			while (rs.next()) {
				if (rs.getInt("idUtente") != idUser) {
					User u = new User(rs.getInt("idUtente"), rs.getString("Nome"), rs.getString("Cognome"),
							rs.getString("UserName"), null);
					result.add(u);
				}
			}

			if (result.isEmpty())
				throw new CustomException(Errors.UserNotFound);
		} catch (SQLException e) {			e.printStackTrace();
			throw new CustomException(Errors.ServerError);
		}

		return result;
	}

	public static int getActiveProjectsCount() throws CustomException {
		int count = 0;
		
		try {
			String query = "SELECT Count(*) AS numero_progetti FROM progetto WHERE ATTIVO = 1";
			PreparedStatement stat = (PreparedStatement) connection.prepareStatement(query);

			ResultSet rs = stat.executeQuery();

			if (rs.next()) {
				count = rs.getInt("numero_progetti");
			}

		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
		
		return count;
	}
	
	public static int getTotalProjectsCount() throws CustomException {
		int count = 0;
		
		try {
			String query = "SELECT Count(*) AS numero_progetti FROM progetto";
			PreparedStatement stat = (PreparedStatement) connection.prepareStatement(query);

			ResultSet rs = stat.executeQuery();

			if (rs.next()) {
				count = rs.getInt("numero_progetti");
			}

		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
		
		return count;
	}
	
	public static ArrayList<Activity> getAllActivities() throws CustomException
	{
		ArrayList<Activity> result = null;
		
		try {
			String query = "SELECT * FROM attivita";
			PreparedStatement stat = (PreparedStatement) connection.prepareStatement(query);

			ResultSet rs = stat.executeQuery();
			result = new ArrayList<Activity>();
			while (rs.next()) {
				Date d = null;
				Timestamp t = rs.getTimestamp("Ora");
				if (t != null)
					d = new Date(t.getTime());
				
				Activity act = new Activity(rs.getInt("idProgetto"), rs.getInt("idAttivita"), rs.getString("Nome"), 
									rs.getString("Luogo"), d, rs.getBoolean("Completata")); 
						
			
				result.add(act);
			}

			if (result.isEmpty())
				throw new CustomException(Errors.ActivitiesNotFound);
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
		
		return result;
	}
	
	public static int getDoneActivitiesCount() throws CustomException
	{
		try{
			String query = "SELECT Count(*) FROM attivita WHERE Completata = 1";
			java.sql.Statement stat = connection.createStatement();
			ResultSet rs = stat.executeQuery(query);
			
			if (rs.next())
				return rs.getInt(1);
		}
		catch (SQLException e)
		{
			throw new CustomException(Errors.ActivitiesNotFound);
		}
		return 0;
	}
	
	public static int getActivitiesCount() throws CustomException
	{
		try{
			String query = "SELECT Count(*) FROM attivita";
			java.sql.Statement stat = connection.createStatement();
			ResultSet rs = stat.executeQuery(query);
			
			if (rs.next())
				return rs.getInt(1);
		}
		catch (SQLException e)
		{
			throw new CustomException(Errors.ActivitiesNotFound);
		}
		return 0;
	}
	
	public static ActivityInfo getActivityInfo(Activity activity) throws CustomException {
		
		ActivityInfo activityInfo = null;
		ArrayList<User> responsabili = null;
		Project project = null;
		
		String query = "SELECT * FROM responsabile_attivita AS ar JOIN utente ON ar.idUtente = utente.idUtente " +
						"WHERE ar.idAttivita = ?";
		try {
			PreparedStatement stat = connection.prepareStatement(query);
			stat.setInt(1, activity.getIdActivity());
			
			ResultSet rs = stat.executeQuery();
			
			responsabili = new ArrayList<User>();
			while (rs.next())
			{
				User u = new User(rs.getInt("idUtente"), rs.getString("Nome"), rs.getString("Cognome"), rs.getString("UserName"), null);
				responsabili.add(u);
			}
		}
		catch (SQLException e)
		{
			throw new CustomException(Errors.ServerError);
		}
		
		try {
			query = "SELECT * FROM progetto as p JOIN attivita as a ON p.idProgetto = a.idProgetto WHERE idAttivita = ?";
			PreparedStatement stat = connection.prepareStatement(query);
			stat.setInt(1, activity.getIdActivity());
			
			ResultSet rs = stat.executeQuery();
			
			if (rs.next())
			{
				project = new Project(rs.getInt("idProgetto"), rs.getInt("idAttivita"), rs.getString("Nome"), rs.getBoolean("Attivo"));
			}
			
		}
		catch (SQLException e)
		{
			throw new CustomException(Errors.ServerError);
		}
		activityInfo = new ActivityInfo(activity.getIdActivity(), responsabili, project, activity.getName(),
						activity.getPlace(), activity.getHour(), activity.getIsDone(), activity.isFinishable());
		
		return activityInfo;
	}
	
	public static ArrayList<Activity> getActivitiesFromProject(Project project) throws CustomException
	{
		ArrayList<Activity> result = null;
		
		String query = "SELECT * FROM attivita WHERE idProgetto = ? ORDER BY attivita.idAttivita ASC";
		
		try {
			PreparedStatement stat = connection.prepareStatement(query);
			stat.setInt(1, project.getIdProject());
			
			ResultSet rs = stat.executeQuery();
			result = new ArrayList<Activity>();
			while (rs.next()) {
				Date d = null;
				Timestamp t = rs.getTimestamp("Ora");
				if (t != null)
					d = new Date(t.getTime());
				
				Activity act = new Activity(rs.getInt("idProgetto"), rs.getInt("idAttivita"), rs.getString("Nome"), 
									rs.getString("Luogo"), d, rs.getBoolean("Completata")); 
						
			
				result.add(act);
			}
			
		}
		catch (SQLException e)
		{
			throw new CustomException(Errors.ActivitiesNotFound);
		}
		
		return result;
	}
	
	public static Boolean canICompleteMyActivity(Activity activity) throws CustomException
	{
		Project p = new Project(activity.getIdProject());
		ArrayList<Activity> projectActivities = getActivitiesFromProject(p);
		
		for (Activity act : projectActivities){
			
			if (act.equals(activity))
				return true;
			
			if (!act.getIsDone())
				break;			
		}
		
		
		return false;
	}
	
	// METODI DI AGGIUNTA ENTRY AL DB
	public static void addActivity(Activity activity) throws CustomException {
		try {
			PreparedStatement stat = (PreparedStatement) connection
					.prepareStatement("INSERT INTO attivita (Nome, Luogo, Ora, idProgetto)" + "VALUES (?, ?, ?, ?)");

			stat.setString(1, activity.getName());
			stat.setString(2, activity.getPlace());
			Timestamp t = new Timestamp(activity.getHour().getTime());
			stat.setTimestamp(3, t);
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

	public static Project getProjectFromNameAndAdmin(String projectName, int idAdmin) throws CustomException {

		Project result = null;
		String query = "SELECT * FROM progetto WHERE Nome = ? AND idAdmin = ? ";

		try {
			PreparedStatement stat = (PreparedStatement) connection.prepareStatement(query);
			stat.setString(1, projectName);
			stat.setInt(2, idAdmin);
			ResultSet rs = stat.executeQuery();

			if (rs.next()) {
				result = new Project(rs.getInt("idProgetto"), rs.getInt("idAdmin"), rs.getString("Nome"),
						rs.getBoolean("Attivo"));
			} else
				throw new CustomException(Errors.ProjectsNotFound);
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
		return result;
	}

	public static Activity getActivityFromNameAndProject(String activityName, int idProject)throws CustomException {

		Activity result = null;
		String query = "SELECT * FROM attivita WHERE Nome = ? AND idProgetto = ?";

		try {
			PreparedStatement stat = (PreparedStatement) connection.prepareStatement(query);
			stat.setString(1, activityName);
			stat.setInt(2, idProject);
			ResultSet rs = stat.executeQuery();

			if (rs.next()) {
				Date d = null;
				Timestamp t = rs.getTimestamp("Ora");
				if (t != null)
					d = new Date(t.getTime());
				
				result = new Activity(rs.getInt("idProgetto"), rs.getInt("idAttivita"), rs.getString("Nome"),
						rs.getString("Luogo"), d, rs.getBoolean("Completata"));
			} else
				throw new CustomException(Errors.ProjectsNotFound);
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
		return result;
	}

	
	
	// METODI DI RIMOZIONE DA DB
	

	public static void removeProject(Project project) throws CustomException {
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
	
	public static void removeParticipantsFromProject(Project project) throws CustomException {
		String query = "DELETE FROM partecipante WHERE idProgetto = ?";

		try {
			PreparedStatement stat = connection.prepareStatement(query);

			stat.setInt(1, project.getIdProject());
			stat.executeUpdate();
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
	}
	
	public static void removeActivitiesFromProject(Project project) throws CustomException {
		String query = "DELETE FROM responsabile_attivita WHERE idAttivita IN "
				+ "(SELECT idAttivita FROM attivita WHERE idProgetto = ?)";
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
	
	public static void removeFriendship(Friendship friendship) throws CustomException {
		
		String query = "DELETE FROM amicizia WHERE idUtente1 = ? AND idUtente2 = ?";
		
		try {
			PreparedStatement stat = connection.prepareStatement(query);
			stat.setInt(1, friendship.getIdUtente1());
			stat.setInt(2, friendship.getIdUtente2());
			
			stat.executeUpdate();
		}
		catch (SQLException exception)
		{
			throw new CustomException(Errors.UserNotFound);
		}
		
	}

	
	
	// METODI DI GESTIONE UTENTI E forse SESSIONE
	
	public static void addClientToObservers(IClient client)
	{
		try
		{
			String query = "INSERT INTO utente_connesso (utente, connessione) " +
							"VALUES (?, ?)";
			PreparedStatement stat = connection.prepareStatement(query);
			
			stat.setObject(1, client);
			stat.setTimestamp(2, Timestamp.from(Instant.now()));
			
			stat.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void 

}
