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
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.form.CheckBoxGroup;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.filters.StringFilter;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CaptionPanel;

public class EventWindow extends Window {


	// Campos
	private final TextField fldName = new TextField();
	private final TextField fldPeriodicity = new TextField();
	private final DateField fldExpiration = new DateField();
	private final TextField fldIterations = new TextField();
	private final ComboBox fldType = new ComboBox();
	private final TextField fldCodigoLDAP = new TextField();
	final Grid grid = new Grid(this, ServiceNameConst.LIST_PROFILE, getGridConfig(), 7);
	
	
	public EventWindow() {
		super();

		initialize();

		addToolBar();
		
		TabPanel tabPanel = new TabPanel();
		TabItem tbtmDetalles = new TabItem("Detalles");
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		tabPanel.add(tbtmDetalles);
		tbtmDetalles.add(horizontalPanel);
		tbtmDetalles.setHeight("72px");

		horizontalPanel.add(getPanelFields());
		horizontalPanel.add(getPermissions());

		add(tabPanel, new RowData(475.0, Style.DEFAULT, new Margins()));
		
		addGrid();
		

	}

	/**
	 * Inicializa la ventana actual
	 */
	private void initialize() {
		setInitialWidth(490);
		setHeight(400);
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
		grid.setSize("450", "200");
		grid.setBorders(true);
		grid.defaultActionOnSelectItem();
		this.setBottomComponent(grid.getToolBar());
		add(grid, new RowData(470.0, Style.DEFAULT, new Margins()));
	}

	/**
	 * Agrega los campos 
	 * @param verticalPanel
	 */
	private VerticalPanel getPanelFields() {
		VerticalPanel verticalPanel = new VerticalPanel();
		
		verticalPanel.add(getFieldHorizontalLine(fldName, "Nombre", "262px", "75px"));
		//field.setAllowBlank(Boolean.FALSE);
		registerField(fldName);
		
		verticalPanel.add(getFieldHorizontalLine(fldPeriodicity, "Periodicidad", "262px", "75px"));
		//field.setAllowBlank(Boolean.FALSE);
		registerField(fldPeriodicity);

		verticalPanel.add(getFieldHorizontalLine(fldExpiration, "Fecha de Expiracion", "262px", "75px"));
		//field.setAllowBlank(Boolean.FALSE);
		registerField(fldExpiration);

		verticalPanel.add(getFieldHorizontalLine(fldIterations, "Cantidad de iteraciones", "262px", "75px"));
		//field.setAllowBlank(Boolean.FALSE);
		registerField(fldIterations);

		verticalPanel.add(getFieldHorizontalLine(fldType, "Tipo de Evento", "262px", "75px"));
		//field.setAllowBlank(Boolean.FALSE);
		registerField(fldType);

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

		/*chckbxgrpPermisos.add(fldObjective);
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
		*/
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

		ColumnConfig clmncnfgNombre = new ColumnConfig(ParamsConst.NAME, "Nombre", 150);
		configs.add(clmncnfgNombre);

		ColumnConfig clmncnfgConexion = new ColumnConfig(ParamsConst.CONECTION, "Conexion", 150);
		configs.add(clmncnfgConexion);

		ColumnConfig clmncnfgGrupoLdap = new ColumnConfig(ParamsConst.GROUP, "Grupo LDAP", 150);
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
			fldName.setValue(actual.get(ParamsConst.NAME));
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
		/*	params.put(ParamsConst.CONECTION, fldConection.getValue());
			params.put(ParamsConst.GROUP, fldGroup.getValue());
			params.put(ParamsConst.CHECK_OBJECTIVE,
					fldObjective.getValue());
			params.put(ParamsConst.CHECK_ADMIN, fldAdmin.getValue()); */
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
						}

						@Override
						public void onSuccess(Object result) {
							Info.display("iEvenTask",
									"Se almaceno el perfil con exito.");
							clear();
							// FIXME: Evaluar si no hay algo mejor que el repaint, ya que este demora mucho
							grid.repaint();
						}

					});

		}

		
	}




	

}
