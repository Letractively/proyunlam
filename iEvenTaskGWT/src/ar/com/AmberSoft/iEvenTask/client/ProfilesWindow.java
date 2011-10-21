package ar.com.AmberSoft.iEvenTask.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.client.utils.Grid;
import ar.com.AmberSoft.iEvenTask.client.validaciones.ValidaMultiField;
import ar.com.AmberSoft.iEvenTask.shared.DispatcherUtil;
import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;
import ar.com.AmberSoft.iEvenTask.shared.ServiceNameConst;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.CheckBoxGroup;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.filters.StringFilter;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ScrollPanel;

@SuppressWarnings({"rawtypes", "unchecked"})
public class ProfilesWindow extends Window {

	public static final Integer WINDOW_WIDTH = 310;
	public static final Integer WINDOW_HEIGTH = 570;
	
	public static final Integer FIELD_WIDTH = 150;
	public static final Integer LABEL_WIDTH = 100;
	
	public static final Integer DETAILS_HEIGTH = 72;
	
	public static final Integer ESPECIFIC_PANEL_WIDTH = LABEL_WIDTH + FIELD_WIDTH;
	public static final Integer ESPECIFIC_PANEL_HEIGTH = 70;
	
	public static final Integer GRID_WIDTH = WINDOW_WIDTH - 15;
	public static final Integer GRID_HEIGTH = 250;
	
	// Campos
	private final ComboBox fldGroup = new ComboBox();
	
	final CheckBox fldGestionarPerfiles = new CheckBox();
	final CheckBox fldGestionarEventos = new CheckBox();
	final CheckBox fldTareas = new CheckBox();
	final CheckBox fldTareasNoAsignadas = new CheckBox();
	final CheckBox fldObjetivos = new CheckBox();
	final CheckBox fldObjetivosNoAsignadas = new CheckBox();
	final CheckBox fldComentarios = new CheckBox();
	final CheckBox fldComentariosNoAsignadas = new CheckBox();
	final CheckBox fldAsignarTareas = new CheckBox();
	final CheckBox fldReasignarTareas = new CheckBox();
	final CheckBox fldSubdividirTareas = new CheckBox();

	final Grid grid = new Grid(this, ServiceNameConst.LIST_PROFILE, getGridConfig(), 10);
	
	final Button save = new SaveButton(this);
	final Button cancel = new CancelButton(this);
	final CheckBoxGroup grupoChecks = new CheckBoxGroup();
	
	
	public ProfilesWindow() {
		super();

		initialize();

		addToolBar();
		
		TabPanel tabPanel = new TabPanel();
		TabItem tbtmDetalles = new TabItem("Detalles");
		VerticalPanel panel = new VerticalPanel();
		tabPanel.add(tbtmDetalles);
		tbtmDetalles.add(panel);
		tbtmDetalles.setHeight(DETAILS_HEIGTH.toString());

		panel.add(getPanelFields());
		panel.add(getPermissions());

		add(tabPanel, new RowData(WINDOW_WIDTH, Style.DEFAULT, new Margins()));
		
		addGrid();

	}

	/**
	 * Inicializa la ventana actual
	 */
	private void initialize() {
		setInitialWidth(WINDOW_WIDTH);
		setHeight(WINDOW_HEIGTH);
		setMaximizable(true);
		setTitleCollapse(true);
		setHeading("Gesti\u00F3n de Perfiles");
		setLayout(new RowLayout(Orientation.VERTICAL));
	}

	/**
	 * Agrega la bara de botones
	 * @return
	 */
	private void addToolBar() {
		ToolBar toolBar = new ToolBar();
		toolBar.add(save);
		toolBar.add(cancel);
		add(toolBar);
	}

	/**
	 * Agrega una grilla
	 */
	private void addGrid() {
		grid.addFilter(new StringFilter(ParamsConst.NAME));
		grid.addFilter(new StringFilter(ParamsConst.CONNECTION));
		grid.defaultContextMenu();
		grid.setSize(GRID_WIDTH, GRID_HEIGTH);
		grid.setBorders(true);
		grid.defaultActionOnSelectItem();
		this.setBottomComponent(grid.getToolBar());
		add(grid, new RowData(GRID_WIDTH, Style.DEFAULT, new Margins()));
	}

