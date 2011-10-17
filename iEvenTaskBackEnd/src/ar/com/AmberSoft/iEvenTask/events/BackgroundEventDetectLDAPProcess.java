package ar.com.AmberSoft.iEvenTask.events;

import org.apache.log4j.Logger;

import ar.com.AmberSoft.iEvenTask.backend.entities.Event;

public class BackgroundEventDetectLDAPProcess extends
		BackgroundEventDetectProcess {
	
	private static Logger logger = Logger.getLogger(BackgroundEventDetectLDAPProcess.class);

	public BackgroundEventDetectLDAPProcess(Event event) {
		super(event);
	}

	@Override
	public Boolean eventDetect() {
		logger.debug("Iniciando deteccion de evento LDAP");
		
		
		
		logger.debug("Fin deteccion de evento LDAP");
		return null;
	}

}
