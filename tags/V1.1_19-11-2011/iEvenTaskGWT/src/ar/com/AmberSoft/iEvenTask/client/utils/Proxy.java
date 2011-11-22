package ar.com.AmberSoft.iEvenTask.client.utils;

import java.util.HashMap;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.shared.DispatcherUtil;
import ar.com.AmberSoft.iEvenTask.shared.ServiceNameConst;

import com.extjs.gxt.ui.client.data.BaseFilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.google.gwt.user.client.rpc.AsyncCallback;

@SuppressWarnings({"rawtypes", "unchecked"})
public class Proxy extends RpcProxy {

	private GridDataCallback callback;
	private Map params = new HashMap<String, String>();

	public Map getParams() {
		return params;
	}

	public Proxy(String service, Grid grid, Map params){
		callback = new GridDataCallback(grid);
		this.params.putAll(params);
		this.params.put(ServiceNameConst.SERVICIO, service);
	}
	
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

	public void setCallback(GridDataCallback callback) {
		this.callback = callback;
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
