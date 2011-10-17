package ar.com.AmberSoft.iEvenTask.client.utils;

import java.util.List;

import ar.com.AmberSoft.iEvenTask.client.resources.Resources;

import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;

@SuppressWarnings("rawtypes")
public class TreeGrid extends
		com.extjs.gxt.ui.client.widget.treegrid.TreeGrid {

	public TreeGrid(String serviceName, List<ColumnConfig> configs) {
		super(new TreeStore(new TreeLoader(serviceName, null)),  new ColumnModel(configs));
		setBorders(true);  
	    getStyle().setLeafIcon(Resources.ICONS.music());  
	    //setAutoExpandColumn(ParamsConst.PATH);  
	    setTrackMouseOver(false);
	    
	    // Se obtiene el loader para indicarle cual es la grilla con la que
		// trabaja y que el orden de las columnas se establece de forma remota
		((TreeLoader)loader).setGrid(this);
	    
		setStateId("treeGridExample");
		setStateful(Boolean.TRUE);


		// Habilitamos para que se muestre una mascara mientras se esta cargando
		// la grilla
		setLoadMask(Boolean.TRUE);
		
	}
	

}
