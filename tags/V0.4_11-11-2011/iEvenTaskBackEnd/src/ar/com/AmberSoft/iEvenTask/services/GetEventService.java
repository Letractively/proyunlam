package ar.com.AmberSoft.iEvenTask.services;

import ar.com.AmberSoft.iEvenTask.backend.entities.Event;

public class GetEventService extends GetEntityService {

	@Override
	public String getEntity() {
		return Event.class.getName();
	}

}
