package ar.com.AmberSoft.iEvenTask.services;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ar.com.AmberSoft.iEvenTask.backend.entities.Perfil;
import ar.com.AmberSoft.iEvenTask.hibernate.HibernateUtil;
import ar.com.AmberSoft.util.ParamsConst;

public class DeleteProfileService implements Service {

	@Override
	public Map execute(Map params) {
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Collection ids = (Collection) params.get(ParamsConst.IDS);
		for (Iterator iterator = ids.iterator(); iterator.hasNext();) {
			Integer id = (Integer) iterator.next();
			Perfil perfil = (Perfil) session.get(Perfil.class, id);
			session.delete(perfil);
		}
		transaction.commit();
		
		return null;
	}

}
