package ar.com.AmberSoft.iEvenTask.client;

import java.util.ArrayList;
import java.util.List;

import ar.com.AmberSoft.iEvenTask.client.utils.Grid;
import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;
import ar.com.AmberSoft.iEvenTask.shared.ServiceNameConst;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.filters.StringFilter;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;

public class TaskWindow extends Window {
	
	public static final Integer WINDOW_WIDTH = 500;
	public static final Integer WINDOW_HEIGTH = 436;
	
	public static final Integer FIELD_WIDTH = 150;
	public static final Integer LABEL_WIDTH = 100;
	
	public static final Integer DETAILS_HEIGTH = 72;
	
	public static final Integer ESPECIFIC_PANEL_WIDTH = LABEL_WIDTH + FIELD_WIDTH;
	public static final Integer ESPECIFIC_PANEL_HEIGTH = 70;
	
	public static final Integer GRID_WIDTH = WINDOW_WIDTH - 15;
	public static final Integer GRID_HEIGTH = 250;
	
	public static final Integer TOOLBAR_WIDTH = WINDOW_WIDTH -15;
	public static final Integer TOOLBAR_HEIGTH = 4;
	// Campos
	@SuppressWarnings("rawtypes")
	private final TextField fldName = new TextField();			//nombre de la tarea
	private final DateField fldComienzo = new DateField();		//fecha de comienzo
	private final DateField fldFin = new DateField();			//fecha de fin
	@SuppressWarnings("rawtypes")
	private final TextField fldDuracion = new TextField(); 		//duracion
	@SuppressWarnings("rawtypes")
	private final TextField fldDescripcion = new TextField(); 	//descripcion
	@SuppressWarnings("rawtypes")
	private final TextField fldResponsable = new TextField(); 	//responsable
	
	public TaskWindow() {
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
		
		add(tabPanel, new RowData(WINDOW_WIDTH, Style.DEFAULT, new Margins()));

	}
	/**
	 * Inicializa la ventana actual
	 */
	private void initialize() {
		setInitialWidth(WINDOW_WIDTH);
		setHeight(WINDOW_HEIGTH);
		setMaximizable(true);
		setTitleCollapse(true);
		setHeading("Alta de Tareas");
	}
	
	private void addToolBar() {
		ToolBar toolBar = new ToolBar();
		toolBar.setEnableOverflow(false);
		toolBar.setWidth(TOOLBAR_WIDTH);
		toolBar.setHeight(TOOLBAR_HEIGTH);
		toolBar.add(new SaveButton(this));
		toolBar.add(new CancelButton(this));
		add(toolBar);
	}
	
	
	private VerticalPanel getPanelFields() {
		VerticalPanel verticalPanel = new VerticalPanel();
		
		verticalPanel.add(getFieldHorizontalLine(fldName, "Nombre", FIELD_WIDTH, LABEL_WIDTH));
		fldName.setAllowBlank(Boolean.FALSE);
		registerField(fldName);
		
		verticalPanel.add(getFieldHorizontalLine(fldComienzo, "Fecha de Comienzo", FIELD_WIDTH, LABEL_WIDTH));
		//field.setAllowBlank(Boolean.FALSE);
		registerField(fldComienzo);

		verticalPanel.add(getFieldHorizontalLine(fldFin, "Fecha de Fin", FIELD_WIDTH, LABEL_WIDTH));
		//field.setAllowBlank(Boolean.FALSE);
		registerField(fldFin);
		
		verticalPanel.add(getFieldHorizontalLine(fldDuracion, "Duracion", FIELD_WIDTH, LABEL_WIDTH));
		fldDuracion.setAllowBlank(Boolean.TRUE);
		registerField(fldDuracion);

		verticalPanel.add(getFieldHorizontalLine(fldDescripcion, "Descripcion", FIELD_WIDTH, LABEL_WIDTH));
		fldDescripcion.setAllowBlank(Boolean.FALSE);
		registerField(fldDescripcion);
		
		verticalPanel.add(getFieldHorizontalLine(fldResponsable, "Responsable", FIELD_WIDTH, LABEL_WIDTH));
		fldResponsable.setAllowBlank(Boolean.FALSE);
		registerField(fldResponsable);
		
		return verticalPanel;
	}
	
	public void beforeUpdate(BaseModel baseModel) {
		
	}
	
	public void onSave() {
		
	}
}
