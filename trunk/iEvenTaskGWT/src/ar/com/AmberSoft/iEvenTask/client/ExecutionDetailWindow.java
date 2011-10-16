package ar.com.AmberSoft.iEvenTask.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;

@SuppressWarnings({"rawtypes","unchecked"})
public class ExecutionDetailWindow extends Window {

	public static final Integer WINDOW_WIDTH = 950;
	public static final Integer WINDOW_HEIGTH = 300;
	
	public static final Integer FIELD_WIDTH = 150;
	public static final Integer LABEL_WIDTH = 100;
	
	public static final Integer DETAILS_HEIGTH = 72;
	
	public static final Integer ESPECIFIC_PANEL_WIDTH = LABEL_WIDTH + FIELD_WIDTH;
	public static final Integer ESPECIFIC_PANEL_HEIGTH = 70;
	
	public static final Integer GRID_WIDTH = WINDOW_WIDTH - 15;
	public static final Integer GRID_HEIGTH = 250;
	
	// Campos
	private final ListStore store = new ListStore();
	private final Grid grid = new Grid(store, new ColumnModel(getGridConfig()));
	
	public ExecutionDetailWindow() {
		super();

		initialize();
		
		addGrid();

	}

	/**
	 * Inicializa la ventana actual
	 */
	private void initialize() {
		setInitialWidth(WINDOW_WIDTH);
		setHeight(WINDOW_HEIGTH);
		setMaximizable(false);
		setTitleCollapse(false);
		setHeading("Detalle de ejecucion");
		setLayout(new RowLayout(Orientation.VERTICAL));
	}

	/**
	 * Agrega una grilla
	 */
	private void addGrid() {
		grid.setSize(GRID_WIDTH, GRID_HEIGTH);
		grid.setBorders(true);
		add(grid, new RowData(GRID_WIDTH, Style.DEFAULT, new Margins()));
	}


	
	/**
	 * Retorna la configuracion de la grilla
	 */
	private List getGridConfig() {
		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

		ColumnConfig clmncnfgNombre = new ColumnConfig(ParamsConst.DATE, "Fecha/Hora", 150);
		configs.add(clmncnfgNombre);

		ColumnConfig clmncnfgConexion = new ColumnConfig(ParamsConst.DETAIL, "Detalle", 750);
		configs.add(clmncnfgConexion);

		return configs;
	}

	@Override
	public void onDelete() {
	}

	@Override
	public void beforeUpdate(BaseModel baseModel) {
	}

	@Override
	public void onSave() {
	}
	
	public void addDetailExecution(Date date, String detail){
		BaseModel baseModel = new BaseModel();
		baseModel.set(ParamsConst.DATE, date.toString());
		baseModel.set(ParamsConst.DETAIL, detail);
		store.add(baseModel);
	}

	@Override
	public void componentsEnabled() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentsDisabled() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onModify() {
		// TODO Auto-generated method stub
		
	}
}
