package ar.com.AmberSoft.iEvenTask.backend.services;

import ar.com.AmberSoft.iEvenTask.server.hibernate.HibernateUtil;

public class TestService {
	
	
	public void conectarse(){
		HibernateUtil.getSessionFactory();
	}
	
	
}
