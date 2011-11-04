package ar.com.AmberSoft.iEvenTask.client;

import java.util.HashMap;
import java.util.Map;

public class EventWindowOptionFactory {
	
	private static EventWindowOptionFactory instance;
	
	private Map<String, EventWindowOption> eventWindowOptions;
	
	private EventWindowOptionFactory(){
		eventWindowOptions = new HashMap<String, EventWindowOption>();
		eventWindowOptions.put(EventWindowOption.LDAP, new EventWindowOptionLDAP());
		eventWindowOptions.put(EventWindowOption.LOGS, new EventWindowOptionLogs());
		eventWindowOptions.put(EventWindowOption.FILES, new EventWindowOptionFiles());
		eventWindowOptions.put(EventWindowOption.SERVICES, new EventWindowOptionServices());
	}
	
	public static EventWindowOptionFactory getInstance(){
		if (instance==null){
			instance = new EventWindowOptionFactory();
		}
		return instance;
	}
	
	public EventWindowOption getEventWindowOption(String option, EventWindow eventWindow){
		EventWindowOption windowOption = eventWindowOptions.get(option);
		if (windowOption!=null){
			windowOption.setEventWindow(eventWindow);
		}
		return windowOption;
	}
	
	
	
	
}
