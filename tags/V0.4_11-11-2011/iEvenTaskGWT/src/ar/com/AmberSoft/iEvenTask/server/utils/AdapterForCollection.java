package ar.com.AmberSoft.iEvenTask.server.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;

import ar.com.AmberSoft.iEvenTask.services.ListTaskService;

public class AdapterForCollection implements Compatibilizable {
	
	private static Logger logger = Logger.getLogger(AdapterForCollection.class);

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public Object adapt(Object actual) {

		if (actual!=null){
			logger.info("actual=" + actual.getClass().getName());
		}
		
		if (actual instanceof Collection) {
			Collection update = new ArrayList();
			Collection actualMap = (Collection) actual;
			try {
				Iterator it = actualMap.iterator();
				while (it.hasNext()) {
					Object fieldValue = (Object) it.next();
					logger.info("fieldValue=" + fieldValue.getClass().getName());
					GWTCompatibilityEvaluatorTypes evaluatorTypes = new GWTCompatibilityEvaluatorTypes(fieldValue);
					Compatibilizable compatibilizable = evaluatorTypes.getCompatibilizableAdapter();
					fieldValue = compatibilizable.adapt(fieldValue);
					update.add(fieldValue);
				}
			} catch (Exception e){
				logger.error(Tools.getStackTrace(e));
			}
			return update;
		}

		return null;
	}

}
