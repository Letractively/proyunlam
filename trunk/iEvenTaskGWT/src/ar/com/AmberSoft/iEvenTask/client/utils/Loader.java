package ar.com.AmberSoft.iEvenTask.client.utils;

import com.extjs.gxt.ui.client.data.BaseFilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class Loader extends BasePagingLoader{

	@Override
	protected Object newLoadConfig() {
        BasePagingLoadConfig config = new BaseFilterPagingLoadConfig();  
        return config;
        // Revisar si es necesario reescribir este metodo
		//return super.newLoadConfig();
	}

	public Loader(String service, AsyncCallback callback) {
		super(null);
		proxy = new Proxy();
		((Proxy)proxy).setService(service);
		((Proxy)proxy).setCallback(callback);
		setRemoteSort(Boolean.TRUE);
	}


}
