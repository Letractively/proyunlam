package ar.com.AmberSoft.iEvenTask.events;

import org.apache.log4j.Logger;

import ar.com.AmberSoft.iEvenTask.backend.entities.Event;
import ar.com.AmberSoft.util.LDAPClient;

public class BackgroundEventDetectLDAPProcess extends
		BackgroundEventDetectProcess {
	
	private static Logger logger = Logger.getLogger(BackgroundEventDetectLDAPProcess.class);

	public BackgroundEventDetectLDAPProcess(Event event) {
		super(event);
	}

	@Override
	public Boolean eventDetect() {
		logger.debug("Iniciando deteccion de evento LDAP");
		
		LDAPClient.search("Leonel", "Amber2011");
		
		logger.debug("Fin deteccion de evento LDAP");
		return null;
	}

}
