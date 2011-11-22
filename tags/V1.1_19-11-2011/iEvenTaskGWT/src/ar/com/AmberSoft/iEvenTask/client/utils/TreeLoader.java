package ar.com.AmberSoft.iEvenTask.client.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.client.Context;
import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;

import com.extjs.gxt.ui.client.data.BaseTreeLoader;
import com.extjs.gxt.ui.client.data.BaseTreeModel;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.TreeLoadEvent;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;

@SuppressWarnings({"rawtypes", "unchecked"})
public class TreeLoader extends BaseTreeLoader {

	@Override
	protected void onLoadSuccess(Object loadConfig, List result) {
		ArrayList list = new ArrayList();
		Iterator itList = result.iterator();
		while (itList.hasNext()) {
			Map actual = (Map) itList.next();
			ColumnModel model = ((TreeGridProxy)this.getProxy()).getCallback().getGrid().getColumnModel();
			BaseTreeModel baseModel;
			if (Boolean.TRUE.equals(actual.get(ParamsConst.IS_DIRECTORY))) {
				Context.getInstance().addDetailExecution("Instanciando Folder");
				baseModel = new Folder();
			} else {
				Context.getInstance().addDetailExecution("Instanciando Music");
				baseModel = new Music();
			}
			List columns = model.getColumns();
			Iterator it = columns.iterator();
			while (it.hasNext()) {
				ColumnConfig columnConfig = (ColumnConfig) it.next();
				baseModel.set(columnConfig.getId(), actual.get(columnConfig.getId()));
			}
			list.add(baseModel);
		}		
		
	    TreeLoadEvent evt = new TreeLoadEvent(this, loadConfig, list);
	    if (loadConfig != null && children.contains(loadConfig)) {
	      evt.parent = (ModelData) loadConfig;
	      children.remove(loadConfig);
	    }
	    fireEvent(Load, evt);
	}

	@Override
	public boolean load() {
		setReuseLoadConfig(Boolean.TRUE);
		return super.load();
	}

	public TreeLoader(String service, TreeGrid grid) {
		super(new TreeGridProxy(service, grid));
	}
	
	public void setGrid(TreeGrid grid) {
		((TreeGridProxy)proxy).setGrid(grid);
	}
	
}
