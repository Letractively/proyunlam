package ar.com.AmberSoft.iEvenTask.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.hibernate.Query;

import ar.com.AmberSoft.iEvenTask.utils.FiltersWrapperFactory;
import ar.com.AmberSoft.iEvenTask.utils.Wrapper;

import com.extjs.gxt.ui.client.data.BaseFilterConfig;
import com.extjs.gxt.ui.client.data.BaseStringFilterConfig;

public abstract class Service {
	
	public static String FROM = " FROM ";
	public static String WHERE = " WHERE ";
	public static String AND = " AND ";
	public static String LIKE = " LIKE ";
	public static String QUESTION_SYMBOL = " ? ";
	public static String PORCENT = "%";
	public static String FILTERS = "filters";
	
	public static Map operatorOnWhere;
	
	public Service(){
		if (Service.operatorOnWhere==null){
			operatorOnWhere = new HashMap();
			operatorOnWhere.put(BaseStringFilterConfig.class.getName(), LIKE);
		}
	}
	
	public abstract Map execute(Map params);
	
}
