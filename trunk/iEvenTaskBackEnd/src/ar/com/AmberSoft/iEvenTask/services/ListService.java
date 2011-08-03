package ar.com.AmberSoft.iEvenTask.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import ar.com.AmberSoft.iEvenTask.hibernate.HibernateUtil;
import ar.com.AmberSoft.iEvenTask.utils.FiltersWrapperFactory;
import ar.com.AmberSoft.iEvenTask.utils.Wrapper;
import ar.com.AmberSoft.util.ParamsConst;

import com.extjs.gxt.ui.client.data.BaseFilterConfig;

public abstract class ListService extends Service {

	protected Query query;
	protected StringBuffer queryText;
	
	@Override
	public Map execute(Map params) {
		
		initList(params);
				
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		queryText = new StringBuffer();
		queryText.append(FROM);
		queryText.append(getEntity());
		
		setFiltersInQueryText(params);
		
		previousCreateQuery(params);
		
		query = session.createQuery(queryText.toString());
		
		postCreateQuery(params);
		
		setFiltersValuesInQuery(params);
		
		Map map = new HashMap();
		Collection list = new ArrayList();
		map.put(ParamsConst.LIST, query.list());
		
		session.getTransaction().commit();
		
		previousReturnMap(params, map);
		
		return map;
	}
	

	public void setFiltersValuesInQuery(Map params) {
		Collection filters = (Collection) params.get(FILTERS);
		if (filters!=null){
			int index = 0;
			Iterator it = filters.iterator();
			while (it.hasNext()) {
				BaseFilterConfig filter = (BaseFilterConfig) it.next();
				Wrapper wrapper = FiltersWrapperFactory.getInstance().getWrapper(filter);
				wrapper.setValueOnQuery(query, index);
				index++;
			}
		}
	}

	public void setFiltersInQueryText(Map params) {
		Boolean first = Boolean.TRUE;
		Collection filters = (Collection) params.get(FILTERS);
		if (filters!=null){
			Iterator it = filters.iterator();
			while (it.hasNext()) {
				if (first){
					queryText.append(WHERE);
					first = Boolean.FALSE;	
				} else {
					queryText.append(AND);
				}
				BaseFilterConfig filter = (BaseFilterConfig) it.next();
				queryText.append(filter.getField());
				queryText.append(operatorOnWhere.get(filter.getClass().getName()));
				queryText.append(QUESTION_SYMBOL);
			}
		}
	}

	/**
	 * Obligatorio redefinir para saber con que entidad se trabaja
	 * @return
	 */
	protected abstract String getEntity();
	
	/**
	 * Es posible redefinir para tomar alguna accion, antes de retornar el map como resultado del servicio
	 * @param params
	 * @param map
	 */
	protected void previousReturnMap(Map params, Map map) {}

	/**
	 * Es posible redefinir para tomar alguna accion despues de la creacion de la Query
	 * @param params
	 */
	protected void postCreateQuery(Map params) {}

	/**
	 * Es posible redefinir para tomar alguna accion antes de la creacion de la Query
	 * @param params
	 */
	protected void previousCreateQuery(Map params) {}

	/**
	 * Es posible redefinir para tomar alguna accion al comienzo de la ejecucion del servicio
	 * @param params
	 */
	protected void initList(Map params) {}


}
