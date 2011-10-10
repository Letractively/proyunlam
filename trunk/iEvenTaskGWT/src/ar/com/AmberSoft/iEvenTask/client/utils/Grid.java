package ar.com.AmberSoft.iEvenTask.client.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.client.Seleccionable;
import ar.com.AmberSoft.iEvenTask.client.TaskWindow;

import com.extjs.gxt.ui.client.data.BaseFilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.filters.Filter;
import com.extjs.gxt.ui.client.widget.grid.filters.GridFilters;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;

public class Grid extends com.extjs.gxt.ui.client.widget.grid.Grid {

	public static final String SELECT_ALL = "Seleccionar Todos";
	public static final String DELETE = "Elminar";
	public static final String MODIFY = "Modificar";

	/**
	 * Ventana actual con la que trabaja la grilla
	 */
	//private Window window;
	private Seleccionable seleccionable;
	/**
	 * Filtros que se utilizaran en la grilla
	 */
	private final GridFilters filters = new GridFilters();

	/**
	 * Menu contextual que aparece al presionar el boton derecho del mouse sobre
	 * la grilla
	 */
	private Menu contextMenu;

	/**
	 * Lista con los modelData que muestra la grilla
	 */
	private Collection list;

	/**
	 * Item del menu por default cuya funcionalidad es eliminar un elemento de
	 * la grilla
	 */
	private MenuItem itemDelete;

	/**
	 * Item del menu por default cuya funcionalidad es modificar un elemento de
	 * la grilla
	 */
	private MenuItem itemModify;

	/**
	 * Barra de herramientas que se muestra debajo de la grilla
	 */
	private PagingToolBar toolBar;

	public PagingToolBar getToolBar() {
		return toolBar;
	}

	public Collection getList() {
		return list;
	}

	public void setList(Collection list) {
		this.list = list;
	}

	/**
	 * Busca en la lista de datos que muestra la grilla actualmente un modelData
	 * a partir del valor que pueda contener un campo en particular Retorna el
	 * primero modelData que coincide con la busqueda
	 * 
	 * @param field
	 * @param value
	 * @return
	 */
	public Map search(String field, Object value) {
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Map actual = (Map) it.next();
			if (actual.get(field).equals(value)) {
				return actual;
			}
		}
		return null;
	}

	public Grid(Seleccionable seleccionable, String serviceName, List<ColumnConfig> configs, final Integer pageSize) {
		// Se invoca al constructor padre para que se inicialice todo
		// convenientemente
		super(new ListStore(new Loader(serviceName, null)), new ColumnModel(configs));
		this.seleccionable = seleccionable;

		// Se obtiene el loader para indicarle cual es la grilla con la que
		// trabaja y que el orden de las columnas se establece de forma remota
		final Loader loader = (Loader) this.getStore().getLoader();
		loader.setGrid(this);
		loader.setRemoteSort(Boolean.TRUE);

		// Se establece que los filtros se trabajaran de forma remota
		filters.setLocal(Boolean.FALSE);
		this.addPlugin(filters);

		// Establecemos una barra de herramientas y la relacionamos con el
		// loader actual
		toolBar = new PagingToolBar(pageSize);
		toolBar.bind(loader);

		setStateId("pagingGridExample");
		setStateful(Boolean.TRUE);

		// Se agrega un listener necesario para recargar la informacion de la
		// grilla cuando se introduce algun filtro
		addListener(Events.Attach, new Listener<GridEvent>() {
			public void handleEvent(GridEvent be) {
				PagingLoadConfig config = new BaseFilterPagingLoadConfig();
				config.setOffset(0);
				config.setLimit(pageSize);
				loader.load(config);
			}
		});

		// Habilitamos para que se muestre una mascara mientras se esta cargando
		// la grilla
		setLoadMask(Boolean.TRUE);

	}
	
	/**
	 * Agrega un nuevo filtro
	 * 
	 * @param filter
	 */
	public void addFilter(Filter filter) {
		filters.addFilter(filter);
	}

	/**
	 * Agrega un nuevo item al menu contextual
	 * 
	 * @param text
	 * @param enabled
	 * @param listener
	 * @return
	 */
	public MenuItem addContextMenuItem(String text, Boolean enabled,
			SelectionListener<MenuEvent> listener) {
		initializeContextMenu();
		MenuItem item = new MenuItem();
		item.setText(text);
		item.addSelectionListener(listener);
		item.setEnabled(enabled);
		contextMenu.add(item);
		return item;
	}

	/**
	 * Si el menu contextual no fue generado entonces lo crea
	 */
	private void initializeContextMenu() {
		if (contextMenu == null) {
			contextMenu = new Menu();
			this.setContextMenu(contextMenu);
		}
	}

	/**
	 * Establece un menu contextual por defecto Se le pasa la ventana actual por
	 * parametro para poder establecer el estado y otras cuestiones
	 */
	public void defaultContextMenu() {
		addMenuItemSelectAll();
		itemDelete = addContextMenuItem(DELETE, Boolean.FALSE,
				new SelectionListener<MenuEvent>() {
					@Override
					public void componentSelected(MenuEvent ce) {
						seleccionable.onDelete();
					}
				});
	}
	/**
	 * Este metodo es igual al anterior, con la unica diferencia que se agrega el item "modificar"
	 */
	public void defaultContextMenuTask() {
		addMenuItemSelectAll();
		itemDelete = addContextMenuItem(DELETE, Boolean.FALSE,
				new SelectionListener<MenuEvent>() {
			@Override
			public void componentSelected(MenuEvent ce) {
				seleccionable.onDelete();
			}
		});
		itemModify = addContextMenuItem(MODIFY, Boolean.FALSE,
				new SelectionListener<MenuEvent>() {
			@Override
			public void componentSelected(MenuEvent ce) {
				seleccionable.onModify();
			}
		});
	}

	/**
	 * Agrega al menu contextual un item que permite seleccionar todos los elementos
	 * de la grilla
	 * @param window
	 */
	private void addMenuItemSelectAll() {
		addContextMenuItem(SELECT_ALL, Boolean.TRUE,
				new SelectionListener<MenuEvent>() {
					@Override
					public void componentSelected(MenuEvent ce) {
						getSelectionModel().selectAll();
						//FIXME: Ver como solucionar este tema, ya que aparentemente es necesario setear el estado
						//seleccionable.setWindowState(State.UNKNOW_STATE);
					}
				});
	}
	
	/**
	 * Accion que realizará por defecto al seleccionar un registro de la grilla
	 */
	public void defaultActionOnSelectItem(){
		// Acciones a realizar cuando selecciona algun registro de la grilla
		getSelectionModel().addListener(Events.SelectionChange,
				new Listener() {
					@Override
					public void handleEvent(BaseEvent be) {
						List seleccionados = getSelectionModel().getSelection();
						itemDelete.setEnabled(((seleccionados!=null) && (!seleccionados.isEmpty())));
						seleccionable.onSelect(seleccionados);
					}
				});		
	}
	
	/**
	 * Accion que realizará por defecto al seleccionar un registro de la grilla de Tareas
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void defaultActionOnSelectItemTask(){
		// Acciones a realizar cuando selecciona algun registro de la grilla
		getSelectionModel().addListener(Events.SelectionChange,
				new Listener() {
			@Override
			public void handleEvent(BaseEvent be) {
				List seleccionados = getSelectionModel().getSelection();
				itemDelete.setEnabled(((seleccionados!=null) && (!seleccionados.isEmpty())));
				itemModify.setEnabled(((seleccionados!=null) && (!seleccionados.isEmpty())));
				seleccionable.onSelect(seleccionados);
			}
		});		
	}
	
}
