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
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.CheckBoxGroup;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.filters.StringFilter;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CaptionPanel;

public class ProfilesWindow extends Window {

	public static final Integer WINDOW_WIDTH = 500;
	public static final Integer WINDOW_HEIGTH = 436;
	
	public static final Integer FIELD_WIDTH = 150;
	public static final Integer LABEL_WIDTH = 100;
	
	public static final Integer DETAILS_HEIGTH = 72;
	
	public static final Integer ESPECIFIC_PANEL_WIDTH = LABEL_WIDTH + FIELD_WIDTH;
	public static final Integer ESPECIFIC_PANEL_HEIGTH = 70;
	
	public static final Integer GRID_WIDTH = WINDOW_WIDTH - 15;
	public static final Integer GRID_HEIGTH = 250;
	
	// Campos
	private final ComboBox fldGroup = new ComboBox();
	final CheckBox fldObjective = new CheckBox();
	final CheckBox fldAdmin = new CheckBox();
	final Grid grid = new Grid(this, ServiceNameConst.LIST_PROFILE, getGridConfig(), 10);
	
	final Button save = new SaveButton(this);
	final Button cancel = new CancelButton(this);
	
	public ProfilesWindow() {
		super();

		initialize();

		addToolBar();
		
		TabPanel tabPanel = new TabPanel();
		TabItem tbtmDetalles = new TabItem("Detalles");
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		tabPanel.add(tbtmDetalles);
		tbtmDetalles.add(horizontalPanel);
		tbtmDetalles.setHeight(DETAILS_HEIGTH.toString());

		horizontalPanel.add(getPanelFields());
		horizontalPanel.add(getPermissions());

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
						Info.display(
								"iEvenTask",
								"No se han podido consultar los grupos LDAP.");
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

		CaptionPanel cptnpnlPermisos = new CaptionPanel("Permisos");
		verticalPanel_1.add(cptnpnlPermisos);
		cptnpnlPermisos.setSize("182px", "66px");

		CheckBoxGroup chckbxgrpPermisos = new CheckBoxGroup();
		chckbxgrpPermisos.setOrientation(Orientation.VERTICAL);

		chckbxgrpPermisos.add(fldObjective);
		fldObjective.setBoxLabel("Gesti\u00F3n de Objetivos");
		fldObjective.setHideLabel(true);

		chckbxgrpPermisos.add(fldAdmin);
		fldAdmin.setBoxLabel("Herramientas de Administraci\u00F3n");
		fldAdmin.setHideLabel(true);
		cptnpnlPermisos.setContentWidget(chckbxgrpPermisos);
		registerField(chckbxgrpPermisos);
		chckbxgrpPermisos.setAutoValidate(true);
		chckbxgrpPermisos.setValidator(new ValidaMultiField());
		chckbxgrpPermisos.setSize("172px", "50px");
		chckbxgrpPermisos.setFieldLabel("Permisos");
		verticalPanel_1.setSize("188px", "67px");
		
		return verticalPanel_1;
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

		/*ColumnConfig clmncnfgNombre = new ColumnConfig(ParamsConst.NAME, "Nombre", 150);
		configs.add(clmncnfgNombre);

		ColumnConfig clmncnfgConexion = new ColumnConfig(ParamsConst.CONNECTION, "Conexion", 150);
		configs.add(clmncnfgConexion);*/

		ColumnConfig clmncnfgGrupoLdap = new ColumnConfig(ParamsConst.GROUP, "Grupo LDAP", 300);
		configs.add(clmncnfgGrupoLdap);

		return configs;
	}

	@Override
	public void onDelete() {
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
						Info.display(
								"iEvenTask",
								"No se han podido eliminar los perfiles. Aguarde un momento y vuelva a intentarlo.");
					}

					@Override
					public void onSuccess(Object result) {
						Info.display("iEvenTask",
								"Se eliminaron los perfiles con exito.");
						grid.getStore().getLoader().load();
					}

				});

	}

	@Override
	public void beforeUpdate(BaseModel baseModel) {
		Map actual = grid.search(ParamsConst.ID, baseModel.get(ParamsConst.ID));

		if (actual != null) {
			setCombo(fldGroup, (String)actual.get(ParamsConst.GROUP));
			fldObjective.setValue(Boolean.FALSE);
			fldAdmin.setValue(Boolean.FALSE);
			Collection permisos = (Collection) actual.get(ParamsConst.PERMISSIONS);
			if (permisos!=null){
				Iterator itPermisos = permisos.iterator();
				while (itPermisos.hasNext()) {
					Map permiso = (Map) itPermisos.next();
					if (permiso.get(ParamsConst.ID)=="1"){
						fldObjective.setValue(Boolean.TRUE);
					}
					if (permiso.get(ParamsConst.ID)=="2"){
						fldAdmin.setValue(Boolean.TRUE);
					}
					
				}
			}
		}
		
	}

	@Override
	public void onSave() {
		componentsDisabled();
		if (isValid()) {
			Map params = new HashMap<String, String>();
			params.put(ParamsConst.GROUP, fldGroup.getValue().get("key"));
			params.put(ParamsConst.CHECK_OBJECTIVE,
					fldObjective.getValue());
			params.put(ParamsConst.CHECK_ADMIN, fldAdmin.getValue());
			if (windowState.equals(State.UPDATE_STATE)) {
				List seleccionados = grid.getSelectionModel()
						.getSelection();
				if (seleccionados.size() == 1) {
					Iterator it = seleccionados.iterator();
					if (it.hasNext()) {
						BaseModel baseModel = (BaseModel) it.next();
						params.put(ParamsConst.ID,
								baseModel.get(ParamsConst.ID));
					}
				}
				params.put(ServiceNameConst.SERVICIO,
						ServiceNameConst.UPDATE_PROFILE);
			} else {
				params.put(ServiceNameConst.SERVICIO,
						ServiceNameConst.CREATE_PROFILE);
			}
			DispatcherUtil.getDispatcher().execute(params,
					new AsyncCallback() {

						@Override
						public void onFailure(Throwable caught) {
							Info.display("iEvenTask",
									"No pudo almacenarse el perfil. Aguarde un momento y vuelva a intentarlo.");
							componentsEnabled();
						}

						@Override
						public void onSuccess(Object result) {
							if (result!=null){
								Map response = (Map) result;
								if (response.get(ParamsConst.ERROR).equals("ConstraintViolationException")){
									Info.display("iEvenTask", 
											"El Grupo LDAP ya existe como perfil, no es posible duplicarlos.");
								} 
							} else {
								clear();
								grid.getStore().getLoader().load();
							}
							componentsEnabled();
						}

					});

		} else {
			componentsEnabled();
		}

		
	}

	@Override
	public void componentsEnabled() {
		save.setEnabled(Boolean.TRUE);
		cancel.setEnabled(Boolean.TRUE);
		
	}

	@Override
	public void componentsDisabled() {
		save.setEnabled(Boolean.FALSE);
		cancel.setEnabled(Boolean.FALSE);
		
	}

	@Override
	public void onModify() {
		// TODO Auto-generated method stub
		
	}


}
