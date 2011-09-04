package ar.com.AmberSoft.iEvenTask.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.client.utils.Grid;
import ar.com.AmberSoft.iEvenTask.client.validaciones.IntegerValidator;
import ar.com.AmberSoft.iEvenTask.shared.DispatcherUtil;
import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;
import ar.com.AmberSoft.iEvenTask.shared.ServiceNameConst;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.FileUploadField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.filters.StringFilter;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CaptionPanel;

public class EventWindow extends Window {

	public static final Integer WINDOW_WIDTH = 665;
	public static final Integer WINDOW_HEIGTH = 470;

	public static final Integer FIELD_WIDTH = 150;
	public static final Integer LABEL_WIDTH = 150;
	
	public static final Integer DETAILS_HEIGTH = 72;
	
	public static final Integer ESPECIFIC_PANEL_WIDTH = LABEL_WIDTH + FIELD_WIDTH;
	public static final Integer ESPECIFIC_PANEL_HEIGTH = 70;
	
	public static final Integer GRID_WIDTH = WINDOW_WIDTH - 15;
	public static final Integer GRID_HEIGTH = 250;

	// Campos
	private final TextField fldName = new TextField();
	private final TextField fldPeriodicity = new TextField();
	private final DateField fldExpiration = new DateField();
	private final TextField fldIterations = new TextField();
	private final ComboBox fldType = new ComboBox();
	final Grid grid = new Grid(this, ServiceNameConst.LIST_EVENT, getGridConfig(), 10);
	
	private final VerticalPanel vPanelLDAP = new VerticalPanel();
	private final VerticalPanel vPanelPatron = new VerticalPanel();
	private final VerticalPanel vPanelArchivos = new VerticalPanel();
	private final VerticalPanel vPanelServicios = new VerticalPanel();

	// Campos para Eventos LDAP
	private final TextField fldCode = new TextField();
	// Campos para Eventos Patrones en Logs
	private final FileUploadField fldPathLogs = new FileUploadField();
	private final TextArea fldPatern = new TextArea();
	// Campos para Archivos
	private final ComboBox fldControlType = new ComboBox();
	private final FileUploadField fldPathFields = new FileUploadField();
	// Campos para Servicios
	private final TextField fldHost = new TextField();
	private final TextField fldPort = new TextField();
	//private final CheckBox fldCloseTask = new CheckBox();
	
	public EventWindow() {
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
		
		initializeLDAPPanel();
		initializePatronPanel();
		initializeArchivosPanel();
		initializeServiciosPanel();
		
		horizontalPanel.add(vPanelLDAP);
		horizontalPanel.add(vPanelPatron);
		horizontalPanel.add(vPanelArchivos);
		horizontalPanel.add(vPanelServicios);

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
		setHeading("Gesti\u00F3n de Eventos");
		setLayout(new RowLayout(Orientation.VERTICAL));
	}

	/**
	 * Agrega la bara de botones
	 * @return
	 */
	private void addToolBar() {
		ToolBar toolBar = new ToolBar();
		toolBar.add(new SaveButton(this));
		toolBar.add(new CancelButton(this));
		add(toolBar);
	}

