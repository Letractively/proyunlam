package ar.com.AmberSoft.iEvenTask.client.utils;

import java.util.Map;

import com.extjs.gxt.ui.client.data.BasePagingLoader;

@SuppressWarnings("rawtypes")
public class Loader extends BasePagingLoader{

	@Override
	public boolean load() {
		setReuseLoadConfig(Boolean.TRUE);
		return super.load();
	}

	@SuppressWarnings("unchecked")
	public Loader(String service, Grid grid, Map params) {
		super(null);
		proxy = new Proxy(service, grid, params);
		setRemoteSort(Boolean.TRUE);
	}
	
	@SuppressWarnings("unchecked")
	public Loader(String service, Grid grid) {
		super(null);
		proxy = new Proxy(service, grid);
		setRemoteSort(Boolean.TRUE);
	}
	
	public void setGrid(Grid grid) {
		((Proxy)proxy).setGrid(grid);
	}
	
	public Proxy getProxy(){
		return ((Proxy)proxy);
	}
	
	

	
}
