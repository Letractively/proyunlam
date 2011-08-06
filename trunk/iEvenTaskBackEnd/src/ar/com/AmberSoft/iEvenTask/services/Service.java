package ar.com.AmberSoft.iEvenTask.services;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;

import ar.com.AmberSoft.iEvenTask.hibernate.HibernateUtil;

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
		session = HibernateUtil.getSessionFactory().getCurrentSession();
	}
	
	public abstract Map execute(Map params);
	
}
