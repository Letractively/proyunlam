package ar.com.AmberSoft.iEvenTask.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ar.com.AmberSoft.iEvenTask.client.utils.TreeGrid;
import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;
import ar.com.AmberSoft.iEvenTask.shared.ServiceNameConst;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.treegrid.TreeGridCellRenderer;

public class BrowseFilesModalWindow extends Window {

	@SuppressWarnings("rawtypes")
	private final Field fieldValueReturn;
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	public BrowseFilesModalWindow(Field field) {
		super();
		this.fieldValueReturn = field;
		this.setModal(Boolean.TRUE);
		final TreeGrid treeGrid = new TreeGrid(ServiceNameConst.BROWSE_FILE, getTreeGridConfig());
		treeGrid.setSize(400, 300);
		setSize(380, 330);
		add(treeGrid);
		
		// Acciones a realizar cuando selecciona algun registro de la grilla
		treeGrid.getSelectionModel().addListener(Events.SelectionChange,
				new Listener() {
					@SuppressWarnings("deprecation")
					@Override
					public void handleEvent(BaseEvent be) {
						List seleccionados = treeGrid.getSelectionModel().getSelection();
						if (seleccionados.size() == 1) {
							Iterator it = seleccionados.iterator();
							if (it.hasNext()) {
								BaseModel actual = (BaseModel) it.next();
								fieldValueReturn.setValue(actual.get(ParamsConst.PATH));
								close();
							}
						} 
					}
				});
		
	}

	
	/**
	 * Retorna la configuracion de la grilla de archivos
	 */
	@SuppressWarnings("rawtypes")
	private List getTreeGridConfig() {
		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

		ColumnConfig clmncnfgPath = new ColumnConfig(ParamsConst.PATH, "Buscar...", 380);
		clmncnfgPath.setRenderer(new TreeGridCellRenderer());
		configs.add(clmncnfgPath);

		return configs;
	}
}
