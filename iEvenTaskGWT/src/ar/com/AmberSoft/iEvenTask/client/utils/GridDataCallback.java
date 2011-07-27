package ar.com.AmberSoft.iEvenTask.client.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class GridDataCallback implements AsyncCallback {
	
	private Grid grid;

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	@Override
	public void onFailure(Throwable caught) {
		
		Info.display("iEvenTask","Fallo en la obtencion de la informacion para la grilla.");
	}

	@Override
	public void onSuccess(Object result) {
		if ((result instanceof Map) && (grid!=null)) {
			Map map = (Map) result;
			Collection list = (Collection) map.get(ParamsConst.LIST);
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
					baseModel.set(columnConfig.getId(), actual.get(columnConfig.getId()));
				}
				store.add(baseModel);
			}

			
		}
		
	}

}
