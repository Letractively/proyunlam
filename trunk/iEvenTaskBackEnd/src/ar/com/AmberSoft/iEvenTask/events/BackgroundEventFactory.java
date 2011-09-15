package ar.com.AmberSoft.iEvenTask.events;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import ar.com.AmberSoft.iEvenTask.backend.entities.Event;
import ar.com.AmberSoft.iEvenTask.backend.entities.EventFiles;
import ar.com.AmberSoft.iEvenTask.backend.entities.EventLDAP;
import ar.com.AmberSoft.iEvenTask.backend.entities.EventLogs;
import ar.com.AmberSoft.iEvenTask.backend.entities.EventServices;
import ar.com.AmberSoft.iEvenTask.utils.Tools;

public class BackgroundEventFactory {
	private static Logger logger = Logger.getLogger(BackgroundEventFactory.class);
	
	/**
	 * Representa la relacion entre los eventos y los procesos que se lanzarán segun su tipo
	 */
	public Map<Class, Class> processesNameByEventType;
	
	public BackgroundEventFactory(){
		processesNameByEventType = new HashMap<Class, Class>();
		processesNameByEventType.put(EventFiles.class, BackgroundEventDetectFilesProcess.class);
		processesNameByEventType.put(EventServices.class, BackgroundEventDetectServicesProcess.class);
		processesNameByEventType.put(EventLDAP.class, BackgroundEventDetectLDAPProcess.class);
		processesNameByEventType.put(EventLogs.class, BackgroundEventDetectLogsProcess.class);
	}
	
	/**
	 * Retorna la instancia del proceso que deberia lanzarse segun el evento pasado por parametro
	 * @param event
	 * @return
	 */
	public BackgroundEventDetectProcess getProcess(Event event){
		Class processClass = processesNameByEventType.get(event.getClass());
		try {
			Constructor constructor = processClass.getConstructor(Event.class);
			return (BackgroundEventDetectProcess) constructor.newInstance(event);
		} catch (Exception e) {
			logger.error(Tools.getStackTrace(e));
		}
		return null;
	}

}
