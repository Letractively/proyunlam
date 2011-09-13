package ar.com.AmberSoft.iEvenTask.events;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import ar.com.AmberSoft.iEvenTask.backend.entities.Event;

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
	private Timer timer;
	
	/**
	 * Contador de ejecuciones
	 */
	private Integer executionCount = 0;
	
	public BackgroundEventDetectProcess(Event event){
		logger.debug("Inicializando BackgroundEventDetectProcess");
		if (isAvaiable()){
			this.event = event;
			timer.schedule(this, event.getPeriodicity());
		}
		logger.debug("Fin Inicializacion BackgroundEventDetectProcess");
	}
	
	/**
	 * Especifico para cada tipo de proceso de deteccion
	 * Sera el encargado de la deteccion en si
	 */
	public abstract void eventDetect();
	
	@Override
	public void run() {
		executionCount++;
		logger.debug("Ejecucion " + executionCount.toString() + " , deteccion evento: " + event.getName());
		
		eventDetect();
		
		if (isAvaiable()){
			timer.cancel();
		}
		logger.debug("Finaliza la ejecucion " + executionCount.toString() + " , deteccion evento: " + event.getName());
	}

	/**
	 * Indica si esta habilitado para ejecutarse o continuar ejecutandose
	 * Si se supero la cantidad de iteraciones o se supero la fecha de expiracion 
	 * se cancela el proceso para que no vuelva a ejecutarse
	 * @return
	 */
	public Boolean isAvaiable(){
		Date actual = new Date();
		return (((event.getIterations()==null) || (executionCount < event.getIterations()))
				&& ((event.getExpiration()==null) || (actual.after(event.getExpiration()))));
	}
	
}