	/**
	 * Agrega los campos 
	 * @param verticalPanel
	 */
	private VerticalPanel getPanelFields() {
		VerticalPanel verticalPanel = new VerticalPanel();
		
		verticalPanel.add(getFieldHorizontalLine(fldGroup, "Grupo LDAP", FIELD_WIDTH, LABEL_WIDTH));
		fldGroup.setAllowBlank(Boolean.FALSE);
		registerField(fldGroup);
		
		Map params = new HashMap<String, String>();
		params.put(ServiceNameConst.SERVICIO, ServiceNameConst.LIST_GROUPS);
		DispatcherUtil.getDispatcher().execute(params,
				new AsyncCallback() {

					@Override
					public void onFailure(Throwable caught) {
						DialogFactory.error("No se han podido consultar los grupos LDAP.");
					}

					@Override
					public void onSuccess(Object result) {

						Map map = (Map) result;
						Collection groups = (Collection) map.get(ParamsConst.DATA);
						ListStore listStore = new ListStore();
						Iterator it = groups.iterator();
						while (it.hasNext()) {
							Map actual = (Map) it.next();
							listStore.add(getModelData((String)actual.get(ParamsConst.NAME), (String)actual.get(ParamsConst.NAME)));
						}
						
						fldGroup.setStore(listStore);
					}

				});

		fldGroup.setEditable(Boolean.FALSE);
		fldGroup.setTypeAhead(true);  
		fldGroup.setTriggerAction(TriggerAction.ALL); 
		
		return verticalPanel;
	}

	/**
	 * Agrega un apartado de seleccion de permisos
	 * @param tabPanel
	 * @param tbtmDetalles
	 * @param horizontalPanel
	 */
	private VerticalPanel getPermissions() {
		VerticalPanel verticalPanel_1 = new VerticalPanel();

		LabelField labelField = new LabelField("Permisos");
		verticalPanel_1.add(labelField);
		
		ScrollPanel cptnpnlPermisos = new ScrollPanel();
		verticalPanel_1.add(cptnpnlPermisos);
		cptnpnlPermisos.setSize("295px", "150px");

		
		grupoChecks.setOrientation(Orientation.VERTICAL);
		cptnpnlPermisos.add(grupoChecks);
		cptnpnlPermisos.setTitle("Permisos");
		//cptnpnlPermisos.setContentWidget(grupoChecks);
		registerField(grupoChecks);
		grupoChecks.setAutoValidate(true);
		grupoChecks.setValidator(new ValidaMultiField());
		grupoChecks.setSize("180px", "150px");
		grupoChecks.setFieldLabel("Permisos");
		verticalPanel_1.setSize("290px", "150px");
		
		addCheck(fldGestionarPerfiles, "Gestionar perfiles");
		addCheck(fldGestionarEventos, "Gestionar eventos");
		addCheck(fldTareas, "Crear y modificar tareas");
		addCheck(fldTareasNoAsignadas, "Crear y modificar tareas no asignadas");
		addCheck(fldObjetivos, "Crear y modificar objetivos");
		addCheck(fldObjetivosNoAsignadas, "Crear y modificar objetivos no asignadas");
		addCheck(fldComentarios, "Agregar comentarios");
		addCheck(fldComentariosNoAsignadas, "Agregar comentarios no asignadas");
		addCheck(fldAsignarTareas, "Asignar Tareas");
		addCheck(fldReasignarTareas, "Reasignar Tareas");
		addCheck(fldSubdividirTareas, "Subdividir Tareas");

		return verticalPanel_1;
	}

	public void addCheck(CheckBox field, String label) {
		grupoChecks.add(field);
		field.setBoxLabel(label);
		field.setHideLabel(true);
	}
	
	/**
	 * Retorna la configuracion de la grilla
	 */
	private List getGridConfig() {
		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

		// Se agrega esta columna para mantener el identificador de los perfiles
		ColumnConfig clmncnfgId = new ColumnConfig(ParamsConst.ID, ParamsConst.ID, 1);
		clmncnfgId.setHidden(Boolean.TRUE);
		configs.add(clmncnfgId);

		ColumnConfig clmncnfgGrupoLdap = new ColumnConfig(ParamsConst.GROUP, "Grupo LDAP", 287);
		configs.add(clmncnfgGrupoLdap);

		return configs;
	}

	@Override
	public void onDelete() {
		maskAvaiable();
		super.onDelete();
		Collection ids = new ArrayList();
		List seleccionados = grid.getSelectionModel()
				.getSelectedItems();
		Iterator it = seleccionados.iterator();
		while (it.hasNext()) {
			BaseModel model = (BaseModel) it.next();
			ids.add(model.get(ParamsConst.ID));
		}
		Map params = new HashMap<String, String>();
		params.put(ParamsConst.IDS, ids);
		params.put(ServiceNameConst.SERVICIO, ServiceNameConst.DELETE_PROFILE);
		DispatcherUtil.getDispatcher().execute(params,
				new AsyncCallback() {

					@Override
					public void onFailure(Throwable caught) {
						DialogFactory.error("No se han podido eliminar los perfiles. Aguarde un momento y vuelva a intentarlo.");
						maskDisable();
					}

					@Override
					public void onSuccess(Object result) {
						DialogFactory.info("Se eliminaron los perfiles con exito.");
						grid.getStore().getLoader().load();
						maskDisable();
					}

				});

	}

