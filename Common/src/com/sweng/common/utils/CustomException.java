package com.sweng.common.utils;

import java.io.Serializable;
import java.util.HashMap;

public class CustomException extends Exception implements Serializable{

	private static HashMap<Errors, String> listaErrori = null;
	

	public CustomException(Errors err)
	{
		super(getMessageFromError(err));
	}
	
	private static String getMessageFromError(Errors e)
	{		
		if (listaErrori == null)
			loadErrors();		

		return listaErrori.get(e);
	}
	
	public static void loadErrors()
	{
		listaErrori = new HashMap<Errors, String>();
	
		listaErrori.put(Errors.UserNotFound, "Utente non esistente.");
		listaErrori.put(Errors.WrongPassword, "Utente esistente, ma password sbagliata.");
		listaErrori.put(Errors.ServerError, "Errore da parte del server.");
		listaErrori.put(Errors.ActivitiesNotFound, "Nessuna attivit� presente.");
		listaErrori.put(Errors.ProjectsNotFound, "Nessun progetto disponibile.");
		listaErrori.put(Errors.FriendsNotFound, "L'utente non ha nessun amico.");
		

//		listaErrori.put(Errors.MaxHumidity, "L'umidit� si trova gi� al massimo");
//		listaErrori.put(Errors.MinHumidity, "L'umidit� si trova gi� al minimo");
//		listaErrori.put(Errors.NotFound, "Pianta non trovata");
//		listaErrori.put(Errors.OutOfBorder, "Pianta non inserita. La pianta fuoriesce dalla superficie, grazia alla sua posizione o dimensione");
//		listaErrori.put(Errors.PositionBusy, "Posizione gi� occupata da un'altra pianta");
//		listaErrori.put(Errors.WaitingHalfHour, "Lo stato della serra � gi� stato modificato nell'ultima mezz'ora. Impossibile modificare nuovamente");
		
		
	}
}
