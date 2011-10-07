package ar.com.AmberSoft.iEvenTask.client.menu;

import java.util.ArrayList;
import java.util.List;

import ar.com.AmberSoft.iEvenTask.client.IEvenTask;
import ar.com.AmberSoft.iEvenTask.client.Seleccionable;
import ar.com.AmberSoft.iEvenTask.client.utils.Grid;
import ar.com.AmberSoft.iEvenTask.shared.ParamsConst;
import ar.com.AmberSoft.iEvenTask.shared.ServiceNameConst;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.TextArea;

public class MainTabTareas extends TabItem implements Seleccionable {

	public static final Integer GRID_WIDTH = IEvenTask.APP_WINDOW_WIDTH - (IEvenTask.APP_WINDOW_WIDTH/4);
	public static final Integer GRID_HEIGTH = IEvenTask.MAIN_TAB_PANEL_HEIGTH;
	public static final Integer COMMENT_WIDTH = IEvenTask.APP_WINDOW_WIDTH - GRID_WIDTH;
	public static final Integer COMMENT_HEIGTH = IEvenTask.MAIN_TAB_PANEL_HEIGTH;
	public static final Integer COMMENT_BOX_WIDTH = COMMENT_WIDTH - 10; //estos 10 son para que se vea la barra de scroll
	public static final Integer COMMENT_BOX_HEIGTH = 80;
	
	public MainTabTareas(){
		super("Tareas");
		// TODO acomodar posicion
		setSize(IEvenTask.APP_WINDOW_WIDTH.toString(), IEvenTask.DEFAULT_MENU_HEIGTH.toString());
		
		//componentes del panel de tareas
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		VerticalPanel verticalPanel_grilla = new VerticalPanel();
		VerticalPanel verticalPanel_comentarios = new VerticalPanel();
		@SuppressWarnings("unchecked")
		//Context.getInstance().getUsuario()
		Grid grid = new Grid(this, ServiceNameConst.LIST_TASK, getGridConfig(), 10);
		
		//seteo las propiedades al componente Grid
		grid.setSize(GRID_WIDTH, GRID_HEIGTH);
		grid.defaultContextMenu();
		grid.defaultActionOnSelectItem();
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
		commentPanel.setHeading("Comentarios");
		
		final TextArea newTextComment = new TextArea();
		newTextComment.setCharacterWidth(35);
		newTextComment.setVisibleLines(5);
		commentPanel.add(newTextComment);
		newTextComment.setSize(COMMENT_BOX_WIDTH.toString(), COMMENT_BOX_HEIGTH.toString());
		
		final TextArea otherTextComment = new TextArea();
		otherTextComment.setCharacterWidth(35);
		otherTextComment.setVisibleLines(5);
		otherTextComment.setDirectionEstimator(true);
		otherTextComment.setReadOnly(true);
		otherTextComment.setVisible(false);
		
		Button btnAddComment = new Button("Add Comment");
		btnAddComment.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent be) {
				otherTextComment.setText(newTextComment.getText());
				otherTextComment.setVisible(true);
			}
		});
		commentPanel.setTopComponent(btnAddComment);
		commentPanel.add(otherTextComment);
		otherTextComment.setWidth(COMMENT_BOX_WIDTH.toString());
		commentPanel.setSize(COMMENT_WIDTH.toString(), COMMENT_HEIGTH.toString());
		
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

	@Override
	public void onDelete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSelect(List selected) {
		// TODO Auto-generated method stub
		
	}
	
}
