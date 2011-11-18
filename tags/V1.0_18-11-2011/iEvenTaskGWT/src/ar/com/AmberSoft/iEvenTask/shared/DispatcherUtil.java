package ar.com.AmberSoft.iEvenTask.shared;

import ar.com.AmberSoft.iEvenTask.client.ServiceDispatcher;
import ar.com.AmberSoft.iEvenTask.client.ServiceDispatcherAsync;

import com.google.gwt.core.client.GWT;

public class DispatcherUtil {

	/**
	 * Retorna la instancia del Dispatcher
	 * @return
	 */
	public static ServiceDispatcherAsync getDispatcher(){
		return GWT.create(ServiceDispatcher.class);
	}
	

	
}