	@Override
	public void beforeUpdate(BaseModel baseModel) {
		Map actual = grid.search(ParamsConst.ID, baseModel.get(ParamsConst.ID));

		if (actual != null) {
			setCombo(fldGroup, (String)actual.get(ParamsConst.GROUP));
			 fldGestionarPerfiles.setValue(Boolean.FALSE);
			 fldGestionarEventos.setValue(Boolean.FALSE);
			 fldTareas.setValue(Boolean.FALSE);
			 fldTareasNoAsignadas.setValue(Boolean.FALSE);
			 fldObjetivos.setValue(Boolean.FALSE);
			 fldObjetivosNoAsignadas.setValue(Boolean.FALSE);
			 fldComentarios.setValue(Boolean.FALSE);
			 fldComentariosNoAsignadas.setValue(Boolean.FALSE);
			 fldAsignarTareas.setValue(Boolean.FALSE);
			 fldReasignarTareas.setValue(Boolean.FALSE);
			 fldSubdividirTareas.setValue(Boolean.FALSE);
			Collection permisos = (Collection) actual.get(ParamsConst.PERMISSIONS);
			if (permisos!=null){
				Iterator itPermisos = permisos.iterator();
				while (itPermisos.hasNext()) {
					Map permiso = (Map) itPermisos.next();
					
					evaluarPermiso(permiso, fldGestionarPerfiles, 		"1");
					evaluarPermiso(permiso, fldGestionarEventos, 		"2");
					evaluarPermiso(permiso, fldTareas, 					"3");
					evaluarPermiso(permiso, fldTareasNoAsignadas, 		"4");
					evaluarPermiso(permiso, fldObjetivos, 				"5");
					evaluarPermiso(permiso, fldObjetivosNoAsignadas,	"6");
					evaluarPermiso(permiso, fldComentarios, 			"7");
					evaluarPermiso(permiso, fldComentariosNoAsignadas, 	"8");
					evaluarPermiso(permiso, fldAsignarTareas, 			"9");
					evaluarPermiso(permiso, fldReasignarTareas, 		"10");
					evaluarPermiso(permiso, fldSubdividirTareas, 		"11");
					
				}
			}
		}
		
	}

	public void evaluarPermiso(Map permiso, CheckBox field, String id) {
		if (id.equals(permiso.get(ParamsConst.ID))){
			field.setValue(Boolean.TRUE);
		}
	}

	@Override
	public void onSave() {
		maskAvaiable();
		if (isValid()) {
			Map params = new HashMap<String, String>();
			params.put(ParamsConst.GROUP, fldGroup.getValue().get("key"));
			Collection permisos = new ArrayList();
			
			asignarPermiso(permisos, fldGestionarPerfiles, 		1);
			asignarPermiso(permisos, fldGestionarEventos, 		2);
			asignarPermiso(permisos, fldTareas, 				3);
			asignarPermiso(permisos, fldTareasNoAsignadas, 		4);
			asignarPermiso(permisos, fldObjetivos, 				5);
			asignarPermiso(permisos, fldObjetivosNoAsignadas, 	6);
			asignarPermiso(permisos, fldComentarios, 			7);
			asignarPermiso(permisos, fldComentariosNoAsignadas, 8);
			asignarPermiso(permisos, fldAsignarTareas, 			9);
			asignarPermiso(permisos, fldReasignarTareas, 		10);
			asignarPermiso(permisos, fldSubdividirTareas, 		11);
			
			params.put(ParamsConst.PERMISSIONS,	permisos);

			if (windowState.equals(State.UPDATE_STATE)) {
				List seleccionados = grid.getSelectionModel().getSelection();
				if (seleccionados.size() == 1) {
					Iterator it = seleccionados.iterator();
					if (it.hasNext()) {
						BaseModel baseModel = (BaseModel) it.next();
						params.put(ParamsConst.ID,baseModel.get(ParamsConst.ID));
					}
				}
				params.put(ServiceNameConst.SERVICIO,ServiceNameConst.UPDATE_PROFILE);
			} else {
				params.put(ServiceNameConst.SERVICIO,ServiceNameConst.CREATE_PROFILE);
			}
			DispatcherUtil.getDispatcher().execute(params,new AsyncCallback() {

						@Override
						public void onFailure(Throwable caught) {
							DialogFactory.error("No pudo almacenarse el perfil. Aguarde un momento y vuelva a intentarlo.");
							maskDisable();
						}

						@Override
						public void onSuccess(Object result) {
							if (result!=null){
								Map response = (Map) result;
								if (response.get(ParamsConst.ERROR).equals("ConstraintViolationException")){
									DialogFactory.info("El Grupo ya existe como perfil, no es posible duplicarlos.");
								} 
							} else {
								clear();
								grid.getStore().getLoader().load();
							}
							maskDisable();
						}

					});

		} else {
			maskDisable();
		}

		
	}

	public void asignarPermiso(Collection permisos, CheckBox field,
			Integer value) {
		if (field.getValue()){
			permisos.add(value);
		}
	}

	@Override
	public void onModify() {
	}


}
