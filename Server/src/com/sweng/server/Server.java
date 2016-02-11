package com.sweng.server;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

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
	
	public Server(IServerEvents _listener) throws RemoteException {
		super();
		DBManager.RemoveAllConnectedUsers();
		listener = _listener;
		// TODO Auto-generated constructor stub
	}
	
	// IMPLEMENTAZIONE METODI REMOTI
	
	@Override
	public Activity addActivity(Activity _activity) throws RemoteException, CustomException {
		
		_activity = DBManager.addActivity(_activity);
		
		if (listener != null)
			listener.aggiornaAttivita();
			
		return _activity;
	}
	
	public void startProject(Project project) throws RemoteException, CustomException {
		
		NotifyFirstActivityResponsible(project.getIdProject());
		NotifyAllParticipants(project.getIdProject(), true);
	}
	
	@Override
	public void addActivityResponsible(ActivityResponsible _activityResponsible) throws RemoteException {
		
		try {
			DBManager.addActivityResponsible(_activityResponsible);
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void addFriendship(Friendship _friendship) throws RemoteException {
		
		try {
			DBManager.addFriendship(_friendship);
			User u = DBManager.getUser(_friendship.getIdUtente2());
			
			Notice n = new FriendshipAdded(DBManager.getUser(_friendship.getIdUtente1()));
			NotifyUser(n, u);
			
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void addParticipant(Participant _participant) throws RemoteException {
		
		try {
			DBManager.addParticipant(_participant);
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public Project addProject(Project _project) throws RemoteException, CustomException {
		
		_project = DBManager.addProject(_project);
		
		if (listener != null)
			listener.aggiornaProgetti();
		return _project;
	}
	
	@Override
	public void addUser(User _user) throws RemoteException {
		
		// TODO: è il metodo per il sign-in?
		try {
			DBManager.addUser(_user);
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (listener != null)
			listener.aggiornaUtenti();
	}
	
	@Override
	public User performLogin(String username, String password) throws RemoteException, CustomException {
		
		User result = null;
		result = DBManager.getUser(username, password);
		
		if (DBManager.getConnectedUserByUserId(result.getIdUser()) != null)
			throw new CustomException(Errors.UserAlreadyLoggedIn);
		
		return result;
	}
	
	@Override
	public void addObserver(IClient _client) throws RemoteException {
		
		// Utente connesso alla ricezione delle notifiche
		DBManager.addClientToObservers(_client);
		
		listener.aggiornaUtenti();
		
	}
	
	@Override
	public void removeObserver(IClient _client) throws RemoteException {
		
		try {
			DBManager.removeObserver(_client);
			System.out.println("Utente rimosso");
			if (listener != null)
				listener.aggiornaUtenti();
		} catch (CustomException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public void notifyObservers(Notice _notice) throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public ArrayList<Activity> getActivityFromUser(User user) throws RemoteException, CustomException {
		
		ArrayList<Activity> result = null;
		result = DBManager.getActivityFromUser(user);
		for (Activity act : result)
			act.setFinishable(DBManager.canICompleteMyActivity(act));
			
		return result;
	}
	
	@Override
	public ArrayList<Project> getProjectsFromUsers(User user) throws RemoteException, CustomException {
		
		ArrayList<Project> result = null;
		result = DBManager.getProjectsFromUser(user);
		
		return result;
	}
	
	@Override
	public ArrayList<User> getFriendsFromUser(User user) throws RemoteException, CustomException {
		
		ArrayList<User> result = null;
		result = DBManager.getFriendsFromUser(user);
		
		return result;
	}
	
	@Override
	public ArrayList<User> getParticipantsFromProject(Project project) throws CustomException {
		
		ArrayList<User> result = null;
		result = DBManager.getParticipantsFromProject(project);
		
		return result;
	}
	
	@Override
	public ProjectInfo getProjectInfo(Project project) throws CustomException {
		
		ProjectInfo result = null;
		result = DBManager.getProjectInfo(project);
		
		return result;
	}
	
	@Override
	public ArrayList<User> getNotMyFriends(int idUser) throws CustomException, RemoteException {
		
		ArrayList<User> result = null;
		result = DBManager.getNotMyFriends(idUser);
		
		return result;
	}
	
	@Override
	public void removeProject(Project project) throws RemoteException, CustomException {
		
		DBManager.removeProject(project);
		if (listener != null)
			listener.aggiornaProgetti();
	}
	
	@Override
	public void removeFriendship(Friendship friendship) throws RemoteException, CustomException {
		
		DBManager.removeFriendship(friendship);
	}
	
	@Override
	public ActivityInfo getActivityInfo(Activity activity) throws RemoteException, CustomException {
		
		return DBManager.getActivityInfo(activity);
	}
	
	@Override
	public void setActivityDone(ActivityInfo activityInfo, User whoCompletedActivity)
			throws RemoteException, CustomException {
			
		DBManager.setActivityDone(activityInfo);
		
		if (whoCompletedActivity != null)
			activityInfo.getResponsabili().remove(whoCompletedActivity);
		
		ArrayList<User> otherResponsibles = activityInfo.getResponsabili();
		
		Notice n = new FinishedActivityNotice(activityInfo);
		NotifyUser(n, otherResponsibles);
		
		// CONTROLLARE STATO ATTIVITA/PROGETTO PER NOTIFICHE
		
		ArrayList<Activity> activities = DBManager.getActivitiesFromProject(activityInfo.getProject());
		
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
		
	}
	
	private void NotifyFirstActivityResponsible(int idProject) {
		
		try {
			ArrayList<Activity> activities = DBManager.getActivitiesFromProject(new Project(idProject));
			ActivityInfo activityInfo = DBManager.getActivityInfo(activities.get(0));
			
			UnlockedActivityNotice n = new UnlockedActivityNotice(activities.get(0));
			
			NotifyUser(n, activityInfo.getResponsabili());
			
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void notifyNextResponsible(Activity activity) {
		
		System.out.println("Attività completata. Notificare utente ");
		
		try {
			
			ArrayList<User> responsibles = DBManager.getActivityInfo(activity).getResponsabili();
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
	
	private void NotifyAllParticipants(int idProject, boolean isStarted) {
		
		try {
			ProjectInfo projectInfo = DBManager.getProjectInfo(new Project(idProject));
			ArrayList<User> participants = DBManager.getParticipantsFromProject(new Project(idProject));
			
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
				
				try {
					int idNotifica = DBManager.storeNotice(notice);
					notice.setId(idNotifica);
					
					for (User u : users) {
						DBManager.storeUserNotices(notice, u.getIdUser());
						
						client = DBManager.getConnectedUserByUserId(u.getIdUser());
						if (client != null)
							client.update(notice);
					}
					
				} catch (CustomException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RemoteException e) {
					System.out.println("Errore di connessione. Notifica: " + notice.getTitle() + "\n" + e.getMessage());
				}
				
			}
		});
		thread.start();
		
	}
	
	private void NotifyUser(Notice notice, User user) {
		
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				IClient client;
				
				try {
					int idNotifica = DBManager.storeNotice(notice);
					notice.setId(idNotifica);
					
					DBManager.storeUserNotices(notice, user.getIdUser());
					
					client = DBManager.getConnectedUserByUserId(user.getIdUser());
					if (client != null)
						client.update(notice);
						
				} catch (CustomException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RemoteException e) {
					System.out.println("Errore di connessione. Notifica: " + notice.getTitle() + "\n" + e.getMessage());
				}
				
			}
		});
		thread.start();
	}
	
	private void endOfProject(Project project) {
		
		NotifyAllParticipants(project.getIdProject(), false);
	}
	
	@Override
	public ArrayList<Notice> getNoticeFromUser(User user) throws CustomException, RemoteException {
		
		ArrayList<Notice> result = null;
		result = DBManager.getNoticesByUserId(user.getIdUser());
		
		return result;
	}
	
	@Override
	public void setNoticeRead(Notice notice, User user) throws RemoteException, CustomException {
		
		DBManager.setNoticeDone(notice, user);
	}
	
	@Override
	public ArrayList<User> removeParticipant(Participant participant) throws RemoteException, CustomException {
		
		DBManager.removeParticipant(participant);
		return DBManager.getParticipantsFromProject(new Project(participant.getIdProject()));
	}
	
	@Override
	public ArrayList<User> removeActivityResponsible(ActivityResponsible resp) throws RemoteException, CustomException {
		
		DBManager.removeActivityResponsible(resp);
		
		ArrayList<User> responsabili = DBManager.getActivityInfo(new Activity(resp.getIdActivity())).getResponsabili();
		
		if (!(responsabili.size() > 0)) {
			Activity activity = DBManager.getActivityFromId(resp.getIdActivity());
			Project proj = DBManager.getProjectFromActivity(activity);
			User admin = DBManager.getProjectAdmin(proj);
			
			ActivityResponsible ar = new ActivityResponsible(admin.getIdUser(), activity.getIdActivity());
			addActivityResponsible(ar);
			
			Notice notice = new ActivityWithoutResponsibleNotice(proj, activity);
			NotifyUser(notice, admin);
		}
		
		return DBManager.getActivityInfo(new Activity(resp.getIdActivity())).getResponsabili();
	}
	
	@Override
	public void addTexttoActivity(ActivityInfo activityInfo) throws RemoteException, CustomException {
		
		DBManager.updateActivityText(activityInfo);
	}
	
}
