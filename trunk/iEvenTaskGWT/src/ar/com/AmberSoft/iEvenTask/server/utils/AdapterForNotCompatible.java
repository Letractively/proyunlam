package ar.com.AmberSoft.iEvenTask.server.utils;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

public class AdapterForNotCompatible implements Compatibilizable {

	private static Logger logger = Logger
			.getLogger(AdapterForNotCompatible.class);

	@Override
	public Object adapt(Object actual) {
		Map map = new HashMap();
		try {
			Collection properties = Arrays.asList(PropertyUtils
					.getPropertyDescriptors(actual));
			Iterator itProperties = properties.iterator();
			while (itProperties.hasNext()) {
				PropertyDescriptor descriptor = (PropertyDescriptor) itProperties
						.next();
				if ((PropertyUtils.isWriteable(actual,
						descriptor.getDisplayName()))
						&& (PropertyUtils.isReadable(actual,
								descriptor.getDisplayName()))) {
					Object propertyValue = PropertyUtils.getProperty(actual, descriptor.getName());
					GWTCompatibilityEvaluatorTypes evaluatorTypes = new GWTCompatibilityEvaluatorTypes(propertyValue);
					Compatibilizable compatibilizable = evaluatorTypes.getCompatibilizableAdapter();
					if (propertyValue!=null){
						propertyValue = compatibilizable.adapt(propertyValue);
					}
					map.put(descriptor.getName(), propertyValue);
				}
			}

		} catch (Exception e) {
			logger.error(Tools.getStackTrace(e));
		}

		return map;
	}

}
