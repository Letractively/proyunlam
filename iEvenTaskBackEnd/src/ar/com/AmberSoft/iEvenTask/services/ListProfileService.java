package ar.com.AmberSoft.iEvenTask.services;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import ar.com.AmberSoft.iEvenTask.backend.entities.Perfil;
import ar.com.AmberSoft.iEvenTask.hibernate.HibernateUtil;
import ar.com.AmberSoft.util.ParamsConst;

public class ListProfileService implements Service {

	@Override
	public Map execute(Map params) {
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		StringBuffer queryText = new StringBuffer();
		queryText.append("FROM ");
		queryText.append(Perfil.class.getName());
		
		Collection filters = (Collection) params.get("filters");
		if (filters!=null){
			Iterator it = filters.iterator();
			while (it.hasNext()) {
				Object filter = (Object) it.next();
				try {
					String dataIndex = (String) PropertyUtils.getProperty(filter, "dataIndex");
					Object value = (String) PropertyUtils.getProperty(filter, "value");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		Query query = session.createQuery(queryText.toString());
		Map map = new HashMap();
		Collection list = new ArrayList();
		map.put(ParamsConst.LIST, query.list());
		
		session.getTransaction().commit();
		
		return map;
	}

}
