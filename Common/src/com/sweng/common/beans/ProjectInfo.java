package com.sweng.common.beans;

import java.util.ArrayList;
import java.util.HashMap;

public class ProjectInfo {
	
	private String name;
	private int idProject;
	private boolean isActive;
	private User admin;
	private HashMap<Activity, User> activitiesInResponsible;
	private ArrayList<User> participants;
	private float completetionPercentage;
	
	
	
	public ProjectInfo(String name, int idProject, boolean isActive, User admin,
			HashMap<Activity, User> activitiesInResponsible, ArrayList<User> participants,
			float completetionPercentage) {
		super();
		this.name = name;
		this.idProject = idProject;
		this.isActive = isActive;
		this.admin = admin;
		this.activitiesInResponsible = activitiesInResponsible;
		this.participants = participants;
		this.completetionPercentage = completetionPercentage;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIdProject() {
		return idProject;
	}
	public void setIdProject(int idProject) {
		this.idProject = idProject;
	}
	public User getAdmin() {
		return admin;
	}
	public void setAdmin(User admin) {
		this.admin = admin;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public HashMap<Activity, User> getActivitiesInResponsible() {
		return activitiesInResponsible;
	}
	public void setActivitiesInResponsible(HashMap<Activity, User> activitiesInResponsible) {
		this.activitiesInResponsible = activitiesInResponsible;
	}
	public ArrayList<User> getParticipants() {
		return participants;
	}
	public void setParticipants(ArrayList<User> participants) {
		this.participants = participants;
	}
	public float getCompletetionPercentage() {
		return completetionPercentage;
	}
	public void setCompletetionPercentage(float completetionPercentage) {
		this.completetionPercentage = completetionPercentage;
	}
	
	
	
	
}
