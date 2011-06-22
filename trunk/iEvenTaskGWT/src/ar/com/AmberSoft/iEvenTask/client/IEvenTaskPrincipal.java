package ar.com.AmberSoft.iEvenTask.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.extjs.gxt.ui.client.widget.menu.MenuBar;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuBarItem;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import java.util.Collections;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;

public class IEvenTaskPrincipal implements EntryPoint {

	public void onModuleLoad() {
		RootPanel rootPanel = RootPanel.get();
		
		MenuBar menuBar = new MenuBar();
		
		Menu menu = new Menu();
		menuBar.setContextMenu(menu);
		
		Menu menu_1 = new Menu();
		
		MenuItem mntmPreferencias = new MenuItem("Preferencias");
		menu_1.add(mntmPreferencias);
		MenuBarItem mnbrtmNewMenubaritem = new MenuBarItem("Herramientas", menu_1);
		menuBar.add(mnbrtmNewMenubaritem);
		rootPanel.add(menuBar);
		menuBar.setSize("454px", "15px");
		rootPanel.setWidgetPosition(menuBar, 0, 0);
		
		Grid grid = new Grid(new ListStore(), new ColumnModel(Collections.<ColumnConfig>emptyList()));
		rootPanel.add(grid);
		rootPanel.setWidgetPosition(grid, 59, 57);
		grid.setSize("302px", "202px");
		grid.setBorders(true);
	}
}
