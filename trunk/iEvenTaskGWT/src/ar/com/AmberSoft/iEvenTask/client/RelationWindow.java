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
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.FileUploadField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.filters.StringFilter;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CaptionPanel;

public class RelationWindow extends Window {

	public static final Integer WINDOW_WIDTH = 665;
	public static final Integer WINDOW_HEIGTH = 470;

	public static final Integer FIELD_WIDTH = 150;
	public static final Integer LABEL_WIDTH = 150;
	
	public static final Integer DETAILS_HEIGTH = 72;
	
	public static final Integer ESPECIFIC_PANEL_WIDTH = LABEL_WIDTH + FIELD_WIDTH;
	public static final Integer ESPECIFIC_PANEL_HEIGTH = 70;
	
	public static final Integer GRID_WIDTH = WINDOW_WIDTH - 15;
	public static final Integer GRID_HEIGTH = 250;
	
	public static final String EVENT_LDAP = "ar.com.AmberSoft.iEvenTask.backend.entities.EventLDAP";
	public static final String EVENT_LOGS = "ar.com.AmberSoft.iEvenTask.backend.entities.EventLogs";
	public static final String EVENT_FILES = "ar.com.AmberSoft.iEvenTask.backend.entities.EventFiles";
	public static final String EVENT_SERVICES = "ar.com.AmberSoft.iEvenTask.backend.entities.EventServices";

	private final Grid grid = new Grid(this, ServiceNameConst.LIST_RELATION, getGridConfig(), 10);
	
	// Campos
	private final ComboBox fldEventType = new ComboBox();
	private final ComboBox fldEvent = new ComboBox();
	private final ComboBox fldAction = new ComboBox();
	
	private final VerticalPanel vPanelCreateTask = new VerticalPanel();
	private final VerticalPanel vPanelModifyStatus = new VerticalPanel();

	// Campos para Creacion de Tareas
	private final TextField fldName = new TextField();
	private final TextField fldUser = new TextField();
	
	// Campos para Modificacion de Estados
	private final ComboBox fldFromState = new ComboBox();
	private final ComboBox fldToState = new ComboBox();;
	
	private Map entityServiceRelation = new HashMap(); 
	
	/**
	 * Representa la opcion elegida en el combo de tipos de eventos
	 */
	private RelationWindowOption relationWindowOption;

	public ComboBox getFldEventType() {
		return fldEventType;
	}

	public ComboBox getFldEvent() {
		return fldEvent;
	}
	
	public ComboBox getFldFromState() {
		return fldFromState;
	}

	public ComboBox getFldToState() {
		return fldToState;
	}
	
	public TextField getFldName() {
		return fldName;
	}

	public TextField getFldUser() {
		return fldUser;
	}
	
	public Grid getGrid() {
		return grid;
	}
	
	public VerticalPanel getvPanelCreateTask() {
		return vPanelCreateTask;
	}

	public VerticalPanel getvPanelModifyState() {
		return vPanelModifyStatus;
	}

	public ComboBox getFldAction() {
		return fldAction;
	}

