package ar.com.AmberSoft.iEvenTask.client;

import ar.com.AmberSoft.iEvenTask.client.menu.MainMenu;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Status;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class IEvenTask implements EntryPoint {

	public static Integer appWindowWidth = 1024;
	public static Integer appWindowHeigth = 623;
	
	private FlexTable	taskFlexTable	= new FlexTable();

	public void onModuleLoad() {
		RootPanel rootPanel = RootPanel.get();
		rootPanel.setStyleName("body");
		rootPanel.setSize(appWindowWidth.toString(), appWindowHeigth.toString());

		//LoginWindow loginWindow = new LoginWindow();
		//loginWindow.show();

		
		
		
		/*
		  MenuBar menuBar = new MenuBar();

		Menu menu = new Menu();
		menuBar.setContextMenu(menu);

		Menu menu_1 = new Menu();


		MenuItem mntmPerfiles = new MenuItem("Perfiles");
		mntmPerfiles.addSelectionListener(new SelectionListener<MenuEvent>() {
			public void componentSelected(MenuEvent ce) {
				ProfilesWindow profilesWindow = new ProfilesWindow();
				try {
					profilesWindow.show();
				} catch (WindowPreviouslyRegisteredException e){
					// FIXME: Mostrar por interfaz grafica un mensaje de error
				}
			}
		});
		
		menu_1.add(mntmPerfiles);
		
		
		MenuItem mntmEvent = new MenuItem("Eventos");
		mntmEvent.addSelectionListener(new SelectionListener<MenuEvent>() {
			public void componentSelected(MenuEvent ce) {
				EventWindow eventWindow = new EventWindow();
				eventWindow.show();
			}
		});
		mntmEvent.setIcon(Resources.ICONS.add24());
		
		menu_1.add(mntmEvent);
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
		menuBar.setSize(appWindowWidth.toString(), "24px");
		*/
		
		MainMenu mainMenu = new MainMenu();
		rootPanel.add(mainMenu);
		rootPanel.setWidgetPosition(mainMenu, 0, 0);

		ToolBar buttonsBar = new ToolBar();

		Button btnNuevaTarea = new Button("Nueva Tarea");
		btnNuevaTarea.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent be) {
				TaskWindowOld taskWindow = new TaskWindowOld();
				taskWindow.show();
			}
		});
		buttonsBar.add(btnNuevaTarea);

		Button btnNuevoObjetivo = new Button("Nuevo Objetivo");
		btnNuevoObjetivo.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent be) {
				ObjectiveWindow objectiveWindow = new ObjectiveWindow();
				objectiveWindow.show();
			}
		});
		buttonsBar.add(btnNuevoObjetivo);
		rootPanel.add(buttonsBar);
		rootPanel.setWidgetPosition(buttonsBar, 0, 24);

		// Crea la tabla para guardar las tareas
		taskFlexTable.setText(0, 0, "ID");
		taskFlexTable.setText(0, 1, "Nombre");
		taskFlexTable.setText(0, 2, "Fecha Comienzo");
		taskFlexTable.setText(0, 3, "Fecha Fin");
		taskFlexTable.setText(0, 4, "Duracion");
		taskFlexTable.setText(0, 5, "Descripcion");

		// Agrega estilos a los elementos de la tabla de tareas.
		taskFlexTable.setCellPadding(6);
		/*taskFlexTable.getRowFormatter().addStyleName(0, "watchListHeader");
		taskFlexTable.addStyleName("watchList");
		taskFlexTable.getCellFormatter().addStyleName(0, 1, "watchListNumericColumn");
		taskFlexTable.getCellFormatter().addStyleName(0, 2, "watchListNumericColumn");
		taskFlexTable.getCellFormatter().addStyleName(0, 3, "watchListRemoveColumn");
		*/

		rootPanel.add(taskFlexTable, 10, 80);
		
		final ContentPanel commentPanel = new ContentPanel();
		commentPanel.setHeading("Comentarios");
		
		final TextArea newTextComment = new TextArea();
		newTextComment.setCharacterWidth(35);
		newTextComment.setVisibleLines(5);
		commentPanel.add(newTextComment);
		newTextComment.setSize("290", "80");
		
		final TextArea otherTextComment = new TextArea();
		otherTextComment.setCharacterWidth(35);
		otherTextComment.setVisibleLines(5);
		otherTextComment.setDirectionEstimator(true);
		otherTextComment.setReadOnly(true);
		otherTextComment.setVisible(false);
		
		Button btnAddComment = new Button("Add Comment");
		btnAddComment.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent be) {
				otherTextComment.setText(newTextComment.getText());
				otherTextComment.setVisible(true);
			}
		});
		commentPanel.setTopComponent(btnAddComment);
		commentPanel.add(otherTextComment);
		otherTextComment.setWidth("290");
		
		rootPanel.add(commentPanel);
		rootPanel.setWidgetPosition(commentPanel, 449, 77);
		commentPanel.setSize("300px", "287px");
		
		
		// Barra de estado
	    ToolBar statusBar = new ToolBar();  
	    
	    Status status = new Status();  
	    status.setText("Not writing");  
	    statusBar.add(status);  
	    statusBar.add(new FillToolItem());
		rootPanel.add(statusBar);
		rootPanel.setWidgetPosition(statusBar, 0, new Integer(appWindowHeigth - 30));
	
		
		
		
		
	}
}
