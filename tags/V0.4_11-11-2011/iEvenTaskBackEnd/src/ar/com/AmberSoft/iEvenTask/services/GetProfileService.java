package ar.com.AmberSoft.iEvenTask.services;

import ar.com.AmberSoft.iEvenTask.backend.entities.Profile;

public class GetProfileService extends GetEntityService {

	@Override
	public String getEntity() {
		return Profile.class.getName();
	}

}
