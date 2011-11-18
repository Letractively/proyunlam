package ar.com.AmberSoft.iEvenTask.services;

import ar.com.AmberSoft.iEvenTask.backend.entities.Objetivo;

public class GetObjectiveService extends GetEntityService {

	@Override
	public String getEntity() {
		return Objetivo.class.getName();
	}

}
