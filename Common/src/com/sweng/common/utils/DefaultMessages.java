package com.sweng.common.utils;


public enum DefaultMessages {
	StartedActivityTitle("Attività iniziata"),
	StartedActivity("Bla bla"),
	StartedProjTitle("Aggiunta ad un progetto"),
	StartedProj("Sei stato aggiunto ad un progetto. Apri la notifica per maggiori dettagli."),
	UnlockedActivity("La tua attività è stata sbloccata ed è pronta per essere eseguita."),
	UnlockedActivityTitle("Attività sbloccata"),
	FinishedProjectTitle("Progetto completato"),
	FinishedProject("Il progetto è pronto per essere eseguito."),
	FriendShipAddedTitle("Friendship added"),
	ActivityWithoutResponsibleTitle("No responsible for activity"),
	ActivityWithoutResponsible("Each responsible for activity gives up role of responsible. You become the responsible of the activity."),
	ActivityDoneByOtherTitle("Your activity was completed"),
	ActivityDoneByOther("Another co-responsible completed your activity.");
	
	
	private String message;
	
	private DefaultMessages(String message){
		this.message = message;
	}
	
	public String toString()
	{
		return message;
	}

}
