package ar.com.AmberSoft.iEvenTask.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
		Query query = session.createQuery("FROM " + Perfil.class.getName());

		Map map = new HashMap();
		Collection list = new ArrayList();
		map.put(ParamsConst.LIST, query.list());
		
		session.getTransaction().commit();
		
		return map;
	}

}
