package ar.com.AmberSoft.iEvenTask.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.client.utils.Grid;
import ar.com.AmberSoft.iEvenTask.shared.DispatcherUtil;
import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;
import ar.com.AmberSoft.iEvenTask.shared.ServiceNameConst;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.Validator;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.filters.StringFilter;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.extjs.gxt.ui.client.widget.treegrid.TreeGridCellRenderer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CaptionPanel;

@SuppressWarnings("rawtypes")
public class EventWindow extends Window {

	public static final Integer WINDOW_WIDTH = 800;
	public static final Integer WINDOW_HEIGTH = 500;

	public static final Integer FIELD_WIDTH = 200;
	public static final Integer LABEL_WIDTH = 150;
	
	public static final Integer DETAILS_HEIGTH = 130;
	
	public static final Integer ESPECIFIC_PANEL_WIDTH = LABEL_WIDTH + FIELD_WIDTH;
	public static final Integer ESPECIFIC_PANEL_HEIGTH = 70;
	
	public static final Integer GRID_WIDTH = WINDOW_WIDTH - 15;
	public static final Integer GRID_HEIGTH = 250;

	public static final Integer TREE_GRID_WIDTH = 300;
	public static final Integer TREE_GRID_HEIGTH = 100;
	
	// Campos
	private final TextField fldName = new TextField();
	private final NumberField fldPeriodicity = new NumberField();
	private final DateField fldExpiration = new DateField();
	private final NumberField fldIterations = new NumberField();
	private final ComboBox fldType = new ComboBox();
	@SuppressWarnings("unchecked")
	private final Grid grid = new Grid(this, ServiceNameConst.LIST_EVENT, getGridConfig(), 10);
	
	private final VerticalPanel vPanelLDAP = new VerticalPanel();
	private final VerticalPanel vPanelPatron = new VerticalPanel();
	private final VerticalPanel vPanelArchivos = new VerticalPanel();
	private final VerticalPanel vPanelServicios = new VerticalPanel();

	// Campos para Eventos LDAP
	private final ComboBox fldUser = new ComboBox();
	private final TextField fldCode = new TextField();

	// Campos para Eventos Patrones en Logs
	private final TextArea fldPatern = new TextArea();
	private final TextField fldPathLogs = new TextField();
	private final Button btnPathLogs = new Button("Buscar...");

	
	// Campos para Archivos
	private final ComboBox fldControlType = new ComboBox();
	private final TextField fldPath = new TextField();
	private final Button btnPath = new Button("Buscar...");
	
	// Campos para Servicios
	private final TextField fldHost = new TextField();
	private final TextField fldPort = new TextField();
	
	/**
	 * Representa la opcion elegida en el combo de tipos de eventos
	 */
	private EventWindowOption eventWindowOption;
	
	public TextField getFldPath() {
		return fldPath;
	}

	public TextField getFldPathLogs() {
		return fldPathLogs;
	}

	public Grid getGrid() {
		return grid;
	}
	
	public VerticalPanel getvPanelLDAP() {
		return vPanelLDAP;
	}

	public VerticalPanel getvPanelPatron() {
		return vPanelPatron;
	}

	public VerticalPanel getvPanelArchivos() {
		return vPanelArchivos;
	}

	public VerticalPanel getvPanelServicios() {
		return vPanelServicios;
	}
	
	public TextField getFldName() {
		return fldName;
	}

	public TextField getFldPeriodicity() {
		return fldPeriodicity;
	}

	public DateField getFldExpiration() {
		return fldExpiration;
	}

	public TextField getFldIterations() {
		return fldIterations;
	}

	public ComboBox getFldType() {
		return fldType;
	}

	public TextField getFldCode() {
		return fldCode;
	}

	public TextArea getFldPatern() {
		return fldPatern;
	}

	public ComboBox getFldControlType() {
		return fldControlType;
	}

	public TextField getFldHost() {
		return fldHost;
	}

