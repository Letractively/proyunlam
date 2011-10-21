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
import ar.com.AmberSoft.iEvenTask.client.utils.Grid;
import ar.com.AmberSoft.iEvenTask.shared.DispatcherUtil;
import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;
import ar.com.AmberSoft.iEvenTask.shared.ServiceNameConst;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;


public class MainTabObjetivos extends TabItem implements Seleccionable{
	
	public static final Integer GRID_WIDTH = IEvenTask.APP_WINDOW_WIDTH;
	public static final Integer GRID_HEIGTH = IEvenTask.MAIN_TAB_PANEL_HEIGTH;
	
	@SuppressWarnings("unchecked")
	public final Grid grid = new Grid(this, ServiceNameConst.LIST_OBJECTIVE_WITH_VISIBLE_FILTER, getGridConfig(), 10);

	public MainTabObjetivos() {
		super("Objetivos");
		// TODO acomodar posicion
		setSize(IEvenTask.APP_WINDOW_WIDTH.toString(), IEvenTask.DEFAULT_MENU_HEIGTH.toString());
		
		//componentes del panel de objetivos
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		VerticalPanel verticalPanel_grilla = new VerticalPanel();
		
		//seteo las propiedades al componente Grid
		grid.setSize(GRID_WIDTH, GRID_HEIGTH);
		grid.defaultContextMenuTask();
		grid.defaultActionOnSelectItemTask();
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

		ColumnConfig clmncnfgUsuarioAsignado = new ColumnConfig(ParamsConst.ID_USUARIO_ASIGNADO, "Asignado a", 100);
		configs.add(clmncnfgUsuarioAsignado);

		ColumnConfig clmncnfgDescripcion = new ColumnConfig(ParamsConst.DESCRIPCION, "Descripcion", 344);
		configs.add(clmncnfgDescripcion);

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
		// TODO Auto-generated method stub
		
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
						&& Context.getInstance().isAvaiable(PermissionsConst.TAREAS_NO_ASIGNADAS))){

			ObjectiveWindow objectiveWindow = new ObjectiveWindow(false);
			objectiveWindow.setValuesToUpdate(actual);
			objectiveWindow.show();
		} else {
			DialogFactory.info("No tiene permisos para modificar objetivos no asignadas.");
		}
	}
		
}
