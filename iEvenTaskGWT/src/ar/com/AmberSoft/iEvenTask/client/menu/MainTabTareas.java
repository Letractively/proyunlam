package ar.com.AmberSoft.iEvenTask.client.menu;

import java.util.ArrayList;
import java.util.List;

import ar.com.AmberSoft.iEvenTask.client.IEvenTask;
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
import com.google.gwt.user.client.ui.TextArea;

public class MainTabTareas extends TabItem {

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
		Grid grid = new Grid(this, ServiceNameConst.LIST_PROFILE, getGridConfig(), 10);
		
		//seteo las propiedades al componente Grid
		grid.setSize(GRID_WIDTH, GRID_HEIGTH);
		grid.defaultContextMenu();
		grid.defaultActionOnSelectItem();
		grid.setBorders(true);
		//TODO agregar al pie de la grilla la paginacion
//		verticalPanel_grilla.add(grid.getToolBar());
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
	
	//para probar, uso la consulta de perfiles
	@SuppressWarnings("rawtypes")
	private List getGridConfig() {
		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

		// Se agrega esta columna para mantener el identificador de los perfiles
		ColumnConfig clmncnfgId = new ColumnConfig(ParamsConst.ID, ParamsConst.ID, 1);
		clmncnfgId.setHidden(Boolean.TRUE);
		configs.add(clmncnfgId);

		ColumnConfig clmncnfgNombre = new ColumnConfig(ParamsConst.NAME, "Nombre", 150);
		configs.add(clmncnfgNombre);

		ColumnConfig clmncnfgConexion = new ColumnConfig(ParamsConst.CONNECTION, "Conexion", 150);
		configs.add(clmncnfgConexion);

		ColumnConfig clmncnfgGrupoLdap = new ColumnConfig(ParamsConst.GROUP, "Grupo LDAP", 150);
		configs.add(clmncnfgGrupoLdap);

		return configs;
	}
	
}