	public TextField getFldPort() {
		return fldPort;
	}

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
	@SuppressWarnings({"unchecked"})
	private VerticalPanel getPanelFields() {
		VerticalPanel verticalPanel = new VerticalPanel();
		
		verticalPanel.add(getFieldHorizontalLine(fldName, "Nombre", FIELD_WIDTH, LABEL_WIDTH));
		fldName.setAllowBlank(Boolean.FALSE);
		fldName.setMaxLength(30);
		registerField(fldName);
		
		verticalPanel.add(getFieldHorizontalLine(fldPeriodicity, "Periodicidad", FIELD_WIDTH, LABEL_WIDTH));
		fldPeriodicity.setAllowBlank(Boolean.FALSE);
		fldPeriodicity.setPropertyEditorType(Integer.class);
//		fldPeriodicity.setValidator(new IntegerValidator());
		fldPeriodicity.setMinValue(10000);
		fldPeriodicity.setMaxValue(2147483646);//maximo valor de un int en sql server
		registerField(fldPeriodicity);

		verticalPanel.add(getFieldHorizontalLine(fldExpiration, "Fecha de Expiracion", FIELD_WIDTH, LABEL_WIDTH));
		fldExpiration.setValidator(new Validator() {
			
			@Override
			public String validate(Field<?> field, String value) {
				if (((DateField)field).getValue().before(new Date())){
					return "La fecha de expiracion debe ser posterior a la fecha actual.";
				}
				return null;
			}
		});
		registerField(fldExpiration);

		verticalPanel.add(getFieldHorizontalLine(fldIterations, "Cantidad de iteraciones", FIELD_WIDTH, LABEL_WIDTH));
//		fldIterations.setValidator(new IntegerValidator());
		//field.setAllowBlank(Boolean.FALSE);
		fldIterations.setPropertyEditorType(Integer.class);
		fldIterations.setMinValue(1);
		fldIterations.setMaxValue(2147483646);//maximo valor de un int en sql server
		registerField(fldIterations);

		verticalPanel.add(getFieldHorizontalLine(fldType, "Tipo de Evento", FIELD_WIDTH, LABEL_WIDTH));
		fldType.setAllowBlank(Boolean.FALSE);
		registerField(fldType);

		ListStore listStore = new ListStore();
		//listStore.add(getModelData(EventWindowOption.LDAP, "LDAP"));
		listStore.add(getModelData(EventWindowOption.LOGS, "Patron en logs"));
		listStore.add(getModelData(EventWindowOption.FILES, "Archivos"));
		listStore.add(getModelData(EventWindowOption.SERVICES, "Servicios"));
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
							setVisiblePanel(key);
						}
					}

				});
		
		return verticalPanel;
	}
	
	public void setVisiblePanel(String key){
		eventWindowOption = EventWindowOptionFactory.getInstance().getEventWindowOption(key, this);
		eventWindowOption.setVisiblePanel();
	}
	
	@SuppressWarnings({"unchecked"})
	private void initializeLDAPPanel() {
		VerticalPanel vPanel = this.vPanelLDAP;

		CaptionPanel caption = new CaptionPanel("LDAP");
		vPanel.add(caption);
		caption.setSize(ESPECIFIC_PANEL_WIDTH.toString(), ESPECIFIC_PANEL_HEIGTH.toString());
		
		VerticalPanel bodyCaption = new VerticalPanel();
		caption.add(bodyCaption);
		
		Map params = new HashMap<String, String>();
		params.put(ServiceNameConst.SERVICIO, ServiceNameConst.LIST_USERS);
		DispatcherUtil.getDispatcher().execute(params,
				new AsyncCallback() {

					@Override
					public void onFailure(Throwable caught) {
						DialogFactory.error("No se han podido consultar los usuarios LDAP.");
					}

					@Override
					public void onSuccess(Object result) {

						Map map = (Map) result;
						Collection users = (Collection) map.get(ParamsConst.DATA);
						ListStore listStore = new ListStore();
						Iterator it = users.iterator();
						while (it.hasNext()) {
							Map actual = (Map) it.next();
							listStore.add(getModelData((String)actual.get(ParamsConst.ID), (String)actual.get(ParamsConst.NAME)));
						}
						
						fldUser.setStore(listStore);
					}

				});

		bodyCaption.add(getFieldHorizontalLine(fldUser, "Usuario", FIELD_WIDTH, LABEL_WIDTH));
		fldUser.setEditable(Boolean.FALSE);
		fldUser.setTypeAhead(true);  
		fldUser.setTriggerAction(TriggerAction.ALL); 
		bodyCaption.add(getFieldHorizontalLine(fldCode, "Codigo de Evento", FIELD_WIDTH, LABEL_WIDTH));
		fldCode.setAllowBlank(Boolean.FALSE);
		vPanel.setVisible(Boolean.FALSE);
	}

	private void initializePatronPanel() {
		VerticalPanel vPanel = this.vPanelPatron;

		CaptionPanel caption = new CaptionPanel("Patron en Logs");
		vPanel.add(caption);
		vPanel.setVisible(Boolean.FALSE);
		caption.setSize(ESPECIFIC_PANEL_WIDTH.toString(), ESPECIFIC_PANEL_HEIGTH.toString());
		
		VerticalPanel bodyCaption = new VerticalPanel();
		caption.add(bodyCaption);
		
		bodyCaption.add(getFieldHorizontalLine(fldPathLogs, btnPathLogs, "Ruta", FIELD_WIDTH, LABEL_WIDTH));
		fldPath.setAllowBlank(Boolean.FALSE);
		btnPathLogs.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				BrowseFilesModalWindow modal = new BrowseFilesModalWindow(fldPathLogs);
				modal.show();
			}
		});
		
		bodyCaption.add(getFieldHorizontalLine(fldPatern, "Patron", FIELD_WIDTH, LABEL_WIDTH));
		fldPatern.setAllowBlank(Boolean.FALSE);
		fldPatern.setMaxLength(255);
	}

	@SuppressWarnings({"unchecked"})
	private void initializeArchivosPanel() {
		VerticalPanel vPanel = this.vPanelArchivos;

		CaptionPanel caption = new CaptionPanel("Archivos");
		vPanel.add(caption);
		caption.setSize(ESPECIFIC_PANEL_WIDTH.toString(), ESPECIFIC_PANEL_HEIGTH.toString());

		VerticalPanel bodyCaption = new VerticalPanel();
		caption.add(bodyCaption);
		
		bodyCaption.add(getFieldHorizontalLine(fldControlType, "Tipo de Control", FIELD_WIDTH, LABEL_WIDTH));
		fldControlType.setAllowBlank(Boolean.FALSE);
		ListStore listStore = new ListStore();
		listStore.add(getModelData("1", "Creacion"));
		listStore.add(getModelData("2", "Modificacion"));
		fldControlType.setStore(listStore);
		fldControlType.setEditable(Boolean.FALSE);
		fldControlType.setTypeAhead(true);  
		fldControlType.setTriggerAction(TriggerAction.ALL); 

		vPanel.setVisible(Boolean.FALSE);
		
		addFileSearch(bodyCaption);
		
	}

	public void addFileSearch(VerticalPanel bodyCaption) {
		bodyCaption.add(getFieldHorizontalLine(fldPath, btnPath, "Ruta", FIELD_WIDTH, LABEL_WIDTH));
		fldPath.setAllowBlank(Boolean.FALSE);
		fldPath.setMaxLength(255);
		btnPath.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				BrowseFilesModalWindow modal = new BrowseFilesModalWindow(fldPath);
				modal.show();
			}
		});
	}
	
	/**
	 * Genera un panel horizontal con una etiqueta y el campo correspondiente
	 * @param field
	 * @param labelText
	 * @param fieldWith
	 * @param labelWidth
	 * @return
	 */
	protected HorizontalPanel getFieldHorizontalLine(Field field, Button button, String labelText, Integer fieldWith, Integer labelWidth) {
		HorizontalPanel fieldHorizontalLine = new HorizontalPanel();
		fieldHorizontalLine.setWidth(new Integer(fieldWith + labelWidth + EXTRA_WIDTH));
		LabelField labelField = new LabelField(labelText);
		fieldHorizontalLine.add(labelField);
		labelField.setWidth(labelWidth);
		fieldHorizontalLine.add(field);
		// Realiza la validacion del campo cuando pierde el foco
		field.setAutoValidate(Boolean.TRUE);
		field.setValidateOnBlur(Boolean.TRUE);
		field.setWidth(fieldWith);
		fieldHorizontalLine.add(button);
		return fieldHorizontalLine;
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

		fldHost.setAllowBlank(Boolean.FALSE);
		fldPort.setAllowBlank(Boolean.FALSE);
		
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
	
	/**
	 * Retorna la configuracion de la grilla de archivos
	 */
	@SuppressWarnings({"unused"})
	private List getTreeGridConfig() {
		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

		// Se agrega esta columna para mantener el identificador de los perfiles
		ColumnConfig clmncnfgPath = new ColumnConfig(ParamsConst.PATH, ParamsConst.PATH, 300);
		clmncnfgPath.setRenderer(new TreeGridCellRenderer());
		configs.add(clmncnfgPath);

		return configs;
	}


	@SuppressWarnings({"unchecked"})
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
		params.put(ServiceNameConst.SERVICIO, ServiceNameConst.DELETE_EVENT);
		DispatcherUtil.getDispatcher().execute(params,
				new AsyncCallback() {

					@Override
					public void onFailure(Throwable caught) {
						maskDisable();
						DialogFactory.error("No se han podido eliminar los eventos. Aguarde un momento y vuelva a intentarlo.");
					}

					@Override
					public void onSuccess(Object result) {
						maskDisable();
						DialogFactory.info("Se eliminaron los eventos con exito.");
						grid.getStore().getLoader().load();	
					}

				});

	}

	@SuppressWarnings({"unchecked"})
	@Override
	public void beforeUpdate(BaseModel baseModel) {
		Map actual = grid.search(ParamsConst.ID, baseModel.get(ParamsConst.ID));

		if (actual != null) {
			
			eventWindowOption = EventWindowOptionFactory.getInstance().getEventWindowOption((String)actual.get(ParamsConst.CLASS), this);
			
			fldName.setValue(actual.get(ParamsConst.NAME));
			fldPeriodicity.setValue((Number)actual.get(ParamsConst.PERIODICITY));
			fldExpiration.setValue((Date)actual.get(ParamsConst.EXPIRATION));
			fldIterations.setValue((Number)actual.get(ParamsConst.ITERATIONS));
			
			eventWindowOption.beforeUpdate(actual);
		}
	}

	@SuppressWarnings({"unchecked"})
	@Override
	public void onSave() {
		maskAvaiable();
		if (isValid()) {
			Map params = new HashMap<Object, Object>();
			params.put(ParamsConst.NAME, fldName.getValue());
			params.put(ParamsConst.PERIODICITY, fldPeriodicity.getValue());
			if (fldExpiration.getValue()!=null){
				params.put(ParamsConst.EXPIRATION, fldExpiration.getValue().getTime());
			}
			params.put(ParamsConst.ITERATIONS,	fldIterations.getValue());
			eventWindowOption.onSave(params);
			DispatcherUtil.getDispatcher().execute(params,
					new AsyncCallback() {

						@Override
						public void onFailure(Throwable caught) {
							maskDisable();
							DialogFactory.error("No pudo almacenarse el evento. Aguarde un momento y vuelva a intentarlo.");
						}
						@Override
						public void onSuccess(Object result) {
							maskDisable();
							DialogFactory.info("Se almaceno el evento con exito.");
							clear();
							grid.getStore().getLoader().load();						}
					});
		} else {
			maskDisable();
		}
	}

	@Override
	protected Boolean isValid() {
		Boolean isValidCommons = super.isValid();
		if (eventWindowOption!=null){
			Boolean isValidEspecific = eventWindowOption.isValid();
			return (isValidCommons&&isValidEspecific);
		} else {
			return super.isValid();
		}
	}

	@Override
	public void onModify() {
	}
	
	protected void clear() {
		super.clear();
		if (eventWindowOption!=null){
			eventWindowOption.clear();
		}
	}

}