	/**
	 * Agrega una grilla
	 */
	private void addGrid() {
		grid.addFilter(new StringFilter(ParamsConst.NAME));
		grid.addFilter(new StringFilter(ParamsConst.CONECTION));
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
		
		verticalPanel.add(getFieldHorizontalLine(fldName, "Nombre", FIELD_WIDTH, LABEL_WIDTH));
		fldName.setAllowBlank(Boolean.FALSE);
		registerField(fldName);
		
		verticalPanel.add(getFieldHorizontalLine(fldPeriodicity, "Periodicidad", FIELD_WIDTH, LABEL_WIDTH));
		fldPeriodicity.setAllowBlank(Boolean.FALSE);
		fldPeriodicity.setValidator(new IntegerValidator());
		registerField(fldPeriodicity);

		verticalPanel.add(getFieldHorizontalLine(fldExpiration, "Fecha de Expiracion", FIELD_WIDTH, LABEL_WIDTH));
		//field.setAllowBlank(Boolean.FALSE);
		registerField(fldExpiration);

		verticalPanel.add(getFieldHorizontalLine(fldIterations, "Cantidad de iteraciones", FIELD_WIDTH, LABEL_WIDTH));
		fldIterations.setValidator(new IntegerValidator());
		//field.setAllowBlank(Boolean.FALSE);
		registerField(fldIterations);

		verticalPanel.add(getFieldHorizontalLine(fldType, "Tipo de Evento", FIELD_WIDTH, LABEL_WIDTH));
		fldType.setAllowBlank(Boolean.FALSE);
		registerField(fldType);

		ListStore listStore = new ListStore();
		listStore.add(getModelData("1", "LDAP"));
		listStore.add(getModelData("2", "Patron en logs"));
		listStore.add(getModelData("3", "Archivos"));
		listStore.add(getModelData("4", "Servicios"));
		fldType.setStore(listStore);
		fldType.setEditable(Boolean.FALSE);
		fldType.setTypeAhead(true);  
		fldType.setTriggerAction(TriggerAction.ALL); 
		
		fldType.addSelectionChangedListener(new SelectionChangedListener() {

					@Override
					public void selectionChanged(SelectionChangedEvent se) {
						ModelData modelData = se.getSelectedItem();
						if (modelData!=null){
							String key = modelData.get("key");
							if ("1".equals(modelData.get("key"))){
								vPanelLDAP.show();
								vPanelPatron.setVisible(Boolean.FALSE);
								vPanelArchivos.setVisible(Boolean.FALSE);
								vPanelServicios.setVisible(Boolean.FALSE);
							}
							if ("2".equals(modelData.get("key"))){
								vPanelLDAP.setVisible(Boolean.FALSE);
								vPanelPatron.show();
								vPanelArchivos.setVisible(Boolean.FALSE);
								vPanelServicios.setVisible(Boolean.FALSE);
							}		
							if ("3".equals(modelData.get("key"))){
								vPanelLDAP.setVisible(Boolean.FALSE);
								vPanelPatron.setVisible(Boolean.FALSE);
								vPanelArchivos.show();
								vPanelServicios.setVisible(Boolean.FALSE);
							}		
							if ("4".equals(modelData.get("key"))){
								vPanelLDAP.setVisible(Boolean.FALSE);
								vPanelPatron.setVisible(Boolean.FALSE);
								vPanelArchivos.setVisible(Boolean.FALSE);
								vPanelServicios.show();
							}		
						}
					}
				});
		
		return verticalPanel;
	}
	


	private void initializeLDAPPanel() {
		VerticalPanel vPanel = this.vPanelLDAP;

		CaptionPanel caption = new CaptionPanel("LDAP");
		vPanel.add(caption);
		caption.setSize(ESPECIFIC_PANEL_WIDTH.toString(), ESPECIFIC_PANEL_HEIGTH.toString());
		caption.add(getFieldHorizontalLine(fldCode, "Codigo de Evento", FIELD_WIDTH, LABEL_WIDTH));
		fldCode.setAllowBlank(Boolean.FALSE);
		registerField(fldCode);
		vPanel.setVisible(Boolean.FALSE);
	}

	private void initializePatronPanel() {
		VerticalPanel vPanel = this.vPanelPatron;

		CaptionPanel caption = new CaptionPanel("Patron en Logs");
		vPanel.add(caption);
		caption.setSize(ESPECIFIC_PANEL_WIDTH.toString(), ESPECIFIC_PANEL_HEIGTH.toString());
		
		VerticalPanel bodyCaption = new VerticalPanel();
		bodyCaption.add(getFieldHorizontalLine(fldPathLogs, "Ruta", FIELD_WIDTH, LABEL_WIDTH));
		fldPathLogs.setAllowBlank(Boolean.FALSE);
		bodyCaption.add(getFieldHorizontalLine(fldPatern, "Patron", FIELD_WIDTH, LABEL_WIDTH));
		fldPatern.setAllowBlank(Boolean.FALSE);
		caption.add(bodyCaption);
		
		vPanel.setVisible(Boolean.FALSE);
	}

