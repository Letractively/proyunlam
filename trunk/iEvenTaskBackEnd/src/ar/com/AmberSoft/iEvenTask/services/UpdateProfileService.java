package ar.com.AmberSoft.iEvenTask.services;

import java.util.Map;

import org.hibernate.Transaction;

import ar.com.AmberSoft.iEvenTask.backend.entities.Profile;
import ar.com.AmberSoft.util.ParamsConst;

public class UpdateProfileService extends CreateProfileService {

	@Override
	public Map execute(Map params) {
		
		Profile perfil = new Profile();
		perfil.setId((Integer) params.get(ParamsConst.ID));

		setAttributes(params, perfil);
		
		Transaction transaction = getSession().beginTransaction();
		getSession().saveOrUpdate(perfil);
		transaction.commit();
		
		return null;
	}
	

}
