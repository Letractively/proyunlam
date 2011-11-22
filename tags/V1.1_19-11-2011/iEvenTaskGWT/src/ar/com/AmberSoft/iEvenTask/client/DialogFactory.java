package ar.com.AmberSoft.iEvenTask.client;

import ar.com.AmberSoft.iEvenTask.client.menu.MainTabTareas;

public class DialogFactory {

	public static void info(String message){
		new DialogInfo(message);
	}

	public static void error(String message){
		new DialogError(message);
	}
	
	public static void division(String message, MainTabTareas mainTabTareas){
		new DialogDivision(message, mainTabTareas);
	}
}
