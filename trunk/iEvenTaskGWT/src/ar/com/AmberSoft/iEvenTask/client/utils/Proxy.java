package ar.com.AmberSoft.iEvenTask.client.utils;

import java.util.HashMap;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.shared.DispatcherUtil;
import ar.com.AmberSoft.iEvenTask.shared.ServiceNameConst;

import com.extjs.gxt.ui.client.data.BaseFilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class Proxy extends RpcProxy {

	private final GridDataCallback callback;
	private Map params = new HashMap<String, String>();

	public Proxy(String service, Grid grid){
		callback = new GridDataCallback(grid);
		params.put(ServiceNameConst.SERVICIO, service);
	}
	
	public void setGrid(Grid grid) {
		callback.setGrid(grid);
	}

	public GridDataCallback getCallback() {
		return callback;
	}

	
	@Override
	protected void load(Object loadConfig, AsyncCallback proxyCallback) {
		if (loadConfig instanceof BaseFilterPagingLoadConfig) {
			BaseFilterPagingLoadConfig config = (BaseFilterPagingLoadConfig) loadConfig;
			params.putAll(config.getProperties());
		}
		callback.setProxyCallback(proxyCallback);
		DispatcherUtil.getDispatcher().execute(params, callback);
	}
}
