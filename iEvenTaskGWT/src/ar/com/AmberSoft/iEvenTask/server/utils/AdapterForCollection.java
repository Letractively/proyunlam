package ar.com.AmberSoft.iEvenTask.server.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class AdapterForCollection implements Compatibilizable {

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public Object adapt(Object actual) {

		if (actual instanceof Collection) {
			Collection update = new ArrayList();
			Collection actualMap = (Collection) actual;
			try {
				Iterator it = actualMap.iterator();
				while (it.hasNext()) {
					Object fieldValue = (Object) it.next();
					GWTCompatibilityEvaluatorTypes evaluatorTypes = new GWTCompatibilityEvaluatorTypes(
							fieldValue);
					Compatibilizable compatibilizable = evaluatorTypes
							.getCompatibilizableAdapter();
					fieldValue = compatibilizable.adapt(fieldValue);
					update.add(fieldValue);
				}
			} catch (Exception e){
				
			}
			return update;
		}

		return null;
	}

}
