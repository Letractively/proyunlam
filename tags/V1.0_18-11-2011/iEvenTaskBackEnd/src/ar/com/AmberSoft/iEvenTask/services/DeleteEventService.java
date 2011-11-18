package ar.com.AmberSoft.iEvenTask.services;

import ar.com.AmberSoft.iEvenTask.backend.entities.Event;

public class DeleteEventService extends DeleteService {

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntity() {
		return Event.class;
	}

}
