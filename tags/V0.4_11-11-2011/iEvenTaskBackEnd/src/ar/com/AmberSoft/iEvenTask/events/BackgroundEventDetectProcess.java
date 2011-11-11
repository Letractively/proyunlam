package ar.com.AmberSoft.iEvenTask.events;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import ar.com.AmberSoft.iEvenTask.backend.entities.Event;
import ar.com.AmberSoft.iEvenTask.backend.entities.Relation;
import ar.com.AmberSoft.iEvenTask.services.GetEventService;
import ar.com.AmberSoft.iEvenTask.services.UpdateEntityService;
import ar.com.AmberSoft.iEvenTask.utils.Tools;
import ar.com.AmberSoft.util.ParamsConst;

/**
 * Proceso encargado de la deteccion de eventos en la aplicacion
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class BackgroundEventDetectProcess extends TimerTask {

	private static Logger logger = Logger
			.getLogger(BackgroundEventDetectProcess.class);

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

	public BackgroundEventDetectProcess(Event event) {
		logger.debug("Inicializando BackgroundEventDetectProcess");
		this.event = event;
		// if (isAvaiable()){
		if ((event != null) && (event.getDelete() == null)) {
			timer = new Timer();
			timer.schedule(this, event.getPeriodicity());
		}
		logger.debug("Fin Inicializacion BackgroundEventDetectProcess");
	}

	/**
	 * Especifico para cada tipo de proceso de deteccion Sera el encargado de la
	 * deteccion en si retorna verdadero cuando se detecta la ocurrencia del
	 * evento
	 */
	public abstract Boolean eventDetect();

	@Override
	public void run() {

		try {
			Map params = new HashMap();
			params.put(ParamsConst.ID, event.getId().toString());
			params.put(ParamsConst.TRANSACTION_CONTROL, Boolean.FALSE);

			GetEventService getEventService = new GetEventService();
			Transaction transaction = getEventService.getSession()
					.beginTransaction();
			params.put(ParamsConst.TRANSACTION_CONTROL, Boolean.FALSE);
			Map result = getEventService.execute(params);
			event = (Event) result.get(ParamsConst.ENTITY);

			if ((event != null) && (isAvaiable())) {
				Set relations = event.getRelationsAvaiable();
				transaction.commit();
				if (event.getExecutions() != null) {
					executionCount = event.getExecutions();
				}
				executionCount++;
				logger.debug("Ejecucion " + executionCount.toString()
						+ " , deteccion evento: " + event.getName());

				if (eventDetect()) {
					Iterator<Relation> iRelations = relations.iterator();
					while (iRelations.hasNext()) {
						Relation relation = (Relation) iRelations.next();
						relation.execute();
					}
				}

				event.setExecutions(executionCount);
				UpdateEntityService updateEntityService = new UpdateEntityService();
				updateEntityService.execute(result);
				BackgroundEventDetectProcess process = BackgroundEventController
						.getInstance().getFactory().getProcess(event);
				process.executionCount = executionCount;

				logger.debug("Finaliza la ejecucion "
						+ executionCount.toString() + " , deteccion evento: "
						+ event.getName());
			} else {
				logger.debug("Se cancela el proceso de deteccion para el evento: "
						+ event.getName());
				transaction.rollback();
				if ((event != null) && (event.getDelete() != null)) {
					timer.cancel();
				}
				BackgroundEventController.getInstance().getActiveProcesses()
						.remove(event.getId());
			}
		} catch (Exception e) {
			logger.error(Tools.getStackTrace(e));
		}

	}

	/**
	 * Indica si esta habilitado para ejecutarse o continuar ejecutandose Si se
	 * supero la cantidad de iteraciones o se supero la fecha de expiracion se
	 * cancela el proceso para que no vuelva a ejecutarse
	 * 
	 * @return
	 */
	public Boolean isAvaiable() {
		Date actual = new Date();
		return ((event != null)
				&& (event.getDelete() == null)
				&& (event.getRelationsAvaiable().size() > 0)
				&& ((event.getIterations() == null)
						|| (event.getExecutions() == null) || (event
						.getExecutions() < event.getIterations()))
				&& ((event.getExpiration() == null) || (actual.before(event
						.getExpiration()))) && (event.getDelete() == null));
	}

}
