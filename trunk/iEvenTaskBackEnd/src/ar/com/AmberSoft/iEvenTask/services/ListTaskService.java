package ar.com.AmberSoft.iEvenTask.services;

import java.util.Map;

import ar.com.AmberSoft.iEvenTask.backend.entities.Tarea;

public class ListTaskService extends ListService {

	@Override
	protected String getEntity() {
		return Tarea.class.getName();
	}

	@Override
	public Map onEmulate(Map params) {
		return null;
	}
}
