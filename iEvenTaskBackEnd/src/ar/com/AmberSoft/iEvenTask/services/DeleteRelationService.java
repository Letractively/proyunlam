package ar.com.AmberSoft.iEvenTask.services;

import ar.com.AmberSoft.iEvenTask.backend.entities.Relation;

public class DeleteRelationService extends DeleteService {

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntity() {
		return Relation.class;
	}

}
