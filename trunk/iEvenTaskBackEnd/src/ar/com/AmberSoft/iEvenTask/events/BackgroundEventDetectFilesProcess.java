package ar.com.AmberSoft.iEvenTask.events;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import ar.com.AmberSoft.iEvenTask.backend.entities.Event;
import ar.com.AmberSoft.iEvenTask.backend.entities.EventFiles;
import ar.com.AmberSoft.iEvenTask.services.UpdateEntityService;
import ar.com.AmberSoft.util.ParamsConst;

public class BackgroundEventDetectFilesProcess extends
		BackgroundEventDetectProcess {
	
	private static Logger logger = Logger.getLogger(BackgroundEventDetectFilesProcess.class);

	public BackgroundEventDetectFilesProcess(Event event) {
		super(event);
	}

	@Override
	public Boolean eventDetect() {
		logger.debug("Iniciando deteccion de archivos");
		Boolean detected = Boolean.FALSE;
		File file = new File(getEvent().getPath());
		// Se esta detectando una creacion
		if (getEvent().getControlType() == 1){
			if (file.exists()){
				logger.debug("Se detecta la creacion del archivo:" + getEvent().getPath());
				detected = Boolean.TRUE;
			}
		} else {
			// Se esta detectando una modificacion
			Date lastModified = new Date(file.lastModified());
			if ((getEvent().getLastModification()!=null) && (!getEvent().getLastModification().equals(lastModified))){
				logger.debug("Se detecta la modificacion del archivo:" + getEvent().getPath());
				detected = Boolean.TRUE;
			} 
			getEvent().setLastModification(lastModified);
			UpdateEntityService entityService = new UpdateEntityService();
			Map params = new HashMap();
			params.put(ParamsConst.ENTITY, getEvent());
			entityService.execute(params);
		}
		
		logger.debug("Fin deteccion de archivos");
		return detected;
	}
	
	public EventFiles getEvent(){
		return (EventFiles) event;
	}

}
