package ar.com.AmberSoft.iEvenTask.backend.services;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ar.com.AmberSoft.iEvenTask.backend.entities.Operation;
import ar.com.AmberSoft.iEvenTask.server.hibernate.HibernateUtil;

public class TestService {
	
	
	public void conectarse(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.save(new Operation());
		transaction.commit();
	}
	
	
}
