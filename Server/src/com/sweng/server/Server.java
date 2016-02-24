package com.sweng.server;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import com.sweng.common.IClient;
import com.sweng.common.IServer;
import com.sweng.common.beans.Activity;
import com.sweng.common.beans.ActivityInfo;
import com.sweng.common.beans.ActivityResponsible;
import com.sweng.common.beans.Friendship;
import com.sweng.common.beans.Participant;
import com.sweng.common.beans.Project;
import com.sweng.common.beans.ProjectInfo;
import com.sweng.common.beans.User;
import com.sweng.common.notice.ActivityWithoutResponsibleNotice;
import com.sweng.common.notice.FinishedActivityNotice;
import com.sweng.common.notice.FinishedProjNotice;
import com.sweng.common.notice.FriendshipAdded;
import com.sweng.common.notice.Notice;
import com.sweng.common.notice.StartedProjNotice;
import com.sweng.common.notice.UnlockedActivityNotice;
import com.sweng.common.utils.CustomException;
import com.sweng.common.utils.DefaultMessages;
import com.sweng.common.utils.Errors;

public class Server extends UnicastRemoteObject implements IServer {
	
	interface IServerEvents {
		
		public void aggiornaProgetti();
		
		public void aggiornaUtenti();
		
		public void aggiornaAttivita();
		
	}
	
	IServerEvents listener;
	
	private static Server thisInstance = null;
	private DBManager dbMgr = null;
	
	public static Server getInstance(IServerEvents _listener) throws RemoteException {
		
		if (thisInstance == null)
			thisInstance = new Server(_listener);
			
		return thisInstance;
	}
	
	private Server(IServerEvents _listener) throws RemoteException {
		super();
		dbMgr = DBManager.getInstance();
		dbMgr.RemoveAllConnectedUsers();
		listener = _listener;
	}
	
	public ArrayList<User> getAllUsers() throws CustomException
	{
		return dbMgr.getAllUsers();
	}
	
	public ArrayList<IClient> getObservers() throws CustomException
	{
		return dbMgr.getObservers();
	}
	
	public ArrayList<Project> getAllProjects() throws CustomException
	{
		return dbMgr.getAllProjects();
	}
	
	public int getActiveProjectsCount() throws CustomException
	{
		return dbMgr.getActiveProjectsCount();
	}
	
	public ArrayList<Activity> getAllActivities() throws CustomException
	{
		return dbMgr.getAllActivities();
	}
	
	public int getDoneActivitiesCount() throws CustomException
	{
		return dbMgr.getDoneActivitiesCount();
	}
	
	// IMPLEMENTAZIONE METODI REMOTI
	
	@Override
	public Activity addActivity(Activity _activity) throws RemoteException, CustomException {
		
		_activity = dbMgr.addActivity(_activity);
		
		if (listener != null)
			listener.aggiornaAttivita();
			
		return _activity;
	}
	
	public void startProject(Project project) throws RemoteException, CustomException {
		
		dbMgr.setProjectActive(project);
		NotifyFirstActivityResponsible(project);
		NotifyAllParticipants(project, true);
	}
	
	@Override
	public void addActivityResponsible(ActivityResponsible _activityResponsible)
			throws RemoteException, CustomException {
			
		dbMgr.addActivityResponsible(_activityResponsible);
	}
	
	@Override
	public void addFriendship(Friendship _friendship) throws RemoteException, CustomException {
		
		dbMgr.addFriendship(_friendship);
		User u = dbMgr.getUser(_friendship.getIdUtente2());
		
		Notice n = new FriendshipAdded(dbMgr.getUser(_friendship.getIdUtente1()));
		NotifyUser(n, u);
	}
	
	@Override
	public void addParticipant(Participant _participant) throws RemoteException, CustomException {
		
		dbMgr.addParticipant(_participant);
	}
	
	@Override
	public Project addProject(Project _project) throws RemoteException, CustomException {
		
		_project = dbMgr.addProject(_project);
		
		if (listener != null)
			listener.aggiornaProgetti();
		return _project;
	}
	
	@Override
	public void addUser(User _user) throws RemoteException, CustomException {
		
		dbMgr.addUser(_user);
		
		if (listener != null)
			listener.aggiornaUtenti();
	}
	
	@Override
	public User performLogin(String username, String password) throws RemoteException, CustomException {
		
		User result = null;
		result = dbMgr.getUser(username, password);
		
		if (dbMgr.getConnectedUserByUserId(result.getIdUser()) != null)
			throw new CustomException(Errors.UserAlreadyLoggedIn);
			
		return result;
	}
	
