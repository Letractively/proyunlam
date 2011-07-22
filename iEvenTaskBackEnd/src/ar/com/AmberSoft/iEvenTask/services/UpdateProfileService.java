package ar.com.AmberSoft.iEvenTask.services;

import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ar.com.AmberSoft.iEvenTask.backend.entities.Perfil;
import ar.com.AmberSoft.iEvenTask.hibernate.HibernateUtil;
import ar.com.AmberSoft.util.ParamsConst;

public class UpdateProfileService implements Service {

	@Override
	public Map execute(Map params) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Perfil perfil = new Perfil();
		perfil.setId((Integer) params.get(ParamsConst.ID));
		perfil = (Perfil) session.get(Perfil.class, perfil.getId());
		perfil.setNombre((String) params.get(ParamsConst.NAME));
		perfil.setConexion((String) params.get(ParamsConst.CONECTION));
		perfil.setGrupoLDAP((String) params.get(ParamsConst.GROUP));
		session.saveOrUpdate(perfil);
		transaction.commit();
		
		return null;
	}

}