	public RelationWindow() {
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
		
		initializeCreatePanel();
		initializeModifyStatusPanel();
		
		horizontalPanel.add(vPanelCreateTask);
		horizontalPanel.add(vPanelModifyStatus);

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
		setHeading("Gesti\u00F3n de Relaciones");
		setLayout(new RowLayout(Orientation.VERTICAL));
		
		entityServiceRelation.put(EVENT_LDAP, ServiceNameConst.LIST_EVENT_LDAP);
		entityServiceRelation.put(EVENT_LOGS, ServiceNameConst.LIST_EVENT_LOGS);
		entityServiceRelation.put(EVENT_FILES, ServiceNameConst.LIST_EVENT_FILES);
		entityServiceRelation.put(EVENT_SERVICES, ServiceNameConst.LIST_EVENT_SERVICES);
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
	private VerticalPanel getPanelFields() {
		VerticalPanel verticalPanel = new VerticalPanel();
		
		verticalPanel.add(getFieldHorizontalLine(fldEventType, "Tipo de Evento", FIELD_WIDTH, LABEL_WIDTH));
		fldEventType.setAllowBlank(Boolean.FALSE);
		registerField(fldEventType);
		verticalPanel.add(getFieldHorizontalLine(fldEvent, "Evento", FIELD_WIDTH, LABEL_WIDTH));
		fldEvent.setAllowBlank(Boolean.FALSE);
		registerField(fldEvent);
		
		ListStore listStoreEventType = new ListStore();
		listStoreEventType.add(getModelData(ServiceNameConst.LIST_EVENT_LDAP, "LDAP"));
		listStoreEventType.add(getModelData(ServiceNameConst.LIST_EVENT_LOGS, "Patron en logs"));
		listStoreEventType.add(getModelData(ServiceNameConst.LIST_EVENT_FILES, "Archivos"));
		listStoreEventType.add(getModelData(ServiceNameConst.LIST_EVENT_SERVICES, "Servicios"));
		fldEventType.setStore(listStoreEventType);
		fldEventType.setEditable(Boolean.FALSE);
		fldEventType.setTypeAhead(true);  
		fldEventType.setTriggerAction(TriggerAction.ALL);
		
		ListStore listStoreEvent = new ListStore();
		fldEvent.setStore(listStoreEvent);
		fldEvent.setEditable(Boolean.FALSE);
		fldEvent.setTypeAhead(true);  
		fldEvent.setTriggerAction(TriggerAction.ALL);
		
		fldEventType.addSelectionChangedListener(new SelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent se) {
				ModelData modelData = se.getSelectedItem();
				if (modelData!=null){
					String serviceKey = modelData.get("key");

					Map params = new HashMap<String, String>();
					params.put(ServiceNameConst.SERVICIO, serviceKey);
					DispatcherUtil.getDispatcher().execute(params,
							new AsyncCallback() {

								@Override
								public void onFailure(Throwable caught) {
									Info.display(
											"iEvenTask",
											"No se han podido consultar el listado de eventos.");
								}

								@Override
								public void onSuccess(Object result) {
									fldEvent.clear();
									ListStore listStoreEvent = new ListStore();
									Map mapResult = (Map) result;
									Collection data = (Collection) mapResult.get(ParamsConst.DATA);
									for (Iterator iterator = data.iterator(); iterator.hasNext();) {
										Map event = (Map) iterator.next();
										listStoreEvent.add(getModelData(event.get(ParamsConst.ID).toString(), (String)event.get(ParamsConst.NAME)));
									}
									fldEvent.setStore(listStoreEvent);
									
								}
							});
				}
			}
		});
		
		
		verticalPanel.add(getFieldHorizontalLine(fldAction, "Accion", FIELD_WIDTH, LABEL_WIDTH));
		fldAction.setAllowBlank(Boolean.FALSE);
		registerField(fldAction);

		ListStore listStore = new ListStore();
		listStore.add(getModelData(RelationWindowOption.CREATE_TASK, "Crear Tarea"));
		listStore.add(getModelData(RelationWindowOption.MODIFY_STATE, "Modificar Estado"));
		fldAction.setStore(listStore);
		fldAction.setEditable(Boolean.FALSE);
		fldAction.setTypeAhead(true);  
		fldAction.setTriggerAction(TriggerAction.ALL); 
		