	@Override
	public void addObserver(IClient _client) throws RemoteException {
		
		// Utente connesso alla ricezione delle notifiche
		dbMgr.addClientToObservers(_client);
		
		listener.aggiornaUtenti();
		
	}
	
	@Override
	public void removeObserver(IClient _client) throws RemoteException {
		
		try {
			dbMgr.removeObserver(_client);
			System.out.println("Utente rimosso");
			if (listener != null)
				listener.aggiornaUtenti();
		} catch (CustomException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public ArrayList<Activity> getActivityFromUser(User user) throws RemoteException, CustomException {
		
		ArrayList<Activity> result = null;
		result = dbMgr.getActivityFromUser(user);
		for (Activity act : result)
			act.setFinishable(dbMgr.canICompleteMyActivity(act));
			
		return result;
	}
	
	@Override
	public ArrayList<Project> getProjectsFromUsers(User user) throws RemoteException, CustomException {
		
		ArrayList<Project> result = null;
		result = dbMgr.getProjectsFromUser(user);
		
		return result;
	}
	
	@Override
	public ArrayList<User> getFriendsFromUser(User user) throws RemoteException, CustomException {
		
		ArrayList<User> result = null;
		result = dbMgr.getFriendsFromUser(user);
		
		return result;
	}
	
	@Override
	public ArrayList<User> getParticipantsFromProject(Project project) throws CustomException {
		
		ArrayList<User> result = null;
		result = dbMgr.getParticipantsFromProject(project);
		
		return result;
	}
	
	@Override
	public ProjectInfo getProjectInfo(Project project) throws CustomException {
		
		ProjectInfo result = null;
		result = dbMgr.getProjectInfo(project);
		
		return result;
	}
	
	@Override
	public ArrayList<User> getNotMyFriends(int idUser) throws CustomException, RemoteException {
		
		ArrayList<User> result = null;
		result = dbMgr.getNotMyFriends(idUser);
		
		return result;
	}
	
	@Override
	public void removeProject(Project project) throws RemoteException, CustomException {
		
		dbMgr.removeProject(project);
		if (listener != null)
			listener.aggiornaProgetti();
	}
	
	@Override
	public void removeFriendship(Friendship friendship) throws RemoteException, CustomException {
		
		dbMgr.removeFriendship(friendship);
	}
	
	@Override
	public ActivityInfo getActivityInfo(Activity activity) throws RemoteException, CustomException {
		
		return dbMgr.getActivityInfo(activity);
	}
	
	@Override
	public void setActivityDone(ActivityInfo activityInfo, User whoCompletedActivity)
			throws RemoteException, CustomException {
			
		dbMgr.setActivityDone(activityInfo);
		
		if (whoCompletedActivity != null)
			activityInfo.getResponsabili().remove(whoCompletedActivity);
			
		ArrayList<User> otherResponsibles = activityInfo.getResponsabili();
		
		Notice n = new FinishedActivityNotice(activityInfo, whoCompletedActivity);
		NotifyUser(n, otherResponsibles);
		
		// CONTROLLARE STATO ATTIVITA/PROGETTO PER NOTIFICHE
		
		ArrayList<Activity> activities = dbMgr.getActivitiesFromProject(activityInfo.getProject());
		
		Iterator iter = activities.iterator();
		while (iter.hasNext()) {
			Activity act = (Activity) iter.next();
			if (act.equals(activityInfo)) {
				if (!iter.hasNext())
					endOfProject(activityInfo.getProject());
				else
					notifyNextResponsible((Activity) iter.next());
					
				break;
			}
		}
		
		User admin = dbMgr.getUser(activityInfo.getProject().getIdAdmin());
		NotifyUser(n, admin);
		
		
	}
	
	private void NotifyFirstActivityResponsible(Project project) {
		
		try {
			ArrayList<Activity> activities = dbMgr.getActivitiesFromProject(project);
			ActivityInfo activityInfo = dbMgr.getActivityInfo(activities.get(0));
			
			UnlockedActivityNotice n = new UnlockedActivityNotice(activities.get(0));
			
			NotifyUser(n, activityInfo.getResponsabili());
			
		} catch (CustomException e) {
			System.out.println(e.getMessage() + "\n");
		}
		
	}
	
	private void notifyNextResponsible(Activity activity) {
		
		System.out.println("Attività completata. Notificare utente ");
		
		try {
			
			ArrayList<User> responsibles = dbMgr.getActivityInfo(activity).getResponsabili();
			for (User r : responsibles) {
				Date date = Date.from(Instant.now());
				String title = DefaultMessages.UnlockedActivityTitle.toString();
				String message = DefaultMessages.UnlockedActivity.toString();
				Notice n = new UnlockedActivityNotice(activity);
				
				NotifyUser(n, r);
			}
		} catch (CustomException e) {
			
			System.out.println(e.getMessage());
		}
		
	}
	
	private void NotifyAllParticipants(Project project, boolean isStarted) {
		
		try {
			ProjectInfo projectInfo = dbMgr.getProjectInfo(project);
			ArrayList<User> participants = dbMgr.getParticipantsFromProject(project);
			
			Notice notice = null;
			if (isStarted)
				notice = new StartedProjNotice(projectInfo);
			else
				notice = new FinishedProjNotice(projectInfo);
				
			NotifyUser(notice, participants);
			
		} catch (CustomException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void NotifyUser(Notice notice, ArrayList<User> users) {
		
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				IClient client;
				
				int idNotifica = dbMgr.storeNotice(notice);
				notice.setId(idNotifica);
				
				try {
					for (User u : users) {
						dbMgr.storeUserNotices(notice, u.getIdUser());
						client = dbMgr.getConnectedUserByUserId(u.getIdUser());
						
						if (client != null)
							try {
								client.update(notice);
							} catch (RemoteException e) {
								System.out.println("Errore di connessione. Notifica: " + notice.getTitle() + "\n"
										+ e.getMessage());
							}
							
					}
				} catch (CustomException e) {
					System.out.println(e.getMessage() + "\n");
				}
			}
		});
		thread.start();
		
	}
	
	private void NotifyUser(Notice notice, User user) throws CustomException {
		
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				IClient client;
				
				try {
					int idNotifica = dbMgr.storeNotice(notice);
					notice.setId(idNotifica);
					
					dbMgr.storeUserNotices(notice, user.getIdUser());
					
					client = dbMgr.getConnectedUserByUserId(user.getIdUser());
					if (client != null)
						client.update(notice);
						
				} catch (RemoteException | CustomException e) {
					System.out.println("Errore di connessione. Notifica: " + notice.getTitle() + "\n" + e.getMessage());
				}
				
			}
		});
		thread.start();
	}
	
	private void endOfProject(Project project) {
		
		NotifyAllParticipants(project, false);
	}
	
	@Override
	public ArrayList<Notice> getNoticeFromUser(User user) throws CustomException, RemoteException {
		
		ArrayList<Notice> result = null;
		result = dbMgr.getNoticesByUserId(user.getIdUser());
		
		return result;
	}
	
	@Override
	public void setNoticeRead(Notice notice, User user) throws RemoteException, CustomException {
		
		dbMgr.setNoticeDone(notice, user);
	}
	
	@Override
	public ArrayList<User> removeParticipant(Participant participant) throws RemoteException, CustomException {
		
		dbMgr.removeParticipant(participant);
		return dbMgr.getParticipantsFromProject(new Project(participant.getIdProject()));
	}
	
	@Override
	public ArrayList<User> removeActivityResponsible(ActivityResponsible resp) throws RemoteException, CustomException {
		
		dbMgr.removeActivityResponsible(resp);
		Activity activityFromId = dbMgr.getActivityFromId(resp.getIdActivity());
		
		ArrayList<User> responsabili = dbMgr.getActivityInfo(activityFromId).getResponsabili();
		
		if (!(responsabili.size() > 0)) {
			Activity activity = dbMgr.getActivityFromId(resp.getIdActivity());
			Project proj = dbMgr.getProjectFromActivity(activity);
			User admin = dbMgr.getProjectAdmin(proj);
			
			ActivityResponsible ar = new ActivityResponsible(admin.getIdUser(), activity.getIdActivity());
			addActivityResponsible(ar);
			
			Notice notice = new ActivityWithoutResponsibleNotice(proj, activity);
			NotifyUser(notice, admin);
		}
		
		return dbMgr.getActivityInfo(activityFromId).getResponsabili();
	}
	
	@Override
	public void addTexttoActivity(ActivityInfo activityInfo) throws RemoteException, CustomException {
		
		dbMgr.updateActivityText(activityInfo);
	}
	
}