	private void initializeArchivosPanel() {
		VerticalPanel vPanel = this.vPanelArchivos;

		CaptionPanel caption = new CaptionPanel("Archivos");
		vPanel.add(caption);
		caption.setSize(ESPECIFIC_PANEL_WIDTH.toString(), ESPECIFIC_PANEL_HEIGTH.toString());

		VerticalPanel bodyCaption = new VerticalPanel();
		bodyCaption.add(getFieldHorizontalLine(fldControlType, "Tipo de Control", FIELD_WIDTH, LABEL_WIDTH));
		bodyCaption.add(getFieldHorizontalLine(fldPathFields, "Ruta", FIELD_WIDTH, LABEL_WIDTH));
		caption.add(bodyCaption);
		
		ListStore listStore = new ListStore();
		listStore.add(getModelData("1", "Creacion"));
		listStore.add(getModelData("2", "Modificacion"));
		fldControlType.setStore(listStore);
		fldControlType.setEditable(Boolean.FALSE);
		fldControlType.setTypeAhead(true);  
		fldControlType.setTriggerAction(TriggerAction.ALL); 
		
		vPanel.setVisible(Boolean.FALSE);
	}

	private void initializeServiciosPanel() {
		VerticalPanel vPanel = this.vPanelServicios;

		CaptionPanel caption = new CaptionPanel("Servicios");
		vPanel.add(caption);
		caption.setSize(ESPECIFIC_PANEL_WIDTH.toString(), ESPECIFIC_PANEL_HEIGTH.toString());

		VerticalPanel bodyCaption = new VerticalPanel();
		bodyCaption.add(getFieldHorizontalLine(fldHost, "Direccion del servidor", FIELD_WIDTH, LABEL_WIDTH));
		bodyCaption.add(getFieldHorizontalLine(fldPort, "Puerto", FIELD_WIDTH, LABEL_WIDTH));
		caption.add(bodyCaption);

		vPanel.setVisible(Boolean.FALSE);
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

		ColumnConfig clmncnfg1 = new ColumnConfig(ParamsConst.NAME, "Nombre", 150);
		configs.add(clmncnfg1);

		ColumnConfig clmncnfg2 = new ColumnConfig(ParamsConst.PERIODICITY, "Periodicidad", 150);
		configs.add(clmncnfg2);

		ColumnConfig clmncnfg3 = new ColumnConfig(ParamsConst.EXPIRATION, "Expiracion", 150);
		configs.add(clmncnfg3);

		ColumnConfig clmncnfg4 = new ColumnConfig(ParamsConst.ITERATIONS, "Iteraciones", 150);
		configs.add(clmncnfg4);

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
		params.put(ParamsConst.ID, ids);
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
					}

				});

	}

	@Override
	public void beforeUpdate(BaseModel baseModel) {
		Map actual = grid.search(ParamsConst.ID, baseModel.get(ParamsConst.ID));

		if (actual != null) {
			//fldName.setValue(actual.get(ParamsConst.NAME));
		/*	fldConection.setValue(actual.get(ParamsConst.CONECTION));
			fldGroup.setValue(actual.get(ParamsConst.GROUP));
			fldObjective.setValue(Boolean.FALSE);
			fldAdmin.setValue(Boolean.FALSE);
			Collection permisos = (Collection) actual.get(ParamsConst.PERMISOS);
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
			}*/
		}
		
	}

	@Override
	public void onSave() {
		if (isValid()) {
			Map params = new HashMap<String, String>();
			params.put(ParamsConst.NAME, fldName.getValue());
			params.put(ParamsConst.PERIODICITY, fldPeriodicity.getValue());
			params.put(ParamsConst.EXPIRATION, fldExpiration.getValue());
			params.put(ParamsConst.ITERATIONS,	fldIterations.getValue());

			/*listStore.add(getModelData("1", "LDAP"));
			listStore.add(getModelData("2", "Patron en logs"));
			listStore.add(getModelData("3", "Archivos"));
			listStore.add(getModelData("4", "Servicios"));*/

			// Si es de tipo LDAP
			if ("1".equals(fldType.getValue().get("key"))){
				params.put(ParamsConst.CODE,	fldCode.getValue());
				params.put(ServiceNameConst.SERVICIO, ServiceNameConst.CREATE_EVENT_LDAP);
			}
			
			
			/*if (windowState.equals(State.UPDATE_STATE)) {
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
			}*/
			
			
			DispatcherUtil.getDispatcher().execute(params,
					new AsyncCallback() {

						@Override
						public void onFailure(Throwable caught) {
							Info.display("iEvenTask",
									"No pudo almacenarse el evento. Aguarde un momento y vuelva a intentarlo.");
						}

						@Override
						public void onSuccess(Object result) {
							Info.display("iEvenTask",
									"Se almaceno el evento con exito.");
							clear();
							grid.getStore().getLoader().load();						}

					});

		}

		
	}




	

}
