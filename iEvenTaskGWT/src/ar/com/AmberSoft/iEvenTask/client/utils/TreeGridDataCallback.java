package ar.com.AmberSoft.iEvenTask.client.utils;

import com.extjs.gxt.ui.client.widget.Info;
import com.google.gwt.user.client.rpc.AsyncCallback;

@SuppressWarnings({"rawtypes", "unchecked"})
public class TreeGridDataCallback implements AsyncCallback {

	private TreeGrid grid;
	private AsyncCallback proxyCallback;


	public TreeGridDataCallback(TreeGrid grid) {
		this.grid = grid;
	}
	
	public TreeGrid getGrid() {
		return grid;
	}

	public void setGrid(TreeGrid grid) {
		this.grid = grid;
	}

	public void setProxyCallback(AsyncCallback proxyCallback) {
		this.proxyCallback = proxyCallback;
	}
	
	public void onFailure(Throwable caught) {
		Info.display("iEvenTask",
				"Fallo en la obtencion de la informacion para la tree grilla.");
		if (proxyCallback != null) {
			proxyCallback.onFailure(caught);
		}

	}

	@SuppressWarnings("unused")
	@Override
	public void onSuccess(Object result) {
		if (result instanceof PagingLoadResult) {
			PagingLoadResult map = (PagingLoadResult) result;

			if (proxyCallback != null) {
				proxyCallback.onSuccess(result);
			}
		}
	}

}
