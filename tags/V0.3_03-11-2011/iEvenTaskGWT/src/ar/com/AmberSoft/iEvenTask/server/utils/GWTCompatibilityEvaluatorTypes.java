package ar.com.AmberSoft.iEvenTask.server.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@SuppressWarnings({"rawtypes" ,"unchecked"})
public class GWTCompatibilityEvaluatorTypes {

	private static Collection typesGroups;
	
	private static Collection compatibleTypes1;
	private static Collection compatibleTypes2;
	private static Collection compatibleTypes3;
	private static Collection compatibleTypes4;
	
	private static Map adapters;
	
	private Compatibilizable compatibilizableAdapter;

	public Compatibilizable getCompatibilizableAdapter() {
		return compatibilizableAdapter;
	}

	public GWTCompatibilityEvaluatorTypes(Object actual){
		initialize();
		Iterator it = typesGroups.iterator();
		compatibilizableAdapter = compatibiltyEvaluate(actual, (Collection)it.next(), it);
	}

	private Compatibilizable compatibiltyEvaluate(Object actual, Collection types, Iterator groups) {
		Iterator it = types.iterator();
		while (it.hasNext()) {
			Class gwtType = (Class) it.next();
			if (gwtType.isInstance(actual)) {
				return (Compatibilizable) adapters.get(types);
			}
		}
		if (groups.hasNext()){
			return compatibiltyEvaluate(actual, (Collection)groups.next(), groups);
		}
		return new AdapterForNotCompatible();
	}

	/**
	 * Inicializa la coleccion de tipos compatibles con GWT
	 */
	private void initialize() {
		if (typesGroups==null){
			compatibleTypes1 = new ArrayList();
			compatibleTypes2 = new ArrayList();
			compatibleTypes3 = new ArrayList();
			compatibleTypes4 = new ArrayList();
			adapters = new HashMap();
			typesGroups = new ArrayList();	

			compatibleTypes1.add(Integer.class);
			compatibleTypes1.add(String.class);
			compatibleTypes1.add(Long.class);
			compatibleTypes1.add(Boolean.class);
			compatibleTypes1.add(Double.class);
			compatibleTypes1.add(Date.class);
			compatibleTypes2.add(Collection.class);
			compatibleTypes3.add(Map.class);
			
			adapters.put(compatibleTypes1, new AdapterForCompatibleType());
			adapters.put(compatibleTypes2, new AdapterForCollection());
			adapters.put(compatibleTypes3, new AdapterForMap());
			
			typesGroups.add(compatibleTypes1);
			typesGroups.add(compatibleTypes2);
			typesGroups.add(compatibleTypes3);
		}
	}
	
	
}
