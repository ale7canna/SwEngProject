package com.sweng.common.utils;


public enum DefaultMessages {
	UnlockedActivity("La tua attivit� � stata sbloccata ed � pronta per essere eseguita."),
	UnlockedActivityTitle("Attivit� sbloccata");
	
	private String message;
	
	private DefaultMessages(String message){
		this.message = message;
	}
	
	public String toString()
	{
		return message;
	}

}
