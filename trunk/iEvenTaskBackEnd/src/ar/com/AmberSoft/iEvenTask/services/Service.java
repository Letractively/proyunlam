package ar.com.AmberSoft.iEvenTask.services;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;

import ar.com.AmberSoft.iEvenTask.hibernate.HibernateUtil;
import ar.com.AmberSoft.iEvenTask.utils.AppAdmin;

import com.extjs.gxt.ui.client.data.BaseStringFilterConfig;

public abstract class Service {
	
	public static String FROM = " FROM ";
	public static String WHERE = " WHERE ";
	public static String AND = " AND ";
	public static String LIKE = " LIKE ";
	public static String QUESTION_SYMBOL = " ? ";
	public static String PORCENT = "%";
	public static String FILTERS = "filters";
	public static String ORDER_BY = " ORDER BY ";
	public static String SORT_FIELD = "sortField";
	public static String SORT_DIR = "sortDir";
	public static String SPACE = " ";

	
	public static Map operatorOnWhere;
	
	private Session session;
	
	public Session getSession() {
		return session;
	}

	public Service(){
		if (Service.operatorOnWhere==null){
			operatorOnWhere = new HashMap();
			operatorOnWhere.put(BaseStringFilterConfig.class.getName(), LIKE);
		}
		// Si no se esta emulando obtenemos una session de hibernate
		if (!AppAdmin.getInstance().getConfig().isEmulate()){
			session = HibernateUtil.getSessionFactory().getCurrentSession();
		}	
		
	}
	
	public Map execute(Map params){
		if (AppAdmin.getInstance().getConfig().isEmulate()){
			return onEmulate(params);
		}
		return onExecute(params);
	}
	
	/**
	 * Ejecuta el servicio real
	 * @param params
	 * @return
	 */
	public abstract Map onExecute(Map params);
	
	/**
	 * Emula la ejecucion del servicio
	 * Solo se ejecuta con la emulacion activada
	 * @param params
	 * @return
	 */
	public abstract Map onEmulate(Map params);
	
	/**
	 * Transforma un string en un integer
	 * @param value
	 * @return
	 */
	public static Integer stringToInteger(String value){
		if ((value!=null) && (!"".equals(value))){
			return new Integer(value);
		}
		return null;
	}
	
}
