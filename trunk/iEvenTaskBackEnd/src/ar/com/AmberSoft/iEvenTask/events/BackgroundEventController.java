package ar.com.AmberSoft.iEvenTask.events;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import ar.com.AmberSoft.iEvenTask.backend.entities.Event;
import ar.com.AmberSoft.iEvenTask.services.ListEventService;
import ar.com.AmberSoft.util.ParamsConst;

/**
 * Clase encargada de lanzar los procesos de deteccion de eventos
 * Se ejecuta adicionalmente cada un cierto tiempo definido en config.properties
 * con la finalidad de asegurar que ninguno de los procesos quede sin ejecucion
 * por alguna cuestion imprevista
 * @author Leo
 *
 */
public class BackgroundEventController extends TimerTask {
	
	private static Logger logger = Logger.getLogger(BackgroundEventController.class);
	
	private static BackgroundEventController instance;
	/**
	 * Controlador del tiempo entre ejecuciones
	 */
	private Timer timer;
	/**
	 * Indica si la deteccion de eventos se encuentra activa
	 */
	private Boolean detectActivate = Boolean.FALSE;
	
	private Long periodicity;
	
	private Map<Integer, BackgroundEventDetectProcess> activeProcesses; 
	
	
	private ResourceBundle config = ResourceBundle.getBundle("config");

	
	private BackgroundEventController(){
		detectActivate = new Boolean(config.getString("event.detect"));
		if (detectActivate){
			activeProcesses = new HashMap<Integer, BackgroundEventDetectProcess>();
			periodicity = new Long(config.getString("event.controller.periodicity"));
			timer.schedule(this, periodicity);
		}
	}
	
	public static BackgroundEventController getInstance(){
		if (instance == null){
			instance = new BackgroundEventController();
		}
		return instance;
	}

	@Override
	public void run() {
		logger.debug("Ejecutando BackgroundEventController");
	
		ListEventService listEventService = new ListEventService();
		Map result = listEventService.execute(null);
		
		Collection events = (Collection) result.get(ParamsConst.DATA);
		// Se lanza la deteccion para los eventos que tienen alguna relacion establecida
		for (Iterator iterator = events.iterator(); iterator.hasNext();) {
			Event event = (Event) iterator.next();
			Set relations = event.getRelations();
			if ((activeProcesses.get(event.getId())==null) && (relations!=null) && (relations.size()>0)){
				//FIXME: lanzar ejecucion del proceso asociado a el evento
				
			}
		}
		logger.debug("Fin BackgroundEventController");
	}

}
