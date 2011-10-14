package ar.com.AmberSoft.iEvenTask.client.menu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ar.com.AmberSoft.iEvenTask.client.CommentWindows;
import ar.com.AmberSoft.iEvenTask.client.Context;
import ar.com.AmberSoft.iEvenTask.client.DialogInfo;
import ar.com.AmberSoft.iEvenTask.client.IEvenTask;
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
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.HtmlEditor;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class MainTabTareas extends TabItem implements Seleccionable {

	public static final Integer GRID_WIDTH = IEvenTask.APP_WINDOW_WIDTH - (IEvenTask.APP_WINDOW_WIDTH/4);
	public static final Integer GRID_HEIGTH = IEvenTask.MAIN_TAB_PANEL_HEIGTH;
	public static final Integer COMMENT_WIDTH = IEvenTask.APP_WINDOW_WIDTH - GRID_WIDTH;
	public static final Integer COMMENT_HEIGTH = IEvenTask.MAIN_TAB_PANEL_HEIGTH;
	public static final Integer COMMENT_BOX_WIDTH = COMMENT_WIDTH - 10; //estos 10 son para que se vea la barra de scroll
	public static final Integer COMMENT_BOX_HEIGTH = 300;
	@SuppressWarnings("unchecked")
	public final Grid grid = new Grid(this, ServiceNameConst.LIST_TASK, getGridConfig(), 10);
	
	
	public MainTabTareas(){
		super("Tareas");
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
	}
	
	private ContentPanel addComentarios(){
		
		final ContentPanel commentPanel = new ContentPanel();
		commentPanel.setScrollMode(Scroll.AUTO);
		commentPanel.setHeading("Comentarios");
		commentPanel.setSize(COMMENT_WIDTH.toString(), COMMENT_HEIGTH.toString());
		
		Button btnAddComment = new Button("Comentario");
		commentPanel.add(btnAddComment);
		btnAddComment.setIcon(Resources.ICONS.addComment());
		btnAddComment.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent be) {
				List seleccionados = grid.getSelectionModel().getSelectedItems();		
				if (seleccionados.size()==1){
					BaseModel baseModel = (BaseModel) seleccionados.iterator().next();
					Map actual = grid.search(ParamsConst.ID, baseModel.get(ParamsConst.ID));
					
					Context.getInstance().windowShow(new CommentWindows((Integer)actual.get(ParamsConst.ID), (String)actual.get(ParamsConst.NOMBRE_TAREA)));
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

		ColumnConfig clmncnfgFechaComienzo = new ColumnConfig(ParamsConst.FECHA_COMIENZO, "Fecha de Comienzo", 150);
		clmncnfgFechaComienzo.setDateTimeFormat(dtf);
		configs.add(clmncnfgFechaComienzo);

		ColumnConfig clmncnfgFechaFin = new ColumnConfig(ParamsConst.FECHA_FIN, "Fecha de Fin", 150);
		clmncnfgFechaFin.setDateTimeFormat(dtf);
		configs.add(clmncnfgFechaFin);

		ColumnConfig clmncnfgDuracion = new ColumnConfig(ParamsConst.DURACION, "Duracion (hs)", 20);
		configs.add(clmncnfgDuracion);
		
		ColumnConfig clmncnfgDescripcion = new ColumnConfig(ParamsConst.DESCRIPCION, "Descripcion", 150);
		configs.add(clmncnfgDescripcion);
		
		ColumnConfig clmncnfgResponsable = new ColumnConfig(ParamsConst.ID_USUARIO, "Responsable", 150);
		configs.add(clmncnfgResponsable);

		return configs;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public void onDelete() {
		Collection ids = new ArrayList();
		List seleccionados = grid.getSelectionModel().getSelectedItems();
		Iterator it = seleccionados.iterator();
		while (it.hasNext()) {
			BaseModel model = (BaseModel) it.next();
			ids.add(model.get(ParamsConst.ID));
		}
		Map params = new HashMap<String, String>();
		params.put(ParamsConst.IDS, ids);
		params.put(ServiceNameConst.SERVICIO, ServiceNameConst.DELETE_TASK);
		DispatcherUtil.getDispatcher().execute(params,
				new AsyncCallback() {

					@Override
					public void onFailure(Throwable caught) {
						Info.display("iEvenTask", "No se han podido eliminar las tareas. Aguarde un momento y vuelva a intentarlo.");
					}

					@Override
					public void onSuccess(Object result) {
						Info.display("iEvenTask", "Se eliminaron las tareas con exito.");
						grid.getStore().getLoader().load();
					}

				});

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
		TaskWindow taskWindow = new TaskWindow(false);
		taskWindow.setValuesToUpdate(actual);
		taskWindow.show();
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
				String comentario = (String) actual.get(ParamsConst.COMENTARIO);
				Context.getInstance().addHtml(comentario);
			}
		}
	}
}
