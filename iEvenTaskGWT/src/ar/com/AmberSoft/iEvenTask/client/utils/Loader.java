package ar.com.AmberSoft.iEvenTask.client.utils;

import com.extjs.gxt.ui.client.data.BasePagingLoader;

public class Loader extends BasePagingLoader{

	@Override
	public boolean load() {
		setReuseLoadConfig(Boolean.TRUE);
		return super.load();
	}

	public Loader(String service, Grid grid) {
		super(null);
		proxy = new Proxy(service, grid);
		setRemoteSort(Boolean.TRUE);
	}
	
	public void setGrid(Grid grid) {
		((Proxy)proxy).setGrid(grid);
	}
	
	

	
}
