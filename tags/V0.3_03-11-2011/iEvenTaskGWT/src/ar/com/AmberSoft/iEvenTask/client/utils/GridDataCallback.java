package ar.com.AmberSoft.iEvenTask.client.utils;

import ar.com.AmberSoft.iEvenTask.client.Context;
import ar.com.AmberSoft.iEvenTask.client.DialogFactory;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class GridDataCallback implements AsyncCallback {

	protected Grid grid;
	private AsyncCallback proxyCallback;

	public void setProxyCallback(AsyncCallback proxyCallback) {
		this.proxyCallback = proxyCallback;
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public GridDataCallback(Grid grid) {
		this.grid = grid;
	}

	@Override
	public void onFailure(Throwable caught) {
		Context.getInstance().addDetailExecution(caught.getMessage());
		DialogFactory.error("Fallo en la obtencion de la informacion para la grilla.");
		if (proxyCallback != null) {
			proxyCallback.onFailure(caught);
		}

	}

	@Override
	public void onSuccess(Object result) {
		if ((result instanceof PagingLoadResult) && (grid != null)) {
			PagingLoadResult map = (PagingLoadResult) result;
			grid.setList(map.getData());
			grid.unmask();

			if (proxyCallback != null) {
				proxyCallback.onSuccess(result);
			}
		}

	}

}
