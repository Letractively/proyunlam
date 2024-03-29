package ar.com.AmberSoft.iEvenTask.client.menu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.client.Context;
import ar.com.AmberSoft.iEvenTask.client.DialogFactory;
import ar.com.AmberSoft.iEvenTask.client.IEvenTask;
import ar.com.AmberSoft.iEvenTask.client.ObjectiveWindow;
import ar.com.AmberSoft.iEvenTask.client.PermissionsConst;
import ar.com.AmberSoft.iEvenTask.client.Seleccionable;
import ar.com.AmberSoft.iEvenTask.client.TaskWindow;
import ar.com.AmberSoft.iEvenTask.client.utils.Grid;
import ar.com.AmberSoft.iEvenTask.shared.DispatcherUtil;
import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;
import ar.com.AmberSoft.iEvenTask.shared.ServiceNameConst;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.filters.DateFilter;
import com.extjs.gxt.ui.client.widget.grid.filters.StringFilter;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;


public class MainTabObjetivos extends TabItem implements Seleccionable{
	
	public static final Integer GRID_WIDTH = IEvenTask.APP_WINDOW_WIDTH;
	public static final Integer GRID_HEIGTH = IEvenTask.MAIN_TAB_PANEL_HEIGTH;
	
	public static final Integer COMMENT_WIDTH = IEvenTask.APP_WINDOW_WIDTH - GRID_WIDTH;
	public static final Integer COMMENT_HEIGTH = IEvenTask.MAIN_TAB_PANEL_HEIGTH;
	
	@SuppressWarnings("unchecked")
	public final Grid grid = new Grid(this, ServiceNameConst.LIST_OBJECTIVE_WITH_VISIBLE_FILTER, getGridConfig(), 18);
	private final ListStore<ModelData> storeTareas = new ListStore<ModelData>();
	private final com.extjs.gxt.ui.client.widget.grid.Grid<ModelData> gridTareas = new com.extjs.gxt.ui.client.widget.grid.Grid<ModelData>(storeTareas, new ColumnModel(getGridConfig()));

