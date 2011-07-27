package ar.com.AmberSoft.iEvenTask.client.utils;

import java.util.HashMap;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.shared.DispatcherUtil;
import ar.com.AmberSoft.iEvenTask.shared.ServiceNameConst;

import com.extjs.gxt.ui.client.data.BaseFilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.google.gwt.user.client.rpc.AsyncCallback;
 
public class Proxy extends RpcProxy {
	
	private AsyncCallback callback;
	private Map params = new HashMap<String,String>();

	public void setCallback(AsyncCallback callback) {
		this.callback = callback;
	}

	/**
	 * Define el nombre del servicio que se invocará
	 */
	public void setService(String service){
		params.put(ServiceNameConst.SERVICIO, service);
	}

	@Override
	protected void load(Object loadConfig, AsyncCallback callbackParam) {
		
		if (loadConfig instanceof BaseFilterPagingLoadConfig) {
			BaseFilterPagingLoadConfig config = (BaseFilterPagingLoadConfig) loadConfig;
			params.putAll(config.getProperties());
		}
		
		DispatcherUtil.getDispatcher().execute(params, callback);

	}

}
