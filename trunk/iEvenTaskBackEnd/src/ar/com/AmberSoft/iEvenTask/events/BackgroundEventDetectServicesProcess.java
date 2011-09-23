package ar.com.AmberSoft.iEvenTask.events;

import java.io.IOException;

import org.apache.commons.net.telnet.TelnetClient;
import org.apache.log4j.Logger;

import ar.com.AmberSoft.iEvenTask.backend.entities.Event;
import ar.com.AmberSoft.iEvenTask.backend.entities.EventServices;
import ar.com.AmberSoft.iEvenTask.utils.Tools;

public class BackgroundEventDetectServicesProcess extends
		BackgroundEventDetectProcess {
	
	private static Logger logger = Logger.getLogger(BackgroundEventDetectServicesProcess.class);

	public BackgroundEventDetectServicesProcess(Event event) {
		super(event);
	}

	@Override
	public Boolean eventDetect() {
		logger.debug("Iniciando deteccion de servicios");
		Boolean detected = Boolean.FALSE;
		
		TelnetClient telnet = new TelnetClient();
		
		try {
			telnet.connect(getEvent().getHost(), new Integer(getEvent().getPort()));
		} catch (Exception e) {
			// Se detecta el servicio caido
			logger.debug("Se detecta la caida del servicio: " + getEvent().getHost() + ":" + getEvent().getPort());
			detected = Boolean.TRUE;
		}
		
		if (telnet.isConnected()){
			try {
				telnet.disconnect();
			} catch (IOException e) {
				logger.error(Tools.getStackTrace(e));
			}
		}
		logger.debug("Fin deteccion de servicios");
		return detected;
	}

	
	public EventServices getEvent(){
		return (EventServices) event;
	}
}
