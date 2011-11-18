package ar.com.AmberSoft.iEvenTask.events;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import ar.com.AmberSoft.iEvenTask.backend.entities.User;
import ar.com.AmberSoft.iEvenTask.services.EntitiesManager;

public class BackgroundUserUnlockController extends TimerTask {

	private static Logger logger = Logger.getLogger(BackgroundUserUnlockController.class);
	
	private ResourceBundle config = ResourceBundle.getBundle("config");
	
	private Long periodicity = new Long(config.getString("max.inactive.interval"));;
	
	public static Integer SEG_X_MIN = 60;
	public static Integer MILSEG_X_SEG = 1000;
	
	/**
	 * Controlador del tiempo entre ejecuciones
	 */
	private Timer timer;
	
	private static BackgroundUserUnlockController instance;
	
	public static BackgroundUserUnlockController getInstance(){
		if (instance == null){
			instance = new BackgroundUserUnlockController();
		}
		return instance;
	}
	
	private BackgroundUserUnlockController (){
		timer = new Timer();
		timer.schedule(this, periodicity * SEG_X_MIN * MILSEG_X_SEG);
	}
	
	@Override
	public void run() {

		logger.debug("Ejecutando controlador de bloqueos");
		
		Date actual = new Date();
		
		Map entities = EntitiesManager.getInstance().getEntities();
		if (entities!=null){
			Set keys = entities.keySet();
			Iterator it = keys.iterator();
			while (it.hasNext()) {
				Object key = (Object) it.next();
				User user = (User) entities.get(key);
				if ((user!=null) && (user.getLockTime()!=null)){
					Long diferencia = actual.getTime() - user.getLockTime().getTime();
					Long minutosTranscurridos = diferencia / SEG_X_MIN / MILSEG_X_SEG;
					if (minutosTranscurridos>periodicity){
						logger.debug("Desactivando bloqueos para el usuario " + user.getName() + " por inactividad.");
						EntitiesManager.getInstance().unlockAll(user);
					}
				}
			}
		}
		
		logger.debug("Fin controlador de bloqueos");
	}

}
