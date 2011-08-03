package ar.com.AmberSoft.iEvenTask.client.utils;

import java.util.List;

import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.filters.Filter;
import com.extjs.gxt.ui.client.widget.grid.filters.GridFilters;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;

public class Grid extends com.extjs.gxt.ui.client.widget.grid.Grid {

	private final GridDataCallback callback = new GridDataCallback();
	final GridFilters filters = new GridFilters();
	Menu contextMenu;
	

	public Grid(String serviceName, List<ColumnConfig> configs){
		super(new ListStore(new Loader(serviceName)), new ColumnModel(configs));
		Loader loader = (Loader) this.getStore().getLoader(); 
		loader.setCallback(callback);
		callback.setGrid(this);	
		filters.setLocal(Boolean.FALSE);
		this.addPlugin(filters);
		loader.setRemoteSort(Boolean.TRUE);
	}
	
	
	public void addFilter(Filter filter){
		filters.addFilter(filter);
	}

	public MenuItem addContextMenuItem(String text, Boolean enabled, SelectionListener<MenuEvent> listener){
		initializeContextMenu();
		MenuItem item = new MenuItem();
		item.setText(text);
		item.addSelectionListener(listener);
		item.setEnabled(enabled);
		contextMenu.add(item);
		return item;
	}


	private void initializeContextMenu() {
		if (contextMenu==null){
			contextMenu = new Menu();
			this.setContextMenu(contextMenu);
		}
	}
	
}
