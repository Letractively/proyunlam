package ar.com.AmberSoft.iEvenTask.client;

import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuBar;
import com.extjs.gxt.ui.client.widget.menu.MenuBarItem;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class IEvenTask implements EntryPoint {

	public void onModuleLoad() {
		RootPanel rootPanel = RootPanel.get();
		
		LoginWindow loginWindow = new LoginWindow();
		loginWindow.show();
		
		MenuBar menuBar = new MenuBar();
		
		Menu menu = new Menu();
		menuBar.setContextMenu(menu);
		
		Menu menu_1 = new Menu();
		
		MenuItem mntmPerfiles = new MenuItem("Perfiles");
		mntmPerfiles.addSelectionListener(new SelectionListener<MenuEvent>() {
			public void componentSelected(MenuEvent ce) {
				ProfilesWindow profilesWindow = new ProfilesWindow();
				profilesWindow.show();
			}
		});
		menu_1.add(mntmPerfiles);
		
		MenuItem mntmAcciones = new MenuItem("Acciones");
		menu_1.add(mntmAcciones);
		
		MenuItem mntmPreferencias = new MenuItem("Preferencias");
		menu_1.add(mntmPreferencias);
		MenuBarItem mnbrtmNewMenubaritem = new MenuBarItem("Herramientas", menu_1);
		menuBar.add(mnbrtmNewMenubaritem);
		rootPanel.add(menuBar);
		menuBar.setSize("454px", "15px");
		rootPanel.setWidgetPosition(menuBar, 0, 0);
	}
}
