package ar.com.AmberSoft.iEvenTask.client.menu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ar.com.AmberSoft.iEvenTask.client.CommentWindows;
import ar.com.AmberSoft.iEvenTask.client.Context;
import ar.com.AmberSoft.iEvenTask.client.DialogFactory;
import ar.com.AmberSoft.iEvenTask.client.DialogInfo;
import ar.com.AmberSoft.iEvenTask.client.IEvenTask;
import ar.com.AmberSoft.iEvenTask.client.PermissionsConst;
import ar.com.AmberSoft.iEvenTask.client.Seleccionable;
import ar.com.AmberSoft.iEvenTask.client.TaskWindow;
import ar.com.AmberSoft.iEvenTask.client.resources.Resources;
import ar.com.AmberSoft.iEvenTask.client.utils.Grid;
import ar.com.AmberSoft.iEvenTask.shared.DispatcherUtil;
import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;
import ar.com.AmberSoft.iEvenTask.shared.ServiceNameConst;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.HtmlEditor;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.filters.DateFilter;
import com.extjs.gxt.ui.client.widget.grid.filters.StringFilter;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class MainTabTareas extends TabItem implements Seleccionable {

	public static final Integer GRID_WIDTH = IEvenTask.APP_WINDOW_WIDTH - (IEvenTask.APP_WINDOW_WIDTH/4);
	public static final Integer GRID_HEIGTH = IEvenTask.MAIN_TAB_PANEL_HEIGTH;
	public static final Integer COMMENT_WIDTH = IEvenTask.APP_WINDOW_WIDTH - GRID_WIDTH;
	public static final Integer COMMENT_HEIGTH = IEvenTask.MAIN_TAB_PANEL_HEIGTH + 10;
	public static final Integer COMMENT_BOX_WIDTH = COMMENT_WIDTH - 5; //estos 10 son para que se vea la barra de scroll
	public static final Integer COMMENT_BOX_HEIGTH = 390;
	
	public static final String COMMENT_FORMAT_START = "<STRONG><EM><FONT size=2>";
	public static final String COMMENT_FORMAT_END = "</FONT></EM></STRONG>";
	
	public static Integer DELAY = 1000000000;
	
	@SuppressWarnings("unchecked")
	public final Grid grid = new Grid(this, ServiceNameConst.LIST_TASK_WITH_VISIBLE_FILTER, getGridConfig(), 18);
	
	
	public MainTabTareas(){
		super("Tareas");

		grid.addFilter(new StringFilter(ParamsConst.NOMBRE_TAREA));
		grid.addFilter(new DateFilter(ParamsConst.FECHA_COMIENZO));
		grid.addFilter(new DateFilter(ParamsConst.FECHA_FIN));
		//FIXME: revisar como filtrar correctamente los numero
		//grid.addFilter(new NumericFilter(ParamsConst.CUMPLIMIENTO));
		grid.addFilter(new StringFilter(ParamsConst.ESTADO));
		grid.addFilter(new StringFilter(ParamsConst.DESCRIPCION));
		grid.addFilter(new StringFilter(ParamsConst.ASIGNADO));
		
		Context.getInstance().setTaskGrid(grid);
		
		// TODO acomodar posicion
		setSize(IEvenTask.APP_WINDOW_WIDTH.toString(), IEvenTask.DEFAULT_MENU_HEIGTH.toString());
		
		//componentes del panel de tareas
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		VerticalPanel verticalPanel_grilla = new VerticalPanel();
		VerticalPanel verticalPanel_comentarios = new VerticalPanel();
		//Context.getInstance().getUsuario()
		
		//seteo las propiedades al componente Grid
		grid.setSize(GRID_WIDTH, GRID_HEIGTH);
		grid.defaultContextMenuTask();
		grid.defaultActionOnSelectItemTask();
		grid.setBorders(true);
		verticalPanel_grilla.add(grid.getToolBar());
		verticalPanel_grilla.add(grid);
		
		
		horizontalPanel.add(verticalPanel_grilla);
		verticalPanel_comentarios.add(addComentarios());
		
		horizontalPanel.add(verticalPanel_comentarios);
		this.add(horizontalPanel);
		
		autoRefresh();
	}

	public void autoRefresh() {
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				Context.getInstance().getTaskGrid().getStore().getLoader().load();
			}
		};
		timer.scheduleRepeating(DELAY);
	}
	
	private ContentPanel addComentarios(){
		
		final ContentPanel commentPanel = new ContentPanel();
		commentPanel.setHeading("Comentarios");
		commentPanel.setSize(COMMENT_WIDTH.toString(), COMMENT_HEIGTH.toString());

		if ((Context.getInstance().isAvaiable(PermissionsConst.COMENTARIOS)) 
			|| (Context.getInstance().isAvaiable(PermissionsConst.COMENTARIOS_NO_ASIGNADOS))){
			Button btnAddComment = new Button("Comentario");
			commentPanel.add(btnAddComment);
			
			btnAddComment.setIcon(Resources.ICONS.addComment());
			btnAddComment.addSelectionListener(new SelectionListener<ButtonEvent>() {
				public void componentSelected(ButtonEvent be) {
					List seleccionados = grid.getSelectionModel().getSelectedItems();		
					if (seleccionados.size()==1){
						BaseModel baseModel = (BaseModel) seleccionados.iterator().next();
						Map actual = grid.search(ParamsConst.ID, baseModel.get(ParamsConst.ID));
						
						if ((Context.getInstance().getUsuario().get(ParamsConst.ID).equals(actual.get(ParamsConst.ID_USUARIO))) || 
								(!(Context.getInstance().getUsuario().get(ParamsConst.ID).equals(actual.get(ParamsConst.ID_USUARIO))) 
										&& Context.getInstance().isAvaiable(PermissionsConst.COMENTARIOS_NO_ASIGNADOS))){
							//Context.getInstance().windowShow(new CommentWindows((Integer)actual.get(ParamsConst.ID), (String)actual.get(ParamsConst.NOMBRE_TAREA)));
							CommentWindows windows = new CommentWindows((Integer)actual.get(ParamsConst.ID), (String)actual.get(ParamsConst.NOMBRE_TAREA));
							windows.show();
						} else {
							DialogFactory.info("No tiene permisos para agregar comentarios en tareas no asignadas");
						}
					} else {
						if (seleccionados.size()==0){
							DialogInfo dialogInfo = new DialogInfo( 
									"Seleccione una tarea para agregar comentarios.");
						} else {
							DialogInfo dialogInfo = new DialogInfo( 
								"Seleccione solo una tarea para agregar comentarios.");
						}
					}
				}
			});
		}
		
		/*Html html = new Html();
		commentPanel.add(html);
		html.setWidth(COMMENT_BOX_WIDTH);
		html.setHeight(COMMENT_BOX_HEIGTH);
		html.setBorders(Boolean.TRUE);
		Context.getInstance().setHtml(html);*/

		final HtmlEditor htmlEditor = new HtmlEditor();
		commentPanel.add(htmlEditor);
		htmlEditor.setWidth(COMMENT_BOX_WIDTH);
		htmlEditor.setHeight(COMMENT_BOX_HEIGTH);
		htmlEditor.setShowToolbar(Boolean.FALSE);
		htmlEditor.setReadOnly(Boolean.TRUE);
		Context.getInstance().setHtmlEditor(htmlEditor);
				
		return commentPanel;
	}
	
	@SuppressWarnings("rawtypes")
	private List getGridConfig() {
		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
		DateTimeFormat dtf = DateTimeFormat.getFormat("dd-MM-yyyy");
		
		// Se agrega esta columna para mantener el identificador de las tareas
		ColumnConfig clmncnfgId = new ColumnConfig(ParamsConst.ID, ParamsConst.ID, 1);
		clmncnfgId.setHidden(Boolean.TRUE);
		configs.add(clmncnfgId);

		ColumnConfig clmncnfgNombreTarea = new ColumnConfig(ParamsConst.NOMBRE_TAREA, "Nombre", 100);
		configs.add(clmncnfgNombreTarea);
		
		ColumnConfig clmncnfgFechaComienzo = new ColumnConfig(ParamsConst.FECHA_COMIENZO, "Fecha de Comienzo", 100);
		clmncnfgFechaComienzo.setDateTimeFormat(dtf);
		configs.add(clmncnfgFechaComienzo);
		
		ColumnConfig clmncnfgFechaFin = new ColumnConfig(ParamsConst.FECHA_FIN, "Fecha de Fin", 100);
		clmncnfgFechaFin.setDateTimeFormat(dtf);
		configs.add(clmncnfgFechaFin);

		ColumnConfig clmncnfgCumplimiento = new ColumnConfig(ParamsConst.CUMPLIMIENTO, "Cumplimiento (%)", 100);
		configs.add(clmncnfgCumplimiento);

		ColumnConfig clmncnfgEstado = new ColumnConfig(ParamsConst.ESTADO, "Estado", 130);
		configs.add(clmncnfgEstado);
		
		ColumnConfig clmncnfgResponsable = new ColumnConfig(ParamsConst.ID_USUARIO, "Responsable", 140);
		configs.add(clmncnfgResponsable);
		clmncnfgResponsable.setHidden(Boolean.TRUE);

		ColumnConfig clmncnfgAsignado = new ColumnConfig(ParamsConst.ASIGNADO, "Responsable", 100);
		configs.add(clmncnfgAsignado);

		ColumnConfig clmncnfgDescripcion = new ColumnConfig(ParamsConst.DESCRIPCION, "Descripcion", 325);
		configs.add(clmncnfgDescripcion);

		return configs;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public void onDelete() {
		Collection ids = new ArrayList();
		List seleccionados = grid.getSelectionModel().getSelectedItems();
		Boolean validNoAsignada = Boolean.TRUE;
		Iterator it = seleccionados.iterator();
		while (it.hasNext()) {
			BaseModel model = (BaseModel) it.next();
			ids.add(model.get(ParamsConst.ID));
			if (validNoAsignada){
				validNoAsignada = ((Context.getInstance().getUsuario().get(ParamsConst.ID).equals(model.get(ParamsConst.ID_USUARIO))) || 
						(!(Context.getInstance().getUsuario().get(ParamsConst.ID).equals(model.get(ParamsConst.ID_USUARIO))) 
								&& Context.getInstance().isAvaiable(PermissionsConst.TAREAS_NO_ASIGNADAS)));
			} else {
				break;
			}
		}
		if (validNoAsignada){
			Map params = new HashMap<String, String>();
			params.put(ParamsConst.IDS, ids);
			params.put(ServiceNameConst.SERVICIO, ServiceNameConst.DELETE_TASK);
			DispatcherUtil.getDispatcher().execute(params,
					new AsyncCallback() {
	
						@Override
						public void onFailure(Throwable caught) {
							DialogFactory.error("No se han podido eliminar las tareas. Aguarde un momento y vuelva a intentarlo.");
						}
	
						@Override
						public void onSuccess(Object result) {
							DialogFactory.info("Se eliminaron las tareas con exito.");
							grid.getStore().getLoader().load();
						}
	
					});
		} else {
			DialogFactory.info("No tiene permisos para borrar tareas no asignadas.");
		}
	}
		
	@SuppressWarnings("rawtypes")
	/**
	 * Se invoca cuando se realiza una accion de seleccion
	 */
	public void onSelect(List selected){
		if (selected.size() == 1) {
			Iterator it = selected.iterator();
			if (it.hasNext()) {
				beforeUpdate((BaseModel) it.next());
			}
		} else {
			Context.getInstance().getHtmlEditor().setValue("");
		}
	}


	@SuppressWarnings({"rawtypes", "unchecked"})
	public void onModify() {
		List seleccionados = grid.getSelectionModel().getSelectedItems();
		Map<Object,Object> actual = null;
		if (seleccionados.size() == 1) {
			Iterator it = seleccionados.iterator();
			if (it.hasNext()) {
				BaseModel model = (BaseModel) it.next();
				actual = grid.search(ParamsConst.ID, model.get(ParamsConst.ID));
			}
		}
		if ((Context.getInstance().getUsuario().get(ParamsConst.ID).equals(actual.get(ParamsConst.ID_USUARIO))) || 
			(!(Context.getInstance().getUsuario().get(ParamsConst.ID).equals(actual.get(ParamsConst.ID_USUARIO))) 
					&& Context.getInstance().isAvaiable(PermissionsConst.TAREAS_NO_ASIGNADAS))){
			
			// Antes de abrir la ventana de modificacion, se verifica si no esta siendo modificado por otro usuario
			final Map<Object,Object> actualFinal = actual;
			Map params = new HashMap<String, String>();
			params.put(ParamsConst.ID, actual.get(ParamsConst.ID));
			params.put(ServiceNameConst.SERVICIO, ServiceNameConst.LOCK);
			DispatcherUtil.getDispatcher().execute(params,
					new AsyncCallback() {
	
						@Override
						public void onFailure(Throwable caught) {
							DialogFactory.error("No se ha podido bloquear la tarea a editar.");
						}
	
						@Override
						public void onSuccess(Object result) {
							Map user = (Map)((Map)result).get(ParamsConst.USER);
							if (user!=null){
								DialogFactory.info("La tarea se encuentra bloqueada por el usuario " + user.get(ParamsConst.NAME));
							} else {
								TaskWindow taskWindow = new TaskWindow(false);
								Context.getInstance().addDetailExecution("TaskWindow llamada a setear valores");
								taskWindow.setValuesToUpdate(actualFinal);
								taskWindow.show();
							}
						}
	
					});
			
			
		} else {
			DialogFactory.info("No tiene permisos para modificar tareas no asignadas.");
		}
	}
	

	public void beforeUpdate(BaseModel baseModel) {
		
		Map actual = grid.search(ParamsConst.ID, baseModel.get(ParamsConst.ID));
		
		viewComment((Collection) actual.get(ParamsConst.COMENTARIOS));

	}
	
	private void viewComment(Collection comentarios) {
		Context.getInstance().getHtmlEditor().setValue("");
		if (comentarios!=null){
			Iterator it = comentarios.iterator();
			while (it.hasNext()) {
				Map actual= (Map) it.next();
				Context.getInstance().addComment(
						(String) actual.get(ParamsConst.COMENTARIO),
						(Date) actual.get(ParamsConst.FECHA), 
						(String) actual.get(ParamsConst.USUARIO_NOMBRE));
			}
		}
	}

	@Override
	public void onDividir() {
		DialogFactory.division("en:", this);
	}
}
