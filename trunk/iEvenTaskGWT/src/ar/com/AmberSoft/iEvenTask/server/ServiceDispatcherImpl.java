package ar.com.AmberSoft.iEvenTask.server;

import java.util.Map;

import ar.com.AmberSoft.iEvenTask.client.ServiceDispatcher;
import ar.com.AmberSoft.iEvenTask.server.services.Service;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ServiceDispatcherImpl extends RemoteServiceServlet implements
		ServiceDispatcher {

	public Map execute(Map params) throws IllegalArgumentException {
		
		Service service = Service.getService(params);
		return service.execute(params);

	}


}