		fldAction.addSelectionChangedListener(new SelectionChangedListener() {

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
		relationWindowOption = RelationWindowOptionFactory.getInstance().getRelationWindowOption(key, this);
		relationWindowOption.setVisiblePanel();
	}
	
	private void initializeCreatePanel() {
		VerticalPanel vPanel = this.vPanelCreateTask;

		CaptionPanel caption = new CaptionPanel("Creacion de Tareas");
		vPanel.add(caption);
		caption.setSize(ESPECIFIC_PANEL_WIDTH.toString(), ESPECIFIC_PANEL_HEIGTH.toString());
		
		VerticalPanel bodyCaption = new VerticalPanel();
		bodyCaption.add(getFieldHorizontalLine(fldName, "Nombre de la tarea", FIELD_WIDTH, LABEL_WIDTH));
		fldName.setAllowBlank(Boolean.FALSE);
		bodyCaption.add(getFieldHorizontalLine(fldUser, "Usuario responsable", FIELD_WIDTH, LABEL_WIDTH));
		fldUser.setAllowBlank(Boolean.FALSE);
		caption.add(bodyCaption);
		
		vPanel.setVisible(Boolean.FALSE);
	}

	private void initializeModifyStatusPanel() {
		VerticalPanel vPanel = this.vPanelModifyStatus;

		CaptionPanel caption = new CaptionPanel("Modificar Estado");
		vPanel.add(caption);
		caption.setSize(ESPECIFIC_PANEL_WIDTH.toString(), ESPECIFIC_PANEL_HEIGTH.toString());

		ListStore listStoreState = new ListStore();
		listStoreState.add(getModelData("Creada", "Creada"));
		listStoreState.add(getModelData("En curso", "En curso"));

		VerticalPanel bodyCaption = new VerticalPanel();
		bodyCaption.add(getFieldHorizontalLine(fldFromState, "Estado inicial", FIELD_WIDTH, LABEL_WIDTH));
		fldFromState.setAllowBlank(Boolean.FALSE);
		fldFromState.setStore(listStoreState);
		fldFromState.setEditable(Boolean.FALSE);
		fldFromState.setTypeAhead(true);  
		fldFromState.setTriggerAction(TriggerAction.ALL);
		
		bodyCaption.add(getFieldHorizontalLine(fldToState, "Nuevo Estado", FIELD_WIDTH, LABEL_WIDTH));
		fldToState.setAllowBlank(Boolean.FALSE);
		fldToState.setAllowBlank(Boolean.FALSE);
		fldToState.setStore(listStoreState);
		fldToState.setEditable(Boolean.FALSE);
		fldToState.setTypeAhead(true);  
		fldToState.setTriggerAction(TriggerAction.ALL);

		caption.add(bodyCaption);
		
		vPanel.setVisible(Boolean.FALSE);
	}

	
	/**
	 * Retorna la configuracion de la grilla
	 */
	private List getGridConfig() {
		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

		// Se agrega esta columna para mantener el identificador de los perfiles
		ColumnConfig clmncnfgId = new ColumnConfig(ParamsConst.ID, "Identificador", 150);
		//clmncnfgId.setHidden(Boolean.TRUE);
		configs.add(clmncnfgId);

		ColumnConfig clmncnfg1 = new ColumnConfig(ParamsConst.NAME, "Nombre del Evento", 300);
		configs.add(clmncnfg1);

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
		params.put(ServiceNameConst.SERVICIO, ServiceNameConst.DELETE_EVENT);
		DispatcherUtil.getDispatcher().execute(params,
				new AsyncCallback() {

					@Override
					public void onFailure(Throwable caught) {
						Info.display(
								"iEvenTask",
								"No se han podido eliminar los eventos. Aguarde un momento y vuelva a intentarlo.");
					}

					@Override
					public void onSuccess(Object result) {
						Info.display("iEvenTask",
								"Se eliminaron los eventos con exito.");
						grid.getStore().getLoader().load();	
					}

				});

	}

	@Override
	public void beforeUpdate(BaseModel baseModel) {
		Map actual = grid.search(ParamsConst.ID, baseModel.get(ParamsConst.ID));

		if (actual != null) {
			
			relationWindowOption = RelationWindowOptionFactory.getInstance().getRelationWindowOption((String)actual.get(ParamsConst.CLASS), this);
			
			setCombo(getFldEventType(), (String) entityServiceRelation.get((String)((Map)actual.get("event")).get(ParamsConst.CLASS)));
			setCombo(getFldEvent(), ((Map)actual.get("event")).get(ParamsConst.ID).toString());
			setCombo(getFldAction(), (String)(actual.get(ParamsConst.CLASS)));
			
			relationWindowOption.beforeUpdate(actual);

		}
		
	}

	@Override
	public void onSave() {
		if (isValid()) {
			Map params = new HashMap<String, String>();
			params.put(ParamsConst.EVENT,	((ModelData)fldEvent.getValue()).get("key"));

			relationWindowOption.onSave(params);
			
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

	@Override
	protected Boolean isValid() {
		Boolean isValidCommons = super.isValid();
		if (relationWindowOption!=null){
			Boolean isValidEspecific = relationWindowOption.isValid();
			return (isValidCommons&&isValidEspecific);
		} else {
			return super.isValid();
		}
	}

}
