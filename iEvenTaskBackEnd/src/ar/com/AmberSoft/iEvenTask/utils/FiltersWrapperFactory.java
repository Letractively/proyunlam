package ar.com.AmberSoft.iEvenTask.utils;

import java.util.HashMap;
import java.util.Map;

import com.extjs.gxt.ui.client.data.BaseFilterConfig;
import com.extjs.gxt.ui.client.data.BaseStringFilterConfig;
@SuppressWarnings({"rawtypes","unchecked"})
public class FiltersWrapperFactory {
	
	private static FiltersWrapperFactory factory;
	private Map wrappers;
	
	private FiltersWrapperFactory(){
		wrappers = new HashMap();
		wrappers.put(BaseStringFilterConfig.class.getName(), new WrapperStringFilter());
	}
	
	public static FiltersWrapperFactory getInstance(){
		if (factory==null){
			factory = new FiltersWrapperFactory();
		}
		return factory;
	}
	
	public Wrapper getWrapper(BaseFilterConfig filter){
		Wrapper wrapper = (Wrapper) wrappers.get(filter.getClass().getName());
		if (wrapper!=null){
			wrapper.setFilter(filter);
		}
		return wrapper;
	}

}
