package ar.com.AmberSoft.iEvenTask.client;

import java.util.Map;

public abstract class EventWindowOption {
	
	public static final String LDAP = "ar.com.AmberSoft.iEvenTask.backend.entities.EventLDAP";
	public static final String LOGS = "ar.com.AmberSoft.iEvenTask.backend.entities.EventLogs";
	public static final String FILES = "ar.com.AmberSoft.iEvenTask.backend.entities.EventFiles";
	public static final String SERVICES = "ar.com.AmberSoft.iEvenTask.backend.entities.EventServices";

	protected EventWindow eventWindow;
	
	public EventWindow getEventWindow() {
		return eventWindow;
	}

	public void setEventWindow(EventWindow eventWindow) {
		this.eventWindow = eventWindow;
	}

	public abstract void onSave(Map params);
	
	public abstract void setVisiblePanel();
	
	public abstract void beforeUpdate(Map actual);
	
	public abstract Boolean isValid(); 
	

	
	
	
}
