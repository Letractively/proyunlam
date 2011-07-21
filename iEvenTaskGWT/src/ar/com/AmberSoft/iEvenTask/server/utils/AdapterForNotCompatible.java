package ar.com.AmberSoft.iEvenTask.server.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AdapterForNotCompatible implements Compatibilizable {

	@Override
	public Object adapt(Object actual) {
		Map map = new HashMap();
		try {
			Class type = Class.forName(actual.getClass().getName());
			Collection fields = Arrays.asList(type.getDeclaredFields());
			for (Iterator iterator = fields.iterator(); iterator.hasNext();) {
				Field field = (Field) iterator.next();
				try {
					Method method = type.getMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1) , null);
					Object fieldValue = method.invoke(actual, null);
					GWTCompatibilityEvaluatorTypes evaluatorTypes = new GWTCompatibilityEvaluatorTypes(fieldValue);
					Compatibilizable compatibilizable = evaluatorTypes.getCompatibilizableAdapter();
					fieldValue = compatibilizable.adapt(fieldValue);
					map.put(field.getName(), fieldValue);

				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return map;
	}

}
