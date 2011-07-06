package ar.com.AmberSoft.iEvenTask.client;

import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>ServiceDispatcher</code>.
 */
public interface ServiceDispatcherAsync {
	void execute(Map params, AsyncCallback callback)
			throws IllegalArgumentException;
}
