package ar.com.AmberSoft.iEvenTask.client;

public class DialogFactory {

	public static void info(String message){
		new DialogInfo(message);
	}

	public static void error(String message){
		new DialogError(message);
	}
}
