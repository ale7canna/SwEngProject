package com.sweng.common.utils;


public enum DefaultMessages {
	UnlockedActivity("La tua attività è stata sbloccata ed è pronta per essere eseguita."),
	UnlockedActivityTitle("Attività sbloccata");
	
	private String message;
	
	private DefaultMessages(String message){
		this.message = message;
	}
	
	public String toString()
	{
		return message;
	}

}
