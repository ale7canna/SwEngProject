package com.sweng.server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.rmi.RemoteException;
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
import java.util.Set;

import javax.swing.JOptionPane;

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
import com.sweng.common.notice.Notice;
import com.sweng.common.utils.CustomException;
import com.sweng.common.utils.Errors;

public class DBManager {
	
	private static DBManager thisInstance = null;
	private Connection connection = null;
	
	public static DBManager getInstance() {
		
		if (thisInstance == null)
			thisInstance = new DBManager();
		return thisInstance;
	}
	
	private DBManager() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = (Connection) DriverManager.getConnection(Consts.DB_URL.concat("?useSSL=false"),
					Consts.DB_USERNAME, Consts.DB_PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			if (e instanceof ClassNotFoundException)
				JOptionPane.showMessageDialog(null, "DB connection failed due to missing library file.");
			else
				JOptionPane.showMessageDialog(null, "DB conection failed.");
		}
	}
	
	// METODI DI INTERROGAZIONE DB
	public ArrayList<Activity> getActivityFromUser(User user) throws CustomException {
		
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
						rs.getString("Luogo"), d, rs.getBoolean("Completata"), rs.getString("Testo"));
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
	
	public ArrayList<Project> getAllProjects() throws CustomException {
		
		ArrayList<Project> result = null;
		try {
			String query = "SELECT * FROM progetto";
			PreparedStatement stat = (PreparedStatement) connection.prepareStatement(query);
			
			ResultSet rs = stat.executeQuery();
			
			result = new ArrayList<Project>();
			while (rs.next()) {
				Project proj = new Project(rs.getInt("idProgetto"), rs.getInt("idAdmin"), rs.getString("Nome"),
						rs.getBoolean("Attivo"), rs.getBoolean("isComplete"));
				result.add(proj);
			}
			
			if (result.isEmpty())
				throw new CustomException(Errors.ProjectsNotFound);
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
		return result;
	}
	
	public ArrayList<User> getAllUsers() throws CustomException {
		
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
	
	public User getUser(String username, String password) throws CustomException {
		
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
	
	public User getUser(int id) throws CustomException {
		
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
	
	public ArrayList<Project> getProjectsFromUser(User user) throws CustomException {
		
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
						rs.getBoolean("Attivo"), rs.getBoolean("isComplete"));
				result.add(p);
			}
			
			if (result.isEmpty())
				throw new CustomException(Errors.ProjectsNotFound);
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
		
		return result;
	}
	
	public ArrayList<User> getFriendsFromUser(User user) throws CustomException {
		
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
	
	public ProjectInfo getProjectInfo(Project _project) throws CustomException {
		
		Project project = getProjectFromId(_project.getIdProject());
		
		ProjectInfo resultInfo = null;
		int attivitaComplete = 0;
		HashMap<Activity, ArrayList<User>> actInResp = new HashMap<Activity, ArrayList<User>>();
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
				Date d = null;
				Timestamp t = rs.getTimestamp("Ora");
				if (t != null)
					d = new Date(t.getTime());
					
				Activity act = new Activity(project.getIdProject(), rs.getInt("ar.idAttivita"),
						rs.getString("attivita.Nome"), rs.getString("Luogo"), d, completa, rs.getString("Testo"));
				act.setFinishable(canICompleteMyActivity(act));
				User u = new User(rs.getInt("ar.idUtente"), rs.getString("utente.Nome"), rs.getString("Cognome"),
						rs.getString("UserName"), null);
						
				Set<Activity> activities = actInResp.keySet();
				Activity MyRightActivity = null;
				for (Activity a : activities) {
					if (a.equals(act))
						MyRightActivity = a;
				}
				if (MyRightActivity == null)
				{
					MyRightActivity = act;
					if (MyRightActivity.getIsDone())
						attivitaComplete++;
				}
								
				ArrayList<User> arrayList = actInResp.get(MyRightActivity);
				if (arrayList == null)
					arrayList = new ArrayList<User>();
				arrayList.add(u);
				actInResp.put(MyRightActivity, arrayList);
				
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
			resultInfo = new ProjectInfo(project.getName(), project.getIdProject(), project.isActive(), project.isComplete(), admin,
					actInResp, parts, compPerc);
					
		return resultInfo;
		
	}
	
	public ArrayList<User> getParticipantsFromProject(Project project) throws CustomException {
		
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
	
	public ArrayList<User> getNotMyFriends(int idUser) throws CustomException {
		
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
				throw new CustomException(Errors.AllUsersYetFriends);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CustomException(Errors.ServerError);
		}
		
		return result;
	}
	
	public int getActiveProjectsCount() throws CustomException {
		
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
	
	public int getTotalProjectsCount() throws CustomException {
		
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
	
	public ArrayList<Activity> getAllActivities() throws CustomException {
		
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
						rs.getString("Luogo"), d, rs.getBoolean("Completata"), rs.getString("Testo"));
						
				result.add(act);
			}
			
			if (result.isEmpty())
				throw new CustomException(Errors.ActivitiesNotFound);
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
		
		return result;
	}
	
	public int getDoneActivitiesCount() throws CustomException {
		
		try {
			String query = "SELECT Count(*) FROM attivita WHERE Completata = 1";
			java.sql.Statement stat = connection.createStatement();
			ResultSet rs = stat.executeQuery(query);
			
			if (rs.next())
				return rs.getInt(1);
		} catch (SQLException e) {
			throw new CustomException(Errors.ActivitiesNotFound);
		}
		return 0;
	}
	
	public int getActivitiesCount() throws CustomException {
		
		try {
			String query = "SELECT Count(*) FROM attivita";
			java.sql.Statement stat = connection.createStatement();
			ResultSet rs = stat.executeQuery(query);
			
			if (rs.next())
				return rs.getInt(1);
		} catch (SQLException e) {
			throw new CustomException(Errors.ActivitiesNotFound);
		}
		return 0;
	}
	
	public ActivityInfo getActivityInfo(Activity activity) throws CustomException {
		
		ActivityInfo activityInfo = null;
		ArrayList<User> responsabili = null;
		Project project = null;
		
		String query = "SELECT * FROM responsabile_attivita AS ar JOIN utente ON ar.idUtente = utente.idUtente "
				+ "WHERE ar.idAttivita = ?";
		try {
			PreparedStatement stat = connection.prepareStatement(query);
			stat.setInt(1, activity.getIdActivity());
			
			ResultSet rs = stat.executeQuery();
			
			responsabili = new ArrayList<User>();
			while (rs.next()) {
				User u = new User(rs.getInt("idUtente"), rs.getString("Nome"), rs.getString("Cognome"),
						rs.getString("UserName"), null);
				responsabili.add(u);
			}
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
		
		try {
			query = "SELECT * FROM progetto as p JOIN attivita as a ON p.idProgetto = a.idProgetto WHERE idAttivita = ?";
			PreparedStatement stat = connection.prepareStatement(query);
			stat.setInt(1, activity.getIdActivity());
			
			ResultSet rs = stat.executeQuery();
			
			if (rs.next()) {
				project = new Project(rs.getInt("idProgetto"), rs.getInt("idAdmin"), rs.getString("Nome"),
						rs.getBoolean("Attivo"), rs.getBoolean("isComplete"));
			}
			
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
		activityInfo = new ActivityInfo(activity.getIdActivity(), responsabili, project, activity.getName(),
				activity.getPlace(), activity.getHour(), activity.getIsDone(), canICompleteMyActivity(activity),
				activity.getText());
				
		return activityInfo;
	}
	
	public ArrayList<Activity> getActivitiesFromProject(Project project) throws CustomException {
		
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
						rs.getString("Luogo"), d, rs.getBoolean("Completata"), rs.getString("Testo"));
						
				result.add(act);
			}
			
		} catch (SQLException e) {
			throw new CustomException(Errors.ActivitiesNotFound);
		}
		
		return result;
	}
	
	public Boolean canICompleteMyActivity(Activity activity) throws CustomException {
		
		Project p = getProjectFromActivity(activity);
		ArrayList<Activity> projectActivities = getActivitiesFromProject(p);
		
		if (!p.isActive())
			return false;
		for (Activity act : projectActivities) {
			
			if (act.equals(activity)) {
				if (!act.getIsDone())
					return true;
				else
					return false;
			}
			
			if (!act.getIsDone())
				break;
		}
		
		return false;
	}
	
	// METODI DI AGGIUNTA ENTRY AL DB
	public Activity addActivity(Activity activity) throws CustomException {
		
		try {
			PreparedStatement stat = (PreparedStatement) connection.prepareStatement(
					"INSERT INTO attivita (Nome, Luogo, Ora, idProgetto, Testo)" + "VALUES (?, ?, ?, ?, ?)",
					new String[] { "idAttivita" });
					
			stat.setString(1, activity.getName());
			stat.setString(2, activity.getPlace());
			Timestamp t = new Timestamp(activity.getHour().getTime());
			stat.setTimestamp(3, t);
			stat.setInt(4, activity.getIdProject());
			stat.setString(5, activity.getText());
			
			stat.executeUpdate();
			
			ResultSet rs = stat.getGeneratedKeys();
			int idAttivita = -1;
			if (rs.next())
				idAttivita = rs.getInt(1);
			else
				throw new CustomException(Errors.ServerError);
				
			activity.setIdActivity(idAttivita);
			return activity;
			
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
	}
	
	public void addActivityResponsible(ActivityResponsible ar) throws CustomException {
		
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
	
	public void addFriendship(Friendship friendship) throws CustomException {
		
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
	
	public void addParticipant(Participant participant) throws CustomException {
		
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
	
	public Project addProject(Project project) throws CustomException {
		
		try {
			PreparedStatement stat = (PreparedStatement) connection.prepareStatement(
					"INSERT INTO progetto (Nome, idAdmin, Attivo)" + "VALUES (?, ?, ?)", new String[] { "idProgetto" });
					
			stat.setString(1, project.getName());
			stat.setInt(2, project.getIdAdmin());
			stat.setBoolean(3, false);
			stat.executeUpdate();
			
			ResultSet rs = stat.getGeneratedKeys();
			int idProject = -1;
			if (rs.next())
				idProject = rs.getInt(1);
			else
				throw new CustomException(Errors.ServerError);
				
			project.setIdProject(idProject);
			return project;
			
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
	}
	
	public void addUser(User user) throws CustomException {
		
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
	
	private boolean userExists(String username) throws CustomException {
		
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
	
	public Project getProjectFromId(int idProject) throws CustomException {
		
		Project result = null;
		String query = "SELECT * FROM progetto WHERE idProgetto = ? ";
		
		try {
			PreparedStatement stat = (PreparedStatement) connection.prepareStatement(query);
			stat.setInt(1, idProject);
			ResultSet rs = stat.executeQuery();
			
			if (rs.next()) {
				result = new Project(rs.getInt("idProgetto"), rs.getInt("idAdmin"), rs.getString("Nome"),
						rs.getBoolean("Attivo"), rs.getBoolean("isComplete"));
			} else
				throw new CustomException(Errors.ProjectsNotFound);
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
		return result;
	}
	
	public Activity getActivityFromNameAndProject(String activityName, int idProject) throws CustomException {
		
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
						rs.getString("Luogo"), d, rs.getBoolean("Completata"), rs.getString("Testo"));
			} else
				throw new CustomException(Errors.ProjectsNotFound);
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
		return result;
	}
	
	public Activity getActivityFromId(int idActivity) throws CustomException {
		
		Activity result = null;
		String query = "SELECT * FROM attivita WHERE idAttivita = ?";
		
		try {
			PreparedStatement stat = (PreparedStatement) connection.prepareStatement(query);
			stat.setInt(1, idActivity);
			ResultSet rs = stat.executeQuery();
			
			if (rs.next()) {
				Date d = null;
				Timestamp t = rs.getTimestamp("Ora");
				if (t != null)
					d = new Date(t.getTime());
					
				result = new Activity(rs.getInt("idProgetto"), rs.getInt("idAttivita"), rs.getString("Nome"),
						rs.getString("Luogo"), d, rs.getBoolean("Completata"), rs.getString("Testo"));
			} else
				throw new CustomException(Errors.ActivitiesNotFound);
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
		return result;
	}
	
	public void setActivityDone(ActivityInfo activityInfo) throws CustomException {
		
		try {
			String query = "UPDATE attivita SET Completata = 1 WHERE idAttivita = ?";
			PreparedStatement stat = connection.prepareStatement(query);
			
			stat.setInt(1, activityInfo.getIdActivity());
			
			stat.executeUpdate();
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
		
	}
	
	// METODI DI RIMOZIONE DA DB
	
	public void removeProject(Project project) throws CustomException {
		
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
	
	public void removeParticipantsFromProject(Project project) throws CustomException {
		
		String query = "DELETE FROM partecipante WHERE idProgetto = ?";
		
		try {
			PreparedStatement stat = connection.prepareStatement(query);
			
			stat.setInt(1, project.getIdProject());
			stat.executeUpdate();
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
	}
	
	public void removeActivitiesFromProject(Project project) throws CustomException {
		
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
	
	public void removeFriendship(Friendship friendship) throws CustomException {
		
		String query = "DELETE FROM amicizia WHERE idUtente1 = ? AND idUtente2 = ?";
		
		try {
			PreparedStatement stat = connection.prepareStatement(query);
			stat.setInt(1, friendship.getIdUtente1());
			stat.setInt(2, friendship.getIdUtente2());
			
			stat.executeUpdate();
		} catch (SQLException exception) {
			throw new CustomException(Errors.UserNotFound);
		}
		
	}
	
	// METODI DI GESTIONE UTENTI E forse SESSIONE
	
	public void addClientToObservers(IClient client) {
		
		try {
			String query = "INSERT INTO utente_connesso (utente, connessione, username, id_utente) "
					+ "VALUES (?, ?, ?, ?)";
			PreparedStatement stat = connection.prepareStatement(query);
			
			stat.setObject(1, client);
			stat.setTimestamp(2, Timestamp.from(Instant.now()));
			stat.setString(3, client.getUsername());
			stat.setInt(4, client.getId());
			
			stat.executeUpdate();
		} catch (SQLException | RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<IClient> getObservers() throws CustomException {
		
		ArrayList<IClient> result = null;
		try {
			String query = "SELECT * FROM utente_connesso";
			
			PreparedStatement stat = connection.prepareStatement(query);
			
			ResultSet rs = stat.executeQuery();
			result = new ArrayList<>();
			while (rs.next()) {
				// IClient c = getClientFromBytes(rs.getBytes("utente"));
				IClient c = getObjectFromBytes(rs.getBytes("utente"));
				result.add(c);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void RemoveAllConnectedUsers() {
		
		try {
			String query = "DELETE FROM utente_connesso WHERE 1";
			connection.prepareStatement(query).executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public IClient getConnectedUserByUserId(int id) throws CustomException {
		
		try {
			String query = "SELECT utente FROM utente_connesso WHERE id_utente = ? ";
			PreparedStatement stat = connection.prepareStatement(query);
			stat.setInt(1, id);
			ResultSet rs = stat.executeQuery();
			
			if (rs.next())
				return getObjectFromBytes(rs.getBytes("utente"));
				
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
		
		return null;
		
	}
	
	public void removeObserver(IClient _client) throws CustomException {
		
		try {
			String query = "DELETE FROM utente_connesso WHERE id_utente = ?";
			PreparedStatement stat = connection.prepareStatement(query);
			stat.setInt(1, _client.getId());
			
			stat.executeUpdate();
		} catch (SQLException e) {
			throw new CustomException(Errors.UserNotFound);
		} catch (RemoteException e) {
			throw new CustomException(Errors.NetworkError);
		}
	}
	
	public ArrayList<Notice> getNoticesByUserId(int userId) throws CustomException {
		
		ArrayList<Notice> result = null;
		
		try {
			String query = "SELECT id, notifica, notifica_class FROM notifica AS n JOIN notifica_utente AS nu ON n.id = nu.id_notifica "
					+ "WHERE nu.id_utente = ?";
					
			PreparedStatement stat = connection.prepareStatement(query);
			stat.setInt(1, userId);
			
			ResultSet rs = stat.executeQuery();
			result = new ArrayList<>();
			while (rs.next()) {
				byte[] buffer = rs.getBytes("notifica");
				String className = rs.getString("notifica_class");
				try {
					Class specificNotice = Class.forName(className);
					Notice n = (Notice) specificNotice.cast(getObjectFromBytes(buffer));
					n.setId(rs.getInt("id"));
					result.add(n);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					throw new CustomException(Errors.ServerError);
				}
			}
			
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
		
		return result;
	}
	
	private <T> T getObjectFromBytes(byte[] buffer) throws CustomException {
		
		ObjectInputStream objectIn = null;
		if (buffer != null)
			try {
				objectIn = new ObjectInputStream(new ByteArrayInputStream(buffer));
			} catch (IOException e) {
				System.out.println(e.getMessage() + "\n");
				throw new CustomException(Errors.ServerError);
			}
			
		Object deSerializedObject = null;
		try {
			deSerializedObject = objectIn.readObject();
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage() + "\n");
			throw new CustomException(Errors.ServerError);
		} catch (IOException e) {
			System.out.println(e.getMessage() + "\n");
			throw new CustomException(Errors.ServerError);
		}
		
		return (T) deSerializedObject;
		
	}
	
	public void setNoticeDone(Notice notice, User user) throws CustomException {
		
		try {
			String query = "DELETE FROM notifica_utente WHERE id_utente = ? AND id_notifica = ?";
			PreparedStatement stat = connection.prepareStatement(query);
			
			stat.setInt(1, user.getIdUser());
			stat.setInt(2, notice.getId());
			
			stat.executeUpdate();
			
			query = "SELECT Count(*) FROM notifica_utente WHERE id_notifica = ?";
			stat = connection.prepareStatement(query);
			stat.setInt(1, notice.getId());
			
			ResultSet rs = stat.executeQuery();
			int sameNoticesRemaining = 0;
			if (rs.next())
				sameNoticesRemaining = rs.getInt(1);
				
			if (!(sameNoticesRemaining > 0)) {
				query = "DELETE FROM notifica WHERE id = ?";
				stat = connection.prepareStatement(query);
				stat.setInt(1, notice.getId());
				stat.executeUpdate();
			}
			
		} catch (Exception e) {
			throw new CustomException(Errors.ServerError);
		}
		
	}
	
	public void removeParticipant(Participant participant) throws CustomException {
		
		try {
			String query = "DELETE FROM partecipante WHERE idUtente = ? AND idProgetto = ?";
			PreparedStatement stat = connection.prepareStatement(query);
			
			stat.setInt(1, participant.getIdUser());
			stat.setInt(2, participant.getIdProject());
			
			stat.executeUpdate();
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
	}
	
	public void removeActivityResponsible(ActivityResponsible resp) throws CustomException {
		
		try {
			String query = "DELETE FROM responsabile_attivita WHERE idUtente = ? AND idAttivita = ?";
			PreparedStatement stat = connection.prepareStatement(query);
			
			stat.setInt(1, resp.getIdUser());
			stat.setInt(2, resp.getIdActivity());
			
			stat.executeUpdate();
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
	}
	
	public int storeNotice(Notice notice) {
		
		try {
			String query = "INSERT INTO notifica (notifica, notifica_class) VALUES (?, ?)";
			PreparedStatement stat = connection.prepareStatement(query, new String[] { "id" });
			stat.setObject(1, notice);
			stat.setString(2, notice.getClass().getName());
			
			stat.executeUpdate();
			
			ResultSet rs = stat.getGeneratedKeys();
			int idNotice = -1;
			if (rs.next())
				idNotice = rs.getInt(1);
				
			return idNotice;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void storeUserNotices(Notice notice, int idUser) {
		
		try {
			String query = "INSERT INTO notifica_utente (id_utente, id_notifica) VALUES (?, ?)";
			PreparedStatement stat = connection.prepareStatement(query);
			stat.setInt(1, idUser);
			stat.setInt(2, notice.getId());
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getRemainingActivityResponsible(int idActivity) throws CustomException {
		
		try {
			String query = "SELECT Count(*) FROM responsabili_attivita WHERE idAttivita = ?";
			PreparedStatement stat = connection.prepareStatement(query);
			
			ResultSet rs = stat.executeQuery();
			if (rs.next())
				return rs.getInt(1);
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
		
		return 0;
	}
	
	public User getProjectAdmin(Project project) throws CustomException {
		
		User result = null;
		try {
			String query = "SELECT * FROM utente WHERE idUtente = ?";
			PreparedStatement stat = connection.prepareStatement(query);
			stat.setInt(1, project.getIdAdmin());
			ResultSet rs = stat.executeQuery();
			
			if (rs.next())
				return new User(rs.getInt("idUtente"), rs.getString("Nome"), rs.getString("Cognome"),
						rs.getString("UserName"), rs.getString("password"));
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
		
		return result;
	}
	
	public Project getProjectFromActivity(Activity activity) throws CustomException {
		
		try {
			String query = "SELECT * FROM progetto WHERE idProgetto = ?";
			PreparedStatement stat = connection.prepareStatement(query);
			stat.setInt(1, activity.getIdProject());
			
			ResultSet rs = stat.executeQuery();
			if (rs.next())
				return new Project(rs.getInt("idProgetto"), rs.getInt("idAdmin"), rs.getString("Nome"),
						rs.getBoolean("Attivo"), rs.getBoolean("isComplete"));
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
		return null;
	}
	
	public void updateActivityText(ActivityInfo activityInfo) throws CustomException {
		
		try {
			String query = "UPDATE attivita SET Testo = ? WHERE idAttivita = ?";
			PreparedStatement stat = connection.prepareStatement(query);
			
			stat.setString(1, activityInfo.getText());
			stat.setInt(2, activityInfo.getIdActivity());
			
			int affectedRows = stat.executeUpdate();
			
			if (affectedRows != 1)
				throw new CustomException(Errors.ActivitiesNotFound);
				
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
	}

	public void setProjectActive(Project project) throws CustomException {
		
		try {
			String query = "UPDATE progetto SET Attivo = 1 WHERE idProgetto = ?";
			PreparedStatement stat = connection.prepareStatement(query);
			
			stat.setInt(1, project.getIdProject());
			
			int affectedRows = stat.executeUpdate();
			
			if (affectedRows != 1)
				throw new CustomException(Errors.ProjectsNotFound);
				
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
	}
	
	public void setProjectComplete(Project project) throws CustomException {
		
		try {
			String query = "UPDATE progetto SET isComplete = 1 WHERE idProgetto = ?";
			PreparedStatement stat = connection.prepareStatement(query);
			
			stat.setInt(1, project.getIdProject());
			
			int affectedRows = stat.executeUpdate();
			
			if (affectedRows != 1)
				throw new CustomException(Errors.ProjectsNotFound);
				
		} catch (SQLException e) {
			throw new CustomException(Errors.ServerError);
		}
	}
	
}
