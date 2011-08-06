package ar.com.AmberSoft.iEvenTask.services;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.hibernate.Transaction;

import ar.com.AmberSoft.iEvenTask.backend.entities.Perfil;
import ar.com.AmberSoft.util.ParamsConst;

public class DeleteProfileService extends Service {

	@Override
	public Map execute(Map params) {
		
		Transaction transaction = getSession().beginTransaction();
		Collection ids = (Collection) params.get(ParamsConst.IDS);
		for (Iterator iterator = ids.iterator(); iterator.hasNext();) {
			Integer id = (Integer) iterator.next();
			Perfil perfil = (Perfil) getSession().get(Perfil.class, id);
			getSession().delete(perfil);
		}
		transaction.commit();
		
		return null;
	}

}
