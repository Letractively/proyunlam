package ar.com.AmberSoft.iEvenTask.client.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.data.ListLoadConfig;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.ListLoader;
import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.SortInfo;
import com.extjs.gxt.ui.client.util.Util;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;

public class ListStore extends com.extjs.gxt.ui.client.store.ListStore {

	public ListStore() {
		super();
	}

	public ListStore(ListLoader loader) {
		super(loader);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void onLoad(LoadEvent le) {
		this.config = (ListLoadConfig) le.getConfig();

		Object data = le.getData();

		removeAll();

		if (data == null) {
			all = new ArrayList();
		} else if (data instanceof List) {
			List list = (List) data;
			all = new ArrayList(list);
		} else if (data instanceof ListLoadResult) {
			all = getAllListLoadResult(data);
		}

		Iterator it = all.iterator();
		while (it.hasNext()) {
			registerModel((ModelData) it.next());

		}

		if (config != null && config.getSortInfo() != null
				&& !Util.isEmptyString(config.getSortInfo().getSortField())) {
			sortInfo = config.getSortInfo();
		} else {
			sortInfo = new SortInfo();
		}

		if (filtersEnabled) {
			filtersEnabled = false;
			applyFilters(filterProperty);
		}

		if (storeSorter != null) {
			applySort(true);
		}
		fireEvent(DataChanged, createStoreEvent());
	}

	private ArrayList getAllListLoadResult(Object data) {
		ArrayList list = new ArrayList();
		Iterator itList = ((ListLoadResult) data).getData().iterator();
		while (itList.hasNext()) {
			Map actual = (Map) itList.next();
			ColumnModel model = ((Proxy)((Loader)this.getLoader()).getProxy()).getCallback().getGrid().getColumnModel();
			BaseModel baseModel = new BaseModel();
			List columns = model.getColumns();
			Iterator it = columns.iterator();
			while (it.hasNext()) {
				ColumnConfig columnConfig = (ColumnConfig) it.next();
				baseModel.set(columnConfig.getId(), actual.get(columnConfig.getId()));
			}
			list.add(baseModel);
		}
		return list;
	}

}
