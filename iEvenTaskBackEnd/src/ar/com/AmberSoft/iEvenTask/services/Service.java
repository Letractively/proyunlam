package ar.com.AmberSoft.iEvenTask.services;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import ar.com.AmberSoft.iEvenTask.hibernate.HibernateUtil;
import ar.com.AmberSoft.iEvenTask.utils.AppAdmin;
import ar.com.AmberSoft.iEvenTask.utils.Tools;
import ar.com.AmberSoft.util.ParamsConst;

import com.extjs.gxt.ui.client.data.BaseStringFilterConfig;
import com.extjs.gxt.ui.client.widget.grid.filters.NumericFilter;

public abstract class Service {

	private static Logger logger = Logger.getLogger(Service.class);

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
	public static String IS_NULL = " IS NULL ";
	public static String EQUAL = " = ";

	public static Map operatorOnWhere;

	private Session session;

	public Session getSession() {
		return session;
	}

	public Service() {
		if (Service.operatorOnWhere == null) {
			operatorOnWhere = new HashMap();
			operatorOnWhere.put(BaseStringFilterConfig.class.getName(), LIKE);
			operatorOnWhere.put(NumericFilter.class.getName(), EQUAL);
		}
		// Si no se esta emulando obtenemos una session de hibernate
		if (!AppAdmin.getInstance().getConfig().isEmulate()) {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
		}

	}

	public Map execute(Map params) throws Exception {
		logger.info("Ejecutando " + this.getClass().getName());

		if (AppAdmin.getInstance().getConfig().isEmulate()) {
			return onEmulate(params);
		}

		Transaction transaction = null;
		if (isTransactionControl(params)) {
			transaction = getSession().beginTransaction();
		}
		Map result = null;
		try {
			result = onExecute(params);

			if (isTransactionControl(params)) {
				try {
					if (transaction.isActive()) {
						transaction.commit();
					}
				} catch (ConstraintViolationException e) {
					logger.error(Tools.getStackTrace(e));
					
					if ((transaction != null) && (transaction.isActive())) {
						transaction.rollback();
					}
					
					Map map = new HashMap();
					map.put(ParamsConst.ERROR, "ConstraintViolationException");
					
					return map;
				} catch (Throwable e) {
					
					logger.error(Tools.getStackTrace(e));
					
					if ((transaction != null) && (transaction.isActive())) {
						transaction.rollback();
					}
				} 
			}
		} catch (Exception e) {
			logger.error(Tools.getStackTrace(e));
			if ((transaction != null) && (transaction.isActive())) {
				transaction.rollback();
			}
			throw e;
		}

		logger.info("Fin ejecucion " + this.getClass().getName());
		return result;
	}

	/**
	 * Define si el mismo servicio maneja el control transaccional del acceso a
	 * BD Por defecto lo maneja el mismo servicio
	 * 
	 * @param params
	 * @return
	 */
	private Boolean isTransactionControl(Map params) {
		Boolean transactionControl = (Boolean) params
				.get(ParamsConst.TRANSACTION_CONTROL);
		if (transactionControl == null) {
			transactionControl = Boolean.TRUE;
		}
		return transactionControl;
	}

	/**
	 * Ejecuta el servicio real
	 * 
	 * @param params
	 * @return
	 */
	public abstract Map onExecute(Map params)  throws Exception ;

	/**
	 * Emula la ejecucion del servicio Solo se ejecuta con la emulacion activada
	 * 
	 * @param params
	 * @return
	 */
	public abstract Map onEmulate(Map params);

	/**
	 * Transforma un string en un integer
	 * 
	 * @param value
	 * @return
	 */
	public static Integer stringToInteger(String value) {
		if ((value != null) && (!"".equals(value))) {
			return new Integer(value);
		}
		return null;
	}


}
