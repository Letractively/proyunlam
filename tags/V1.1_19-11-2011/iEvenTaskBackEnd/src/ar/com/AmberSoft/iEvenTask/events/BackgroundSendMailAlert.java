package ar.com.AmberSoft.iEvenTask.events;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import ar.com.AmberSoft.util.Email;

public class BackgroundSendMailAlert extends TimerTask{

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(BackgroundSendMailAlert.class);
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	

	
	public static void main(String []args){
		Email email = new Email();
		email.setMensaje(new StringBuffer("Hola Mundo"));
		Collection<String> destinatarios = new ArrayList<String>();
		destinatarios.add("llarreta@gmail.com");
		email.setDestinatarios(destinatarios);
		email.setAsunto("Probando");
		email.enviar();
	}
}
