package ar.com.AmberSoft.iEvenTask.services;

import ar.com.AmberSoft.iEvenTask.backend.entities.Objetivo;

public class DeleteObjectiveService extends DeleteService {

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntity() {
		return Objetivo.class;
	}

}
