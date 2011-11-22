package ar.com.AmberSoft.iEvenTask.client.menu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.client.Context;
import ar.com.AmberSoft.iEvenTask.client.DialogFactory;
import ar.com.AmberSoft.iEvenTask.shared.DispatcherUtil;
import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;
import ar.com.AmberSoft.iEvenTask.shared.ServiceNameConst;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ReportMenu extends Menu {

	
	com.extjs.gxt.ui.client.widget.menu.MenuItem listadoDeTareas;
	com.extjs.gxt.ui.client.widget.menu.MenuItem listadoDeObjetivos;
	
	public ReportMenu() {
		super();
		
		addListadoDeTareas();
		addListadoDeObjetivos();
	
	}

	public void addListadoDeTareas() {
		listadoDeTareas = new com.extjs.gxt.ui.client.widget.menu.MenuItem("Listado de tareas");
		listadoDeTareas.addSelectionListener(new SelectionListener<MenuEvent>() {
			public void componentSelected(MenuEvent ce) {
				Context.getInstance().addDetailExecution("Seteando atributos previos para el reporte");
				Map params = new HashMap();
				params.put(ParamsConst.TITLE, "Listado de tareas");
				params.put(ServiceNameConst.SERVICIO + "2", ServiceNameConst.LIST_TASK_WITH_VISIBLE_FILTER);
				ColumnModel cm = Context.getInstance().getTaskGrid().getColumnModel();
				if (cm!=null){
					Collection<ColumnConfig> configs = cm.getColumns();
					if (configs!=null){
						Collection definiciones = new ArrayList();
						Iterator itConfigs = configs.iterator();
						while (itConfigs.hasNext()) {
							ColumnConfig config = (ColumnConfig) itConfigs.next();
							
							ColumnDefine define = new ColumnDefine();
							if (Style.HorizontalAlignment.LEFT.equals(config.getAlignment())) {
								define.setHorizontalAlignment(0);
							}
							if (Style.HorizontalAlignment.CENTER.equals(config.getAlignment())) {
								define.setHorizontalAlignment(1);
							}
							if (Style.HorizontalAlignment.RIGHT.equals(config.getAlignment())) {
								define.setHorizontalAlignment(2);
							}
							define.setId(config.getId());
							define.setName(config.getHeader());
							define.setSize(new Integer(config.getWidth()));
							define.setHidden(config.isHidden());
							definiciones.add(define);
						}
						params.put(ParamsConst.COLUMN_MODEL, definiciones);		
					}
				}
				
				Context.getInstance().addDetailExecution("Seteando atributos previos para el reporte 2");
				params.putAll(Context.getInstance().getTaskGrid().getStore().getLoadConfig().getProperties());
				Context.getInstance().addDetailExecution("Invocando servicio setea atributos para el reporte");
				params.put(ServiceNameConst.SERVICIO, ServiceNameConst.SET_PARAMS_ON_SESSION);
				DispatcherUtil.getDispatcher().execute(params, new AsyncCallback() {

					@Override
					public void onFailure(Throwable caught) {
						DialogFactory.error("Fallo intentando setear informacion para el reporte.");
					}

					@Override
					public void onSuccess(Object result) {
						Window.open("/iEvenTask/report", "Reporte", "");
					}
				});
				
			 
			}
		});
		add(listadoDeTareas);
	}

	
	public void addListadoDeObjetivos() {
		listadoDeObjetivos = new com.extjs.gxt.ui.client.widget.menu.MenuItem("Listado de Objetivos");
		listadoDeObjetivos.addSelectionListener(new SelectionListener<MenuEvent>() {
			public void componentSelected(MenuEvent ce) {
				Context.getInstance().addDetailExecution("Seteando atributos previos para el reporte");
				Map params = new HashMap();
				params.put(ParamsConst.TITLE, "Listado de Objetivos");
				params.put(ServiceNameConst.SERVICIO + "2", ServiceNameConst.LIST_OBJECTIVE_WITH_VISIBLE_FILTER);
				Context.getInstance().addDetailExecution("Seteando atributos previos para el reporte 1");
				ColumnModel cm = Context.getInstance().getObjectiveGrid().getColumnModel();
				Context.getInstance().addDetailExecution("Seteando atributos previos para el reporte 2");
				if (cm!=null){
					Context.getInstance().addDetailExecution("Seteando atributos previos para el reporte 3");
					Collection<ColumnConfig> configs = cm.getColumns();
					if (configs!=null){
						Context.getInstance().addDetailExecution("Seteando atributos previos para el reporte 4");
						Collection definiciones = new ArrayList();
						Iterator itConfigs = configs.iterator();
						Context.getInstance().addDetailExecution("Seteando atributos previos para el reporte 5");
						while (itConfigs.hasNext()) {
							Context.getInstance().addDetailExecution("Seteando atributos previos para el reporte 6");
							ColumnConfig config = (ColumnConfig) itConfigs.next();
							
							ColumnDefine define = new ColumnDefine();
							if (Style.HorizontalAlignment.LEFT.equals(config.getAlignment())) {
								define.setHorizontalAlignment(0);
							}
							if (Style.HorizontalAlignment.CENTER.equals(config.getAlignment())) {
								define.setHorizontalAlignment(1);
							}
							if (Style.HorizontalAlignment.RIGHT.equals(config.getAlignment())) {
								define.setHorizontalAlignment(2);
							}
							Context.getInstance().addDetailExecution("Seteando atributos previos para el reporte 7");
							define.setId(config.getId());
							Context.getInstance().addDetailExecution("Seteando atributos previos para el reporte 8");
							define.setName(config.getHeader());
							Context.getInstance().addDetailExecution("Seteando atributos previos para el reporte 9");
							define.setSize(new Integer(config.getWidth()));
							Context.getInstance().addDetailExecution("Seteando atributos previos para el reporte 10");
							define.setHidden(config.isHidden());
							Context.getInstance().addDetailExecution("Seteando atributos previos para el reporte 11");
							definiciones.add(define);
						}
						Context.getInstance().addDetailExecution("Seteando atributos previos para el reporte 12");
						params.put(ParamsConst.COLUMN_MODEL, definiciones);		
						Context.getInstance().addDetailExecution("Seteando atributos previos para el reporte 13");
					}
				}
				
				Context.getInstance().addDetailExecution("Seteando atributos previos para el reporte 2");
				params.putAll(Context.getInstance().getTaskGrid().getStore().getLoadConfig().getProperties());
				Context.getInstance().addDetailExecution("Invocando servicio setea atributos para el reporte");
				params.put(ServiceNameConst.SERVICIO, ServiceNameConst.SET_PARAMS_ON_SESSION);
				DispatcherUtil.getDispatcher().execute(params, new AsyncCallback() {

					@Override
					public void onFailure(Throwable caught) {
						DialogFactory.error("Fallo intentando setear informacion para el reporte.");
					}

					@Override
					public void onSuccess(Object result) {
						Window.open("/iEvenTask/report", "Reporte", "");
					}
				});
				
			 
			}
		});
		add(listadoDeObjetivos);
	}

	
}
