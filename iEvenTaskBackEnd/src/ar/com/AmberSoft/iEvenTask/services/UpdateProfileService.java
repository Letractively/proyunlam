package ar.com.AmberSoft.iEvenTask.services;

import java.util.Map;

import ar.com.AmberSoft.iEvenTask.backend.entities.Entity;
import ar.com.AmberSoft.iEvenTask.backend.entities.Profile;
import ar.com.AmberSoft.util.ParamsConst;
@SuppressWarnings({"rawtypes"})
public class UpdateProfileService extends CreateProfileService {

	@Override
	public Entity getEntity(Map params) {
		Profile profile = (Profile) super.getEntity(params);
		profile.setId((Integer) params.get(ParamsConst.ID));
		return profile;
	}
	
	@Override
	public Map onExecute(Map params) {
		getSession().saveOrUpdate(getEntity(params));
		return null;
	}

	
}
