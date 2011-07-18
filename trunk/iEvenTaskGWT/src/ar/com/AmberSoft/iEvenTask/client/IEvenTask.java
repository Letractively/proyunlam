package ar.com.AmberSoft.iEvenTask.client;

import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuBar;
import com.extjs.gxt.ui.client.widget.menu.MenuBarItem;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.extjs.gxt.ui.client.widget.custom.Portal;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.button.SplitButton;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import java.util.Collections;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.Header;
import com.extjs.gxt.ui.client.widget.button.ButtonBar;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.google.gwt.user.client.ui.DecoratedStackPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.extjs.gxt.ui.client.widget.button.ToggleButton;
import com.extjs.gxt.ui.client.event.WidgetListener;
import com.extjs.gxt.ui.client.event.ComponentEvent;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class IEvenTask implements EntryPoint {

	public void onModuleLoad() {
		RootPanel rootPanel = RootPanel.get();
		rootPanel.setStyleName("dialogVPanel");
		rootPanel.setSize("1024", "768");
		
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
		MenuBarItem mnbrtmNewMenubaritem = new MenuBarItem("Archivo", menu_1);
		menuBar.add(mnbrtmNewMenubaritem);
		
		Menu menu_2 = new Menu();
		
		MenuItem mntmBuscar = new MenuItem("Buscar");
		menu_2.add(mntmBuscar);
		
		MenuItem mntmBorrar = new MenuItem("Borrar");
		menu_2.add(mntmBorrar);
		MenuBarItem mnbrtmEdicion = new MenuBarItem("Edicion", menu_2);
		menuBar.add(mnbrtmEdicion);
		
		Menu menu_3 = new Menu();
		
		MenuItem mntmMostrarFiltros = new MenuItem("Mostrar Filtros");
		menu_3.add(mntmMostrarFiltros);
		MenuBarItem mnbrtmVista = new MenuBarItem("Vista", menu_3);
		menuBar.add(mnbrtmVista);
		
		Menu menu_4 = new Menu();
		
		MenuItem mntmExportar = new MenuItem("Exportar...");
		menu_4.add(mntmExportar);
		MenuBarItem mnbrtmHerramientas = new MenuBarItem("Herramientas", menu_4);
		menuBar.add(mnbrtmHerramientas);
		
		Menu menu_5 = new Menu();
		
		MenuItem mntmAyudaEnLinea = new MenuItem("Ayuda en linea");
		menu_5.add(mntmAyudaEnLinea);
		
		MenuItem mntmAcercaDeIeventask = new MenuItem("Acerca de iEvenTask");
		menu_5.add(mntmAcercaDeIeventask);
		MenuBarItem mnbrtmAyuda = new MenuBarItem("Ayuda", menu_5);
		menuBar.add(mnbrtmAyuda);
		rootPanel.add(menuBar);
		menuBar.setSize("578px", "18px");
		rootPanel.setWidgetPosition(menuBar, 0, 0);
		
		LayoutContainer layoutContainer = new LayoutContainer();
		rootPanel.add(layoutContainer);
		rootPanel.setWidgetPosition(layoutContainer, 584, 0);
		layoutContainer.setSize("164px", "466px");
		layoutContainer.setBorders(true);
		
		TabPanel tabPanel = new TabPanel();
		rootPanel.add(tabPanel);
		rootPanel.setWidgetPosition(tabPanel, 0, 81);
		tabPanel.setSize("578px", "387px");
		
		ToolBar toolBar = new ToolBar();
		
		Button btnNuevaTarea = new Button("Nueva Tarea");
		btnNuevaTarea.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent be) {
				TaskWindow taskWindow = new TaskWindow();
				taskWindow.show();
			}
		});
		toolBar.add(btnNuevaTarea);
		
		Button btnNuevoObjetivo = new Button("Nuevo Objetivo");
		btnNuevoObjetivo.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent be) {
				ObjectiveWindow objectiveWindow = new ObjectiveWindow();
				objectiveWindow.show();
			}
		});
		toolBar.add(btnNuevoObjetivo);
		rootPanel.add(toolBar);
		rootPanel.setWidgetPosition(toolBar, 0, 24);
		toolBar.setSize("562px", "34px");
	}
}