	public MainTabObjetivos() {
		super("Objetivos");
		
		grid.addFilter(new StringFilter(ParamsConst.NOMBRE_OBJETIVO));
		grid.addFilter(new StringFilter(ParamsConst.TIPO_OBJETIVO));
		grid.addFilter(new DateFilter(ParamsConst.FECHA_FINALIZACION));
		grid.addFilter(new StringFilter(ParamsConst.ESCALA_MEDICION));
		//FIXME: revisar como filtrar correctamente los numero
		//grid.addFilter(new NumericFilter(ParamsConst.PONDERACION));
		//grid.addFilter(new StringFilter(ParamsConst.ASIGNADO));
		grid.addFilter(new StringFilter(ParamsConst.DESCRIPCION));
		//grid.addFilter(new NumericFilter(ParamsConst.CUMPLIMIENTO));
		Context.getInstance().setObjectiveGrid(grid);
		
		// TODO acomodar posicion
		setSize(IEvenTask.APP_WINDOW_WIDTH.toString(), IEvenTask.DEFAULT_MENU_HEIGTH.toString());
		
		//componentes del panel de objetivos
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		VerticalPanel verticalPanel_grilla = new VerticalPanel();
		
		//seteo las propiedades al componente Grid
		grid.setSize(GRID_WIDTH, GRID_HEIGTH);
		grid.defaultContextMenuObjective();
		grid.defaultActionOnSelectItemObjective();
		grid.setBorders(true);
		verticalPanel_grilla.add(grid.getToolBar());
		verticalPanel_grilla.add(grid);
		horizontalPanel.add(verticalPanel_grilla);
		
		this.add(horizontalPanel);
	}
	
	
	@SuppressWarnings("rawtypes")
	private List getGridConfig() {
		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
		DateTimeFormat dtf = DateTimeFormat.getFormat("dd-MM-yyyy");
		
		// Se agrega esta columna para mantener el identificador de las objetivos
		ColumnConfig clmncnfgId = new ColumnConfig(ParamsConst.ID, ParamsConst.ID, 1);
		clmncnfgId.setHidden(Boolean.TRUE);
		configs.add(clmncnfgId);

		ColumnConfig clmncnfgNombreObjetivo = new ColumnConfig(ParamsConst.NOMBRE_OBJETIVO, "Nombre", 200);
		configs.add(clmncnfgNombreObjetivo);

		ColumnConfig clmncnfgTipoObjetivo = new ColumnConfig(ParamsConst.TIPO_OBJETIVO, "Tipo", 100);
		configs.add(clmncnfgTipoObjetivo);

		ColumnConfig clmncnfgFechaFinalizacion = new ColumnConfig(ParamsConst.FECHA_FINALIZACION, "Fecha de Finalizacion", 120);
		clmncnfgFechaFinalizacion.setDateTimeFormat(dtf);
		configs.add(clmncnfgFechaFinalizacion);

		ColumnConfig clmncnfgEscalaMedicion = new ColumnConfig(ParamsConst.ESCALA_MEDICION, "Escala de Medicion", 100);
		configs.add(clmncnfgEscalaMedicion);
		
		ColumnConfig clmncnfgPonderacion = new ColumnConfig(ParamsConst.PONDERACION, "Ponderacion", 80);
		configs.add(clmncnfgPonderacion);

		ColumnConfig clmncnfgUsuarioAsignado = new ColumnConfig(ParamsConst.ASIGNADO, "Asignado a", 100);
		configs.add(clmncnfgUsuarioAsignado);

		ColumnConfig clmncnfgDescripcion = new ColumnConfig(ParamsConst.DESCRIPCION, "Descripcion", 344);
		configs.add(clmncnfgDescripcion);

		ColumnConfig clmncnfgCumplimiento = new ColumnConfig(ParamsConst.CUMPLIMIENTO, "Cumplimiento (%)", 344);
		configs.add(clmncnfgCumplimiento);
		
		return configs;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public void onDelete() {
		Collection ids = new ArrayList();
		List seleccionados = grid.getSelectionModel().getSelectedItems();
		Boolean validNoAsignada = Boolean.TRUE;
		Iterator it = seleccionados.iterator();
		while (it.hasNext()) {
			BaseModel model = (BaseModel) it.next();
			ids.add(model.get(ParamsConst.ID));
			if (validNoAsignada){
				validNoAsignada = ((Context.getInstance().getUsuario().get(ParamsConst.ID).equals(model.get(ParamsConst.ID_USUARIO))) || 
						(!(Context.getInstance().getUsuario().get(ParamsConst.ID).equals(model.get(ParamsConst.ID_USUARIO))) 
								&& Context.getInstance().isAvaiable(PermissionsConst.OBJETIVOS_NO_ASIGNADOS)));
			} else {
				break;
			}
		}
		if (validNoAsignada){
			Map params = new HashMap<String, String>();
			params.put(ParamsConst.IDS, ids);
			params.put(ServiceNameConst.SERVICIO, ServiceNameConst.DELETE_OBJECTIVE);
			DispatcherUtil.getDispatcher().execute(params,
					new AsyncCallback() {
	
						@Override
						public void onFailure(Throwable caught) {
							DialogFactory.error("No se han podido eliminar los objetivos. Aguarde un momento y vuelva a intentarlo.");
						}
	
						@Override
						public void onSuccess(Object result) {
							DialogFactory.info("Se eliminaron los objetivos con exito.");
							grid.getStore().getLoader().load();
						}
	
					});
		} else {
			DialogFactory.info("No tiene permisos para borrar objetivos no asignados.");
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void onSelect(List selected) {
		if (selected.size() == 1) {
			Iterator it = selected.iterator();
			if (it.hasNext()) {
				beforeUpdate((BaseModel) it.next());
			}
		} 
		
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public void onModify() {
		List seleccionados = grid.getSelectionModel().getSelectedItems();
		Map<Object,Object> actual = null;
		if (seleccionados.size() == 1) {
			Iterator it = seleccionados.iterator();
			if (it.hasNext()) {
				BaseModel model = (BaseModel) it.next();
				actual = grid.search(ParamsConst.ID, model.get(ParamsConst.ID));
			}
		}
		if ((Context.getInstance().getUsuario().get(ParamsConst.ID).equals(actual.get(ParamsConst.ID_USUARIO))) || 
				(!(Context.getInstance().getUsuario().get(ParamsConst.ID).equals(actual.get(ParamsConst.ID_USUARIO))) 
						&& Context.getInstance().isAvaiable(PermissionsConst.OBJETIVOS_NO_ASIGNADOS))){

			
			// Antes de abrir la ventana de modificacion, se verifica si no esta siendo modificado por otro usuario
			final Map<Object,Object> actualFinal = actual;
			Map params = new HashMap<String, String>();
			params.put(ParamsConst.ID, actual.get(ParamsConst.ID));
			params.put(ServiceNameConst.SERVICIO, ServiceNameConst.LOCK);
			final Map finalActual = actual;
			DispatcherUtil.getDispatcher().execute(params,
					new AsyncCallback() {
	
						@Override
						public void onFailure(Throwable caught) {
							DialogFactory.error("No se ha podido bloquear el objetivo a editar.");
						}
	
						@Override
						public void onSuccess(Object result) {
							Map user = (Map)((Map)result).get(ParamsConst.USER);
							if (user!=null){
								DialogFactory.info("El objetivo se encuentra bloqueada por el usuario " + user.get(ParamsConst.NAME));
							} else {
								ObjectiveWindow objectiveWindow = new ObjectiveWindow(false);
								Context.getInstance().addDetailExecution("ObjectiveWindow llamada a setear valores");
								objectiveWindow.setValuesToUpdate(finalActual);
								objectiveWindow.show();
							}
						}
	
					});
		} else {
			DialogFactory.info("No tiene permisos para modificar objetivos no asignadas.");
		}
	}
	
	@SuppressWarnings({"unused", "rawtypes"})
	public void beforeUpdate(BaseModel baseModel) {
		Map actual = grid.search(ParamsConst.ID, baseModel.get(ParamsConst.ID));
		gridTareas.getStore().removeAll();
		Collection tareas = (Collection) actual.get(ParamsConst.TAREAS);
		if (tareas != null) {
			
			Iterator<Map> itTareas = tareas.iterator();
			
			while (itTareas.hasNext()) {
				Map tarea = (Map) itTareas.next();
				
			}
		}
	}
	
	@Override
	public void onDividir() {
	}

}
