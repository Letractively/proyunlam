package ar.com.AmberSoft.iEvenTask.events;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import ar.com.AmberSoft.iEvenTask.backend.entities.Event;
import ar.com.AmberSoft.iEvenTask.backend.entities.Relation;
import ar.com.AmberSoft.iEvenTask.services.GetEventService;
import ar.com.AmberSoft.iEvenTask.services.UpdateEntityService;
import ar.com.AmberSoft.util.ParamsConst;

/**
 * Proceso encargado de la deteccion de eventos en la aplicacion 
 */
public abstract class BackgroundEventDetectProcess extends TimerTask {

	private static Logger logger = Logger.getLogger(BackgroundEventDetectProcess.class);
	
	/**
	 * Evento que intenta detectar
	 */
	protected Event event;
	
	/**
	 * Controlador del tiempo entre ejecuciones
	 */
	private Timer timer = new Timer();
	
	/**
	 * Contador de ejecuciones
	 */
	private Integer executionCount = 0;
	
	public BackgroundEventDetectProcess(Event event){
		logger.debug("Inicializando BackgroundEventDetectProcess");
		this.event = event;
		if (isAvaiable()){
			timer.schedule(this, event.getPeriodicity());
		}
		logger.debug("Fin Inicializacion BackgroundEventDetectProcess");
	}
	
	/**
	 * Especifico para cada tipo de proceso de deteccion
	 * Sera el encargado de la deteccion en si
	 * retorna verdadero cuando se detecta la ocurrencia del evento
	 */
	public abstract Boolean eventDetect();
	
	@Override
	public void run() {
		
		Map params = new HashMap();
		params.put(ParamsConst.ID, event.getId().toString());
		GetEventService getEventService = new GetEventService();
		Map result = getEventService.execute(params);
		Event realEvent = (Event) result.get(ParamsConst.ENTITY); 
		if(realEvent.getExecutions()!=null){
			executionCount = realEvent.getExecutions(); 
		}
		if ((realEvent!=null) && (isAvaiable())){
			executionCount++;
			logger.debug("Ejecucion " + executionCount.toString() + " , deteccion evento: " + event.getName());
			
			if (eventDetect()){
				Iterator<Relation> relations = event.getRelations().iterator();
				while (relations.hasNext()) {
					Relation relation = (Relation) relations.next();
					//FIXME:Tomar la accion correspondiente para cada relacion ante la deteccion de cada evento
					
				}
			}

			realEvent.setExecutions(executionCount);
			UpdateEntityService updateEntityService = new UpdateEntityService();
			updateEntityService.execute(result);
			BackgroundEventDetectProcess process = BackgroundEventController.getInstance().getFactory().getProcess(event);
			process.executionCount = executionCount;
			
			logger.debug("Finaliza la ejecucion " + executionCount.toString() + " , deteccion evento: " + event.getName());
		} else {
			logger.debug("Se cancela el proceso de deteccion para el evento: " + event.getName());
			timer.cancel();
		}
		
	}
	
	/**
	 * Indica si esta habilitado para ejecutarse o continuar ejecutandose
	 * Si se supero la cantidad de iteraciones o se supero la fecha de expiracion 
	 * se cancela el proceso para que no vuelva a ejecutarse
	 * @return
	 */
	public Boolean isAvaiable(){
		Date actual = new Date();
		return (((event.getIterations()==null) || (executionCount <= event.getIterations()))
				&& ((event.getExpiration()==null) || (actual.after(event.getExpiration()))));
	}
	
}
