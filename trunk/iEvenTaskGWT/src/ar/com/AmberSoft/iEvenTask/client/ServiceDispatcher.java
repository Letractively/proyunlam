package ar.com.AmberSoft.iEvenTask.client;

import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("serviceDispatcher")
public interface ServiceDispatcher extends RemoteService {
	Map execute(Map params) throws IllegalArgumentException;
}
