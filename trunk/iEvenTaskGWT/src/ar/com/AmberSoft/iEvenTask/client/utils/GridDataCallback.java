package ar.com.AmberSoft.iEvenTask.client.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class GridDataCallback implements AsyncCallback {

	private Grid grid;
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
		Info.display("iEvenTask",
				"Fallo en la obtencion de la informacion para la grilla.");
		if (proxyCallback != null) {
			proxyCallback.onFailure(caught);
		}

	}

	@Override
	public void onSuccess(Object result) {
		if ((result instanceof PagingLoadResult) && (grid != null)) {
			PagingLoadResult map = (PagingLoadResult) result;

			Collection list = map.getData();
			grid.setList(list);

			//FIXME:Aparentemente esta parte no va mas
			ListStore store = grid.getStore();
			store.removeAll();
			Iterator itList = list.iterator();
			while (itList.hasNext()) {
				Map actual = (Map) itList.next();
				ColumnModel model = grid.getColumnModel();
				BaseModel baseModel = new BaseModel();
				List columns = model.getColumns();
				Iterator it = columns.iterator();
				while (it.hasNext()) {
					ColumnConfig columnConfig = (ColumnConfig) it.next();
					baseModel.set(columnConfig.getId(),
							actual.get(columnConfig.getId()));
				}
				store.add(baseModel);
			}
			// Fin de Aparentemente esta parte no va mas

			grid.unmask();

			if (proxyCallback != null) {
				proxyCallback.onSuccess(result);
			}
		}

	}

}
