package ar.com.AmberSoft.iEvenTask.client.utils;

import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.TreeLoadEvent;


@SuppressWarnings("rawtypes")
public class TreeStore extends com.extjs.gxt.ui.client.store.TreeStore {

	@Override
	protected void onBeforeLoad(LoadEvent le) {
		super.onBeforeLoad(le);
	}

	@Override
	protected void onLoad(TreeLoadEvent le) {
		super.onLoad(le);
	}

	public TreeStore() {
		super();
	}

	@SuppressWarnings("unchecked")
	public TreeStore(TreeLoader loader) {
		super(loader);
	}

}
