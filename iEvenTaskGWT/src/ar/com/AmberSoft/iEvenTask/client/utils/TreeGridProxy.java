package ar.com.AmberSoft.iEvenTask.client.utils;

import java.util.HashMap;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.client.Context;
import ar.com.AmberSoft.iEvenTask.shared.DispatcherUtil;
import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;
import ar.com.AmberSoft.iEvenTask.shared.ServiceNameConst;

import com.extjs.gxt.ui.client.data.BaseFilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.DataReader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class TreeGridProxy extends RpcProxy {

	private final TreeGridDataCallback callback;
	private Map params = new HashMap<String, String>();
	
	@Override
	public void load(final DataReader reader, final Object loadConfig,
			final AsyncCallback callback) {
		load(loadConfig, new AsyncCallback() {

		      public void onFailure(Throwable caught) {
		        callback.onFailure(caught);
		      }

		      @SuppressWarnings("unchecked")
		      public void onSuccess(Object result) {
		        try {
		          Object data = null;
		          if (reader != null) {
		            data = reader.read(loadConfig, result);
		          } else {
		            data = result;
		          }
		          callback.onSuccess(((PagingLoadResult)data).get(ParamsConst.DATA));
		        } catch (Exception e) {
		          Context.getInstance().addDetailExecution(e.getMessage());
		          callback.onFailure(e);
		        }
		      }

		    });
	}

	public TreeGridProxy(String service, TreeGrid grid){
		callback = new TreeGridDataCallback(grid);
		params.put(ServiceNameConst.SERVICIO, service);
	}
	
	public TreeGridDataCallback getCallback() {
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

public void setGrid(TreeGrid grid) {
	callback.setGrid(grid);
}
	
}
