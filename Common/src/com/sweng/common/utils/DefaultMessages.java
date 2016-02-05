package com.sweng.common.utils;


public enum DefaultMessages {
	StartedActivityTitle("Attivit� iniziata"),
	StartedActivity("Bla bla"),
	StartedProjTitle("Aggiunta ad un progetto"),
	StartedProj("Sei stato aggiunto ad un progetto. Apri la notifica per maggiori dettagli."),
	UnlockedActivity("La tua attivit� � stata sbloccata ed � pronta per essere eseguita."),
	UnlockedActivityTitle("Attivit� sbloccata"),
	FinishedProjectTitle("Progetto completato."),
	FinishedProject("Il progetto � pronto per essere eseguito.");
	
	private String message;
	
	private DefaultMessages(String message){
		this.message = message;
	}
	
	public String toString()
	{
		return message;
	}

}
