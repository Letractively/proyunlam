package ar.com.AmberSoft.iEvenTask.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.client.resources.Resources;
import ar.com.AmberSoft.iEvenTask.client.utils.Grid;
import ar.com.AmberSoft.iEvenTask.client.utils.GridDataCallback;
import ar.com.AmberSoft.iEvenTask.client.validaciones.ValidaMultiField;
import ar.com.AmberSoft.iEvenTask.shared.DispatcherUtil;
import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;
import ar.com.AmberSoft.iEvenTask.shared.ServiceNameConst;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.CheckBoxGroup;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.filters.StringFilter;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CaptionPanel;

public class ProfilesWindow extends Window {
	
	private Boolean editing = Boolean.FALSE;
	List<Field> toValidate = new ArrayList<Field>();

	public ProfilesWindow() {
		
		// Campos
		final TextField fldName = new TextField();
		final TextField fldConection = new TextField();		
		final TextField fldGroup = new TextField();
		final CheckBox fldObjective = new CheckBox();
		final CheckBox fldAdmin = new CheckBox();
		
		// Grilla
		final Grid grid = new Grid(ServiceNameConst.LIST_PROFILE, getGridConfig());
		
		// Agrega la grilla a la ventana actual
		this.add(grid);

		// Filtros sobre la grilla
		grid.addFilter(new StringFilter(ParamsConst.NAME));
		grid.addFilter(new StringFilter(ParamsConst.CONECTION));
		
		this.setIcon(Resources.ICONS.table());
		
		// Menu contextual de la grilla
		grid.addContextMenuItem("Seleccionar todos", Boolean.TRUE, new SelectionListener<MenuEvent>() {
			@Override
			public void componentSelected(MenuEvent ce) {
				grid.getSelectionModel().selectAll();
				editing = Boolean.FALSE;
			}
		});
		final MenuItem itemDelete = grid.addContextMenuItem("Elimnar Perfiles", Boolean.FALSE, new SelectionListener<MenuEvent>() {
			@Override
			public void componentSelected(MenuEvent ce) {
				editing = Boolean.FALSE;
				resetFields(fldName, fldConection, fldGroup, fldObjective, fldAdmin);
				Collection ids = new ArrayList();
				List seleccionados = grid.getSelectionModel().getSelectedItems();
				Iterator it = seleccionados.iterator();
				while (it.hasNext()) {
					BaseModel model = (BaseModel) it.next();
					ids.add(model.get(ParamsConst.ID));
				}
				Map params = new HashMap<String,String>();
				params.put(ParamsConst.IDS, ids);
				params.put(ServiceNameConst.SERVICIO, ServiceNameConst.DELETE_PROFILE);
				DispatcherUtil.getDispatcher().execute(params, new AsyncCallback() {

					@Override
					public void onFailure(Throwable caught) {
						Info.display("iEvenTask", "No se han podido eliminar los perfiles. Aguarde un momento y vuelva a intentarlo.");
					}

					@Override
					public void onSuccess(Object result) {
						Info.display("iEvenTask", "Se eliminaron los perfiles con exito.");
						refreshGrid(grid);
					}

				});
			}
		});
		
		
		// Acciones a realizar cuando selecciona algun registro de la grilla
		grid.getSelectionModel().addListener(Events.SelectionChange, new Listener() {
			@Override
			public void handleEvent(BaseEvent be) {
				List seleccionados = grid.getSelectionModel().getSelection();
				itemDelete.setEnabled(Boolean.TRUE);
				if (seleccionados.size()==1){
					Iterator it = seleccionados.iterator();
					if (it.hasNext()){
						BaseModel baseModel = (BaseModel) it.next();
						fldName.setValue(baseModel.get(ParamsConst.NAME));
						fldConection.setValue(baseModel.get(ParamsConst.CONECTION));
						fldGroup.setValue(baseModel.get(ParamsConst.GROUP));
						editing = Boolean.TRUE;
					}
				} else {
					if ((seleccionados.size()==0)||(seleccionados==null)){
						itemDelete.setEnabled(Boolean.FALSE);
					}
					resetFields(fldName, fldConection, fldGroup, fldObjective, fldAdmin);
					editing = Boolean.FALSE;
				}
			}
		});		
		
		
		

		// Propiedades basicas de la ventana actual
		setInitialWidth(490);
		setMaximizable(true);
		setTitleCollapse(true);
		setHeading("Gesti\u00F3n de Perfiles");
		setLayout(new RowLayout(Orientation.VERTICAL));
		
		
		
		
		ToolBar toolBar = new ToolBar();
		
		Button btnGuardar = new Button("Guardar");
		btnGuardar.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				if (isValid()){
					
						Map params = new HashMap<String,String>();
						params.put(ParamsConst.NAME, fldName.getValue());
						params.put(ParamsConst.CONECTION, fldConection.getValue());
						params.put(ParamsConst.GROUP, fldGroup.getValue());
						params.put(ParamsConst.CHECK_OBJECTIVE, fldObjective.getValue());
						params.put(ParamsConst.CHECK_ADMIN, fldAdmin.getValue());
						if (editing.booleanValue()){
							List seleccionados = grid.getSelectionModel().getSelection();
							if (seleccionados.size()==1){
								Iterator it = seleccionados.iterator();
								if (it.hasNext()){
									BaseModel baseModel = (BaseModel) it.next();
									params.put(ParamsConst.ID, baseModel.get(ParamsConst.ID));
								}
							}
							params.put(ServiceNameConst.SERVICIO, ServiceNameConst.UPDATE_PROFILE);
						} else {
							params.put(ServiceNameConst.SERVICIO, ServiceNameConst.CREATE_PROFILE);
						}
						DispatcherUtil.getDispatcher().execute(params, new AsyncCallback() {
	
							@Override
							public void onFailure(Throwable caught) {
								Info.display("iEvenTask", "No pudo almacenarse el perfil. Aguarde un momento y vuelva a intentarlo.");
							}
	
							@Override
							public void onSuccess(Object result) {
								Info.display("iEvenTask", "Se almaceno el perfil con exito.");
								resetFields(fldName, fldConection, fldGroup, fldObjective, fldAdmin);
								refreshGrid(grid);
							}


						});
					
	
				}
				
			}
		});
		toolBar.add(btnGuardar);
		
		Button buttonCancelar = new Button("Cancelar");
		buttonCancelar.addSelectionListener(new SelectionListener() {

			@Override
			public void componentSelected(ComponentEvent ce) {
				resetFields(fldName, fldConection, fldGroup, fldObjective, fldAdmin);
				editing = Boolean.FALSE;
			}
		
		});
		toolBar.add(buttonCancelar);
		add(toolBar);
		
		TabPanel tabPanel = new TabPanel();
		
		TabItem tbtmDetalles = new TabItem("Detalles");
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		
		VerticalPanel verticalPanel = new VerticalPanel();
		
		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		LabelField lblfldNombre = new LabelField("Nombre");
		horizontalPanel_1.add(lblfldNombre);
		lblfldNombre.setWidth("75px");
		
		horizontalPanel_1.add(fldName);
		fldName.setFieldLabel("New TextField");
		fldName.setAutoValidate(Boolean.TRUE);
		fldName.setAllowBlank(Boolean.FALSE);
		addToValidate(fldName);
		verticalPanel.add(horizontalPanel_1);
		horizontalPanel_1.setWidth("262px");
		
		HorizontalPanel horizontalPanel_2 = new HorizontalPanel();
		
		LabelField lblfldConexion = new LabelField("Conexion");
		horizontalPanel_2.add(lblfldConexion);
		lblfldConexion.setWidth("75px");
		
		horizontalPanel_2.add(fldConection);
		fldConection.setFieldLabel("New TextField");
		fldConection.setAllowBlank(Boolean.FALSE);
		addToValidate(fldConection);
		verticalPanel.add(horizontalPanel_2);
		
		HorizontalPanel horizontalPanel_3 = new HorizontalPanel();
		
		LabelField lblfldGrupoLdap = new LabelField("Grupo LDAP");
		horizontalPanel_3.add(lblfldGrupoLdap);
		lblfldGrupoLdap.setWidth("75px");
		
		horizontalPanel_3.add(fldGroup);
		fldGroup.setFieldLabel("New TextField");
		fldGroup.setAllowBlank(Boolean.FALSE);
		addToValidate(fldGroup);
		verticalPanel.add(horizontalPanel_3);
		horizontalPanel.add(verticalPanel);
		verticalPanel.setWidth("264px");
		
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
		addToValidate(chckbxgrpPermisos);
		chckbxgrpPermisos.setAutoValidate(true);
		chckbxgrpPermisos.setValidator(new ValidaMultiField());
		chckbxgrpPermisos.setSize("172px", "50px");
		chckbxgrpPermisos.setFieldLabel("Permisos");
		horizontalPanel.add(verticalPanel_1);
		verticalPanel_1.setSize("188px", "67px");
		tbtmDetalles.add(horizontalPanel);
		tabPanel.add(tbtmDetalles);
		tbtmDetalles.setHeight("72px");
		add(tabPanel, new RowData(475.0, Style.DEFAULT, new Margins()));

		// Tabla
		add(grid, new RowData(470.0, Style.DEFAULT, new Margins()));
		grid.setSize("450", "100");
		grid.setBorders(true);
		
		refreshGrid(grid);

	}
	
	private void refreshGrid(final Grid grid) {
		Map params = new HashMap();
		params.put(ServiceNameConst.SERVICIO, ServiceNameConst.LIST_PROFILE);
		GridDataCallback callback = new GridDataCallback();
		callback.setGrid(grid);
		DispatcherUtil.getDispatcher().execute(params, callback);
	}
	
	private void resetFields(final TextField fldName,
			final TextField fldConection,
			final TextField fldGroup,
			final CheckBox fldObjective,
			final CheckBox fldAdmin) {
		fldName.setValue("");
		fldConection.setValue("");
		fldGroup.setValue("");
		fldObjective.setValue(Boolean.FALSE);
		fldAdmin.setValue(Boolean.FALSE);
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
	    
		ColumnConfig clmncnfgNombre = new ColumnConfig(ParamsConst.NAME, "Nombre", 150);
		configs.add(clmncnfgNombre);
	
		
		ColumnConfig clmncnfgConexion = new ColumnConfig(ParamsConst.CONECTION, "Conexion", 150);
		configs.add(clmncnfgConexion);
		
		ColumnConfig clmncnfgGrupoLdap = new ColumnConfig(ParamsConst.GROUP, "Grupo LDAP", 150);
		configs.add(clmncnfgGrupoLdap);
		
		
		return configs;
	}


	public Boolean isValid(){
		Boolean valid = Boolean.TRUE;
		Iterator it = toValidate.iterator();
		while (it.hasNext()) {
			Field field = (Field) it.next();
			if (!field.isValid()){
				valid = Boolean.FALSE;
			}
		}
		return valid;
	}
	
	public void addToValidate(Field field){
		if (field!=null){
			toValidate.add(field);
		}
	}
	
}
