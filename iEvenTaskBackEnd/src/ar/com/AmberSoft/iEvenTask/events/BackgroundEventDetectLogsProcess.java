package ar.com.AmberSoft.iEvenTask.events;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import ar.com.AmberSoft.iEvenTask.backend.entities.Event;
import ar.com.AmberSoft.iEvenTask.backend.entities.EventLogs;
import ar.com.AmberSoft.iEvenTask.utils.Tools;

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
		        	 // Busca la coincidencia del patron en cada linea
		        	 Pattern pattern = Pattern.compile(getEvent().getPatern());
		        	 Matcher matcher = pattern.matcher(line);
		        	 if (matcher.find()){
		        		 logger.debug("Se detecta el patron (" + getEvent().getPatern() + ") en la linea:\n" + line);
		        		 detected = Boolean.TRUE;
		        	 }
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
