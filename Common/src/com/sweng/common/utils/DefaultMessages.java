package com.sweng.common.utils;


public enum DefaultMessages {
	StartedActivityTitle("Attivit� iniziata"),
	StartedActivity("Bla bla"),
	StartedProjTitle("Aggiunta ad un progetto"),
	StartedProj("Sei stato aggiunto ad un progetto. Apri la notifica per maggiori dettagli."),
	UnlockedActivity("La tua attivit� � stata sbloccata ed � pronta per essere eseguita."),
	UnlockedActivityTitle("Attivit� sbloccata"),
	FinishedProjectTitle("Progetto completato."),
	FinishedProject("Il progetto � pronto per essere eseguito."),
	FriendShipAddedTitle("Friendship added"),
	ActivityWithoutResponsibleTitle("No responsible for activity"),
	ActivityWithoutResponsible("Each responsible for activity gives up the role of responsible.");
	
	private String message;
	
	private DefaultMessages(String message){
		this.message = message;
	}
	
	public String toString()
	{
		return message;
	}

}
