package ar.com.AmberSoft.iEvenTask.client.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.data.BaseTreeLoader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.TreeLoadEvent;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;

public class TreeLoader extends BaseTreeLoader {

	@Override
	protected void onLoadSuccess(Object loadConfig, List result) {
		//FIXME: Transformar result en objetos de tipo modeldata
		
		ArrayList list = new ArrayList();
		Iterator itList = result.iterator();
		while (itList.hasNext()) {
			Map actual = (Map) itList.next();
			ColumnModel model = ((TreeGridProxy)this.getProxy()).getCallback().getGrid().getColumnModel();
			BaseModel baseModel = new BaseModel();
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
