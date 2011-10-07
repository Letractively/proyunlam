package ar.com.AmberSoft.iEvenTask.services;

import com.sun.jmx.snmp.tasks.Task;

public class DeleteTaskService extends DeleteService {
	
	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntity() {
		return Task.class;
	}
}
