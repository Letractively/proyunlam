package ar.com.AmberSoft.iEvenTask.services;

import ar.com.AmberSoft.iEvenTask.backend.entities.Profile;

public class DeleteProfileService extends DeleteService {

	@Override
	public Class getEntity() {
		return Profile.class;
	}

}
