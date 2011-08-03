package ar.com.AmberSoft.iEvenTask.services;

import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ar.com.AmberSoft.iEvenTask.backend.entities.Perfil;
import ar.com.AmberSoft.iEvenTask.hibernate.HibernateUtil;
import ar.com.AmberSoft.util.ParamsConst;

public class CreateProfileService extends Service {

	@Override
	public Map execute(Map params) {
		Perfil perfil = new Perfil();
		perfil.setNombre((String) params.get(ParamsConst.NAME));
		perfil.setConexion((String) params.get(ParamsConst.CONECTION));
		perfil.setGrupoLDAP((String) params.get(ParamsConst.GROUP));
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.save(perfil);
		transaction.commit();

		return null;
	}

}
