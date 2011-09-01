package ar.com.AmberSoft.iEvenTask.services;

import ar.com.AmberSoft.iEvenTask.backend.entities.Event;

public class ListEventService extends ListService {

	@Override
	protected String getEntity() {
		return Event.class.getName();
	}

}
