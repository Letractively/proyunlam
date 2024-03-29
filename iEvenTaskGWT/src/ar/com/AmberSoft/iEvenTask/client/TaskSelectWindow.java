package ar.com.AmberSoft.iEvenTask.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ar.com.AmberSoft.iEvenTask.client.utils.CheckBoxSelectionModel;
import ar.com.AmberSoft.iEvenTask.client.utils.Grid;
import ar.com.AmberSoft.iEvenTask.client.utils.Loader;
import ar.com.AmberSoft.iEvenTask.client.utils.TaskGridDataCallback;
import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;
import ar.com.AmberSoft.iEvenTask.shared.ServiceNameConst;

import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;


public class TaskSelectWindow extends Window implements Seleccionable {

	//FIXME: Revisar si es correcto pasarle como tamanio de pagina
	private final Grid grid = new Grid(this, ServiceNameConst.LIST_TASK_WITH_VISIBLE_FILTER, getGridConfig(), 100000);
	private final Button acceptButton = new Button("Aceptar");
	private Collection selecteds = null;
	
	public Collection getSelecteds() {
		return selecteds;
	}


	public TaskSelectWindow(Collection from) {
		super();
		selecteds = from;

		((Loader)grid.getStore().getLoader()).getProxy().setCallback(new TaskGridDataCallback(this, grid));
		
		this.setModal(Boolean.TRUE);
		setSize(300, 340);
		grid.setSize(280, 285);
		add(grid);
		add(acceptButton);
		acceptButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
			
			@Override
			public void componentSelected(ButtonEvent ce) {
				selecteds.clear();
				selecteds.addAll(grid.getSelectionModel().getSelectedItems());
				close();
			}
		});

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

		CheckBoxSelectionModel sm = new CheckBoxSelectionModel();
		sm.setSelectionMode(SelectionMode.MULTI);
	    configs.add(sm.getColumn());
		
		ColumnConfig clmncnfg1 = new ColumnConfig(ParamsConst.NOMBRE_TAREA, "Nombre", 270);
		configs.add(clmncnfg1);
		
		return configs;
	}


	@Override
	public void onDelete() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onSelect(List selected) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onModify() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onDividir() {
		// TODO Auto-generated method stub
		
	}
}
