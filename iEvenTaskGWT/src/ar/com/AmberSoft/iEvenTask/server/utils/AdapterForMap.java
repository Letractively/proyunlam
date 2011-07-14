package ar.com.AmberSoft.iEvenTask.server.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AdapterForMap implements Compatibilizable {

	@Override
	public Object adapt(Object actual) {
		
		if (actual instanceof Map) {
			Map update = new HashMap();
			Map actualMap = (Map) actual;
			Iterator it = actualMap.keySet().iterator();
			while (it.hasNext()) {
				Object key = (Object) it.next();
				Object fieldValue = actualMap.get(key);
				GWTCompatibilityEvaluatorTypes evaluatorTypes = new GWTCompatibilityEvaluatorTypes(fieldValue);
				Compatibilizable compatibilizable = evaluatorTypes.getCompatibilizableAdapter();
				fieldValue = compatibilizable.adapt(fieldValue);
				update.put(key, fieldValue);
			}
			return update;
		}
		
		return null;
	}

}
