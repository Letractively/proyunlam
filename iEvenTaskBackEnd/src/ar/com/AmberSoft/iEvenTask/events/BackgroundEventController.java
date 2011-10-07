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
import org.hibernate.Transaction;

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
	
	private BackgroundEventFactory factory;
	
	private ResourceBundle config = ResourceBundle.getBundle("config");


	public Map<Integer, BackgroundEventDetectProcess> getActiveProcesses() {
		return activeProcesses;
	}
	
	private BackgroundEventController(){
		detectActivate = new Boolean(config.getString("event.detect"));
		if (detectActivate){
			activeProcesses = new HashMap<Integer, BackgroundEventDetectProcess>();
			periodicity = new Long(config.getString("event.controller.periodicity"));
			factory = new BackgroundEventFactory();
			timer = new Timer();
			timer.schedule(this, periodicity);

		}
	}

	private BackgroundEventController(BackgroundEventController controller){
		detectActivate = controller.detectActivate;
		if (detectActivate){
			activeProcesses = controller.activeProcesses;
			periodicity = controller.periodicity;
			factory = controller.factory;
			timer = new Timer();
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
	/**
	 * El primer run debe ser forzado por la aplicacion
	 * el resto de ejecuciones se planifica segun la periodicidad definida en configuracion
	 */
	public void run() {
		logger.debug("Ejecutando BackgroundEventController");
	
		ListEventService listEventService = new ListEventService();
		Transaction transaction = listEventService.getSession().beginTransaction();
		Map param = new HashMap();
		param.put(ParamsConst.TRANSACTION_CONTROL, Boolean.FALSE);
		Map result = listEventService.execute(param);
		
		Collection events = (Collection) result.get(ParamsConst.DATA);
		// Se lanza la deteccion para los eventos que tienen alguna relacion establecida
		// y que no se encuentren actualmente en ejecucion
		for (Iterator iterator = events.iterator(); iterator.hasNext();) {
			Event event = (Event) iterator.next();
			Set relations = event.getRelations();
			if ((activeProcesses.get(event.getId())==null) && (relations!=null) && (relations.size()>0)){
				activeProcesses.put(event.getId(), factory.getProcess(event));
			}
		}
		transaction.commit();
		new BackgroundEventController(this);
		logger.debug("Fin BackgroundEventController");
	}

	public BackgroundEventFactory getFactory() {
		return factory;
	}

}
