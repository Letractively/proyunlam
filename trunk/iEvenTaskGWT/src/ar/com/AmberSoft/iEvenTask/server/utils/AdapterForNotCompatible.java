package ar.com.AmberSoft.iEvenTask.server.utils;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

public class AdapterForNotCompatible implements Compatibilizable {

	@Override
	public Object adapt(Object actual) {
		
		Map map = new HashMap();
		Collection properties = Arrays.asList(PropertyUtils.getPropertyDescriptors(actual));
		Iterator itProperties = properties.iterator();
		while (itProperties.hasNext()) {
			PropertyDescriptor descriptor = (PropertyDescriptor) itProperties.next();
			try {
				if ((PropertyUtils.isWriteable(actual, descriptor.getDisplayName())) && 
					(PropertyUtils.isReadable(actual, descriptor.getDisplayName()))){
					Object propertyValue = PropertyUtils.getProperty(actual, descriptor.getName());
					GWTCompatibilityEvaluatorTypes evaluatorTypes = new GWTCompatibilityEvaluatorTypes(propertyValue);
					Compatibilizable compatibilizable = evaluatorTypes.getCompatibilizableAdapter();
					propertyValue = compatibilizable.adapt(propertyValue);
					map.put(descriptor.getName(), propertyValue);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return map;
	}

}
