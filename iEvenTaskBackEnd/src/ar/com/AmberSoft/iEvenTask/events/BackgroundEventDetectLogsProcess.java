package ar.com.AmberSoft.iEvenTask.events;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import ar.com.AmberSoft.iEvenTask.backend.entities.Event;
import ar.com.AmberSoft.iEvenTask.backend.entities.EventLogs;
import ar.com.AmberSoft.iEvenTask.services.UpdateEntityService;
import ar.com.AmberSoft.iEvenTask.utils.Tools;
import ar.com.AmberSoft.util.ParamsConst;

public class BackgroundEventDetectLogsProcess extends
		BackgroundEventDetectProcess {
	
	private static Logger logger = Logger.getLogger(BackgroundEventDetectLogsProcess.class);

	public BackgroundEventDetectLogsProcess(Event event) {
		super(event);
	}

	@Override
	public Boolean eventDetect() {
		logger.debug("Iniciando deteccion de logs");
		Boolean detected = Boolean.FALSE;
		File file = new File(getEvent().getPath());
		
		try {
			if ((file.exists()) && (file.canRead())){
				FileReader fileReader = new FileReader (file);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
	
		         // Lectura del fichero
		         String line;
		         while((line=bufferedReader.readLine())!=null) {
		        	 //FIXME: Evaluar que conincida con el patron buscado
		            System.out.println(line);
		            
		         }
			}
		} catch(Exception e){
			logger.error(Tools.getStackTrace(e));
		}
		
		logger.debug("Fin deteccion de logs");
		return detected;
	}
	
	public EventLogs getEvent(){
		return (EventLogs) event;
	}

}
